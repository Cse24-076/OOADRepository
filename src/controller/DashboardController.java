package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

import java.util.List;

public class DashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label customerTypeLabel;  // Added this field
    @FXML private ListView<String> transactionsListView;  // Only this list exists in FXML

    private Customer customer;
    private final Bank bank = Main.getBank();

    @FXML
    public void initialize() {
        customer = Main.getLoggedInCustomer();
        if (customer != null) {
            welcomeLabel.setText("Welcome, " + customer.getName() + "!");

            // Set customer type label
            if (customer instanceof model.IndividualCustomer) {
                customerTypeLabel.setText("Individual Customer");
            } else if (customer instanceof model.CompanyCustomer) {
                customerTypeLabel.setText("Company Customer");
            }

            loadTransactions();
        }
    }

    // Display all transactions across all accounts
    private void loadTransactions() {
        List<String> allTransactions = bank.getCustomerTransactions(customer);
        transactionsListView.getItems().setAll(allTransactions);
    }

    // Navigate to Open Account screen
    @FXML
    private void handleOpenAccount() {
        loadScene("/view/OpenAccount.fxml");
    }

    // Navigate to Deposit screen
    @FXML
    private void handleDeposit() {
        loadScene("/view/Deposit.fxml");
    }

    // Navigate to Withdraw screen
    @FXML
    private void handleWithdraw() {
        loadScene("/view/Withdraw.fxml");
    }

    // Apply interest to all accounts
    @FXML
    private void handleApplyInterest() {
        boolean success = bank.payInterest(customer);
        if (success) {
            showAlert("Success", "Interest applied to all eligible accounts!");
            loadTransactions(); // Refresh transactions
        } else {
            showAlert("Error", "No accounts available for interest payment.");
        }
    }

    // Navigate to View Accounts screen
    @FXML
    private void handleViewAccounts() {
        loadScene("/view/ViewAccounts.fxml");
    }

    // Logout and return to login
    @FXML
    private void handleLogout() {
        Main.setLoggedInCustomer(null); // Clear logged-in customer
        loadScene("/view/Login.fxml");
    }

    // Utility method to load a new FXML scene
    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}