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

public class LoginController {

    @FXML private TextField idField;
    @FXML private PasswordField passwordField;

    private static final Bank bank = BankContext.bank; // ✅ Shared instance
    private static Customer loggedInCustomer;

    @FXML
    private void handleLogin() {
        String id = idField.getText().trim();
        String password = passwordField.getText().trim();

        if (id.isEmpty() || password.isEmpty()) {
            showAlert("Please enter both ID and password.");
            return;
        }

        System.out.println("Trying login: " + id + " / " + password); // 🔍 Debug line
        System.out.println("Bank has customers: " + bank.getAllCustomers().size());

        Customer customer = bank.login(id, password);
        if (customer != null) {
            loggedInCustomer = customer;
            switchScene("/view/Dashboard.fxml");
        } else {
            showAlert("❌ Invalid credentials.");
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Main.fxml");
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

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }
}
