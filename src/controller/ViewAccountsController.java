package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

import java.util.List;

public class ViewAccountsController {

    @FXML private ListView<String> accountListView;

    private final Bank bank = Main.getBank();
    private Customer customer;

    @FXML
    public void initialize() {
        customer = Main.getLoggedInCustomer();
        List<String> accounts = bank.getAccountSummaries(customer);
        accountListView.getItems().setAll(accounts);
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Dashboard.fxml");
    }

    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) accountListView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
