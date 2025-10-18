package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // Global shared Bank instance
    private static final Bank bank = new Bank();

    // Logged-in customer (shared across controllers)
    private static Customer loggedInCustomer;

    public static Bank getBank() {
        return bank;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public static void setLoggedInCustomer(Customer c) {
        loggedInCustomer = c;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/view/Main.fxml")
        );

        primaryStage.setTitle("Banking System");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
