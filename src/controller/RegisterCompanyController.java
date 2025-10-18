package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

public class RegisterCompanyController {

    // Company Details
    @FXML private TextField companyIdField;
    @FXML private TextField companyNameField;
    @FXML private PasswordField passwordField;

    // Contact Person
    @FXML private TextField contactPersonNameField;
    @FXML private TextField contactPersonEmailField;

    // Buttons and Status
    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;

    private final Bank bank = Main.getBank();

    @FXML
    private void handleRegister() {
        String companyId = companyIdField.getText().trim();
        String companyName = companyNameField.getText().trim();
        String password = passwordField.getText().trim();
        String contactName = contactPersonNameField.getText().trim();
        String contactEmail = contactPersonEmailField.getText().trim();

        // Validate required fields
        if (companyId.isEmpty() || companyName.isEmpty() || password.isEmpty() ||
                contactName.isEmpty() || contactEmail.isEmpty()) {
            statusLabel.setText("Please fill in all required fields.");
            return;
        }

        try {
            // Create company customer with basic constructor
            Customer customer = new CompanyCustomer(companyId, password, contactName, companyName);

            boolean success = bank.registerCustomer(customer);
            if (success) {
                statusLabel.setText("Company registered successfully!");
                statusLabel.setStyle("-fx-text-fill: green;");
                clearFields();
            } else {
                statusLabel.setText("Registration failed. Company ID may already exist.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            statusLabel.setText("Error during registration: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Login.fxml");
    }

    private void clearFields() {
        companyIdField.clear();
        companyNameField.clear();
        passwordField.clear();
        contactPersonNameField.clear();
        contactPersonEmailField.clear();
    }

    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) companyIdField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}