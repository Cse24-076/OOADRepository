package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.*;

import java.util.List;

public class ViewAccountsController {

    @FXML private ListView<String> accountListView;

    private static final Bank bank = BankContext.bank;
    private Customer customer;

    @FXML
    private void initialize() {
        customer = LoginController.getLoggedInCustomer();
        if (customer != null) {
            List<String> summaries = bank.getAccountSummaries(customer);
            accountListView.getItems().addAll(summaries);
        }
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Dashboard.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) accountListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
