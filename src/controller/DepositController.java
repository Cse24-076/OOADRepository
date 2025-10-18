package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

public class DepositController {

    @FXML private TextField accountNumberField;
    @FXML private TextField amountField;
    @FXML private Label noteLabel;  // Using noteLabel from FXML for status messages
    @FXML private Button depositButton;
    @FXML private Button backButton;

    private final Bank bank = Main.getBank();
    private Customer customer;

    @FXML
    public void initialize() {
        customer = Main.getLoggedInCustomer();
    }

    @FXML
    private void handleDeposit() {
        String accountNumber = accountNumberField.getText().trim();
        String amountText = amountField.getText().trim();

        // Validate inputs
        if (accountNumber.isEmpty() || amountText.isEmpty()) {
            noteLabel.setText("Please enter both account number and amount.");
            noteLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                noteLabel.setText("Amount must be greater than zero.");
                noteLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        } catch (NumberFormatException e) {
            noteLabel.setText("Please enter a valid amount.");
            noteLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Perform deposit
        boolean success = bank.deposit(customer, accountNumber, amount);

        if (success) {
            noteLabel.setText("Deposit of BWP " + String.format("%.2f", amount) + " successful!");
            noteLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            noteLabel.setText("Deposit failed. Check account number and try again.");
            noteLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Dashboard.fxml");
    }

    private void clearFields() {
        accountNumberField.clear();
        amountField.clear();
    }

    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) amountField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}