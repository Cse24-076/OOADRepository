package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

public class RegisterCompanyController {

    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField companyNameField;
    @FXML private TextField contactPersonField;

    private static final Bank bank = BankContext.bank;// Replace with shared instance if needed

    @FXML
    private void handleRegister() {
        String id = idField.getText().trim();
        String password = passwordField.getText().trim();
        String address = addressField.getText().trim();
        String company = companyNameField.getText().trim();
        String contact = contactPersonField.getText().trim();

        if (id.isEmpty() || password.isEmpty() || address.isEmpty() || company.isEmpty() || contact.isEmpty()) {
            showAlert("All fields are required.");
            return;
        }

        CompanyCustomer customer = new CompanyCustomer(id, password, address, company, contact);
        boolean success = bank.registerCustomer(customer);

        if (success) {
            showAlert("✅ Company registered successfully!");
            switchScene("/view/Login.fxml");
        } else {
            showAlert("❌ Customer ID already exists.");
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/RegisterType.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) idField.getScene().getWindow();
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
