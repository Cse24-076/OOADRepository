package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;

public class OpenAccountController {

    @FXML private TextField branchField;
    @FXML private ComboBox<String> accountTypeBox;
    @FXML private TextField depositField;
    @FXML private TextField employerField;
    @FXML private TextField employerAddressField;
    @FXML private Label noteLabel;  // Using noteLabel for status messages
    @FXML private Button createAccountButton;
    @FXML private Button backButton;

    private final Bank bank = Main.getBank();
    private Customer customer;

    @FXML
    public void initialize() {
        customer = Main.getLoggedInCustomer();
        accountTypeBox.getItems().addAll("Savings", "Investment", "Cheque");

        // Set up account type selection listener to show/hide relevant fields
        accountTypeBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateFormFields(newValue)
        );

        // Initialize with all optional fields hidden
        updateFormFields(null);
    }

    private void updateFormFields(String accountType) {
        // Hide all optional fields first
        depositField.setVisible(false);
        employerField.setVisible(false);
        employerAddressField.setVisible(false);
        noteLabel.setText("");

        if (accountType != null) {
            switch (accountType) {
                case "Investment":
                    depositField.setVisible(true);
                    noteLabel.setText("Note: Investment accounts require minimum BWP 1000 deposit");
                    break;
                case "Cheque":
                    employerField.setVisible(true);
                    employerAddressField.setVisible(true);
                    noteLabel.setText("Note: Cheque accounts available for individual customers only");
                    break;
                case "Savings":
                    noteLabel.setText("Note: Savings accounts have no minimum deposit");
                    break;
            }
        }
    }

    @FXML
    private void handleCreateAccount() {
        String branch = branchField.getText().trim();
        String accountType = accountTypeBox.getValue();

        // Validate required fields
        if (branch.isEmpty() || accountType == null) {
            noteLabel.setText("Please select account type and enter branch name.");
            noteLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Map account type to number (1=Savings, 2=Investment, 3=Cheque)
        int type = accountTypeBox.getSelectionModel().getSelectedIndex() + 1;

        double deposit = 0;
        String employer = "";
        String employerAddress = "";

        // Handle account-specific validations
        if (type == 2) { // Investment account
            try {
                deposit = Double.parseDouble(depositField.getText().trim());
                if (deposit < InvestmentAccount.MIN_OPENING_BALANCE) {
                    noteLabel.setText("Investment accounts require minimum BWP " +
                            InvestmentAccount.MIN_OPENING_BALANCE + " deposit");
                    noteLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            } catch (NumberFormatException e) {
                noteLabel.setText("Please enter a valid deposit amount for investment account.");
                noteLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        } else if (type == 3) { // Cheque account
            employer = employerField.getText().trim();
            employerAddress = employerAddressField.getText().trim();

            // Cheque accounts only for individual customers
            if (!(customer instanceof IndividualCustomer)) {
                noteLabel.setText("Cheque accounts are only available for individual customers.");
                noteLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            if (employer.isEmpty() || employerAddress.isEmpty()) {
                noteLabel.setText("Please enter employer name and address for cheque account.");
                noteLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        }

        // Create the account
        boolean success = bank.openAccount(customer, type, branch, deposit, employer, employerAddress);

        if (success) {
            noteLabel.setText("Account created successfully!");
            noteLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            noteLabel.setText("Failed to create account. Please check your inputs.");
            noteLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Dashboard.fxml");
    }

    private void clearFields() {
        branchField.clear();
        accountTypeBox.getSelectionModel().clearSelection();
        depositField.clear();
        employerField.clear();
        employerAddressField.clear();
        updateFormFields(null);
    }

    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) branchField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}