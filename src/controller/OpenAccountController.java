package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

public class OpenAccountController {

    @FXML private TextField branchField;
    @FXML private ComboBox<String> accountTypeBox;
    @FXML private TextField depositField;
    @FXML private TextField employerField;
    @FXML private TextField employerAddressField;

    private static final Bank bank = BankContext.bank;// Replace with shared instance if needed
    private Customer customer;

    @FXML
    private void initialize() {
        customer = LoginController.getLoggedInCustomer();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");
    }

    @FXML
    private void handleCreateAccount() {
        String branch = branchField.getText().trim();
        String type = accountTypeBox.getValue();
        String depositText = depositField.getText().trim();
        String employer = employerField.getText().trim();
        String employerAddress = employerAddressField.getText().trim();

        if (branch.isEmpty() || type == null) {
            showAlert("Branch and account type are required.");
            return;
        }

        int choice = switch (type) {
            case "Savings" -> 1;
            case "Investment" -> 2;
            case "Cheque" -> 3;
            default -> -1;
        };

        double deposit = 0;
        try {
            deposit = depositText.isEmpty() ? 0 : Double.parseDouble(depositText);
        } catch (NumberFormatException e) {
            showAlert("Invalid deposit amount.");
            return;
        }

        boolean success = bank.openAccount(customer, choice, branch, deposit, employer, employerAddress);
        if (success) {
            showAlert("✅ Account created successfully!");
            switchScene("/view/Dashboard.fxml");
        } else {
            showAlert("❌ Failed to create account. Check requirements.");
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Dashboard.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) branchField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
