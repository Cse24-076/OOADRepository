package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Bank bank = new Bank(); // Optional: replace with Bank.getInstance()

    public static void main(String[] args) {
        launch(args); // 🚀 Launch GUI directly
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Banking System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
