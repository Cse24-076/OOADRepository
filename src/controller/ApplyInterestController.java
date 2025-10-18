package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

public class ApplyInterestController {

    @FXML private Label notificationLabel;  // Using notificationLabel from FXML
    @FXML private Button applyButton;
    @FXML private Button backButton;

    private final Bank bank = Main.getBank();
    private Customer customer;

    @FXML
    public void initialize() {
        customer = Main.getLoggedInCustomer();
        updateNotificationLabel();
    }

    @FXML
    private void handleApplyInterest() {
        boolean success = bank.payInterest(customer);

        if (success) {
            notificationLabel.setText("✓ Monthly interest applied successfully to all eligible accounts!");
            notificationLabel.setStyle("-fx-text-fill: #27ae60;");
        } else {
            notificationLabel.setText("No eligible accounts found for interest payment.");
            notificationLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Dashboard.fxml");
    }

    private void updateNotificationLabel() {
        if (customer == null || customer.getAccounts().isEmpty()) {
            notificationLabel.setText("You don't have any accounts yet. Open an account first.");
            notificationLabel.setStyle("-fx-text-fill: #e74c3c;");
        } else {
            notificationLabel.setText("Click 'Apply Interest' to add monthly interest to your savings and investment accounts.");
            notificationLabel.setStyle("-fx-text-fill: #3C6382;");
        }
    }

    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) notificationLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}