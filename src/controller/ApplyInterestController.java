package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

public class ApplyInterestController {

    @FXML private AnchorPane rootPane;

    private static final Bank bank = BankContext.bank;
    private Customer customer;

    @FXML
    private void initialize() {
        customer = LoginController.getLoggedInCustomer();
    }

    @FXML
    private void handleApplyInterest() {
        if (customer == null) {
            showAlert("No customer logged in.");
            return;
        }

        System.out.println("Accounts before interest: " + customer.getAccounts().size());

        boolean success = bank.payInterest(customer);
        if (success) {
            showAlert("✅ Interest applied to all eligible accounts.");
        } else {
            showAlert("❌ No accounts eligible for interest.");
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Dashboard.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow(); // ✅ Safe reference
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
