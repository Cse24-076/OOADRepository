package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MainController {

    @FXML private Button registerButton;
    @FXML private Button loginButton;

    @FXML
    private void handleRegister() {
        switchScene("/view/RegisterType.fxml");
    }

    @FXML
    private void handleLogin() {
        switchScene("/view/Login.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace(); // Optional: replace with alert if needed
        }
    }
}
