package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import model.*;
import model.Main;

public class LoginController {

    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final Bank bank = Main.getBank();

    @FXML
    private void handleLogin(ActionEvent event) {
        String id = idField.getText();
        String password = passwordField.getText();

        if (id.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both ID and password.");
            return;
        }

        Customer logged = bank.login(id, password);
        if (logged == null) {
            errorLabel.setText("Invalid ID or Password");
            return;
        }

        Main.setLoggedInCustomer(logged);
        loadPage(event, "/view/Dashboard.fxml");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        loadPage(event, "/view/RegisterType.fxml");
    }

    private void loadPage(ActionEvent event, String file) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(file));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
