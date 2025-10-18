package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterTypeController {

    @FXML
    private void handleIndividual(ActionEvent event) {
        loadPage(event, "/view/RegisterIndividual.fxml");
    }

    @FXML
    private void handleCompany(ActionEvent event) {
        loadPage(event, "/view/RegisterCompany.fxml");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        loadPage(event, "/view/Login.fxml");
    }

    private void loadPage(ActionEvent event, String file) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(file));

            // Correct way to get the current window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
