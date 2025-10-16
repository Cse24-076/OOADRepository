package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class RegisterTypeController {

    @FXML private Button individualButton;
    @FXML private Button companyButton;

    @FXML
    private void handleIndividual() {
        switchScene("/view/RegisterIndividual.fxml");
    }

    @FXML
    private void handleCompany() {
        switchScene("/view/RegisterCompany.fxml");
    }

    @FXML
    private void handleBack() {
        switchScene("/view/Main.fxml");
    }

    private void switchScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) individualButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace(); // Optional: replace with alert
        }
    }
}
