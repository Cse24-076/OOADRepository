package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

public class WithdrawController {

    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;

    private static final Bank bank = BankContext.bank;// Replace with shared instance if needed
    private Customer customer;

    @FXML
    private void initialize() {
        customer = LoginController.getLoggedInCustomer();
    }

    @FXML
    private void handleWithdraw() {
        String accNum = accountNumberField.getText().trim();
        String amountText = amountField.getText().trim();

        if (accNum.isEmpty() || amountText.isEmpty()) {
            showAlert("Please enter account number and amount.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Invalid amount.");
            return;
        }

        boolean success = bank.withdraw(customer, accNum, amount);
        if (success) {
            showAlert("✅ Withdrawal successful!");
            switchScene("/view/Dashboard.fxml");
        } else {
            showAlert("❌ Withdrawal failed. Check account type, balance, or number.");
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Dashboard.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) accountNumberField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
