package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.Customer;

public class DashboardController {

    @FXML private Label welcomeLabel;

    private Customer customer;

    @FXML
    private void initialize() {
        customer = LoginController.getLoggedInCustomer();
        if (customer != null) {
            welcomeLabel.setText("Welcome, " + customer.getCustomerID());
        }
    }

    @FXML
    private void handleOpenAccount() {
        switchScene("/view/OpenAccount.fxml");
    }

    @FXML
    private void handleViewAccounts() {
        switchScene("/view/ViewAccounts.fxml");
    }

    @FXML
    private void handleDeposit() {
        switchScene("/view/Deposit.fxml");
    }

    @FXML
    private void handleWithdraw() {
        switchScene("/view/Withdraw.fxml");
    }

    @FXML
    private void handleApplyInterest() {
        switchScene("/view/ApplyInterest.fxml");
    }

    @FXML
    private void handleLogout() {
        switchScene("/view/Main.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
