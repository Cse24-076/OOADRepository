package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.time.LocalDate;

public class RegisterIndividualController {

    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    @FXML private TextField firstNameField;
    @FXML private TextField surnameField;
    @FXML private DatePicker dobPicker;
    @FXML private ComboBox<String> nationalityBox;
    @FXML private TextField addressField;

    @FXML private ComboBox<String> employmentStatusBox;
    @FXML private TextField occupationField;
    @FXML private ComboBox<String> incomeSourceBox;
    @FXML private TextField monthlyIncomeField;

    @FXML private TextField phoneField;
    @FXML private TextField emailField;

    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private Label statusLabel;

    private final Bank bank = Main.getBank();

    @FXML
    public void initialize() {
        // Initialize Nationality ComboBox with 2 options
        ObservableList<String> nationalityOptions = FXCollections.observableArrayList(
                "Citizen",
                "Non-Citizen"
        );
        nationalityBox.setItems(nationalityOptions);

        // Initialize Employment Status ComboBox
        ObservableList<String> employmentOptions = FXCollections.observableArrayList(
                "Employed",
                "Self-Employed",
                "Unemployed",
                "Student",
                "Retired",
                "Part-Time",
                "Contract",
                "Other"
        );
        employmentStatusBox.setItems(employmentOptions);

        // Initialize Source of Income ComboBox
        ObservableList<String> incomeSourceOptions = FXCollections.observableArrayList(
                "Employment Salary",
                "Business Income",
                "Investments",
                "Rental Income",
                "Pension",
                "Government Support",
                "Family Support",
                "Savings",
                "Other"
        );
        incomeSourceBox.setItems(incomeSourceOptions);
    }

    @FXML
    private void handleRegister() {
        String customerID = idField.getText().trim();
        String password = passwordField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String surname = surnameField.getText().trim();
        LocalDate dateOfBirth = dobPicker.getValue();
        String nationality = nationalityBox.getValue();
        String address = addressField.getText().trim();
        String employmentStatus = employmentStatusBox.getValue();
        String occupation = occupationField.getText().trim();
        String incomeSource = incomeSourceBox.getValue();
        String monthlyIncomeText = monthlyIncomeField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        System.out.println("DEBUG: Starting registration for: " + customerID);

        // Validate required fields
        if (customerID.isEmpty() || password.isEmpty() || firstName.isEmpty() ||
                surname.isEmpty() || dateOfBirth == null || nationality == null ||
                phone.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill in all required fields.");
            System.out.println("DEBUG: Validation failed - missing required fields");
            return;
        }

        // Validate monthly income if provided
        double monthlyIncome = 0.0;
        if (!monthlyIncomeText.isEmpty()) {
            try {
                monthlyIncome = Double.parseDouble(monthlyIncomeText);
                if (monthlyIncome < 0) {
                    statusLabel.setText("Monthly income cannot be negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                statusLabel.setText("Please enter a valid monthly income.");
                return;
            }
        }

        try {
            System.out.println("DEBUG: Creating IndividualCustomer with 13-argument constructor...");

            // Create customer with full constructor - ALL FXML FIELDS
            IndividualCustomer customer = new IndividualCustomer(
                    customerID, password, firstName, surname, dateOfBirth,
                    nationality, address, employmentStatus, occupation,
                    incomeSource, monthlyIncome, phone, email
            );

            System.out.println("DEBUG: Customer object created successfully: " + customer.getCustomerID());

            boolean success = bank.registerCustomer(customer);
            System.out.println("DEBUG: Bank registration result: " + success);

            if (success) {
                statusLabel.setText("Individual registered successfully!");
                statusLabel.setStyle("-fx-text-fill: green;");
                clearFields();

                // Check if file was created
                java.io.File file = new java.io.File("customers.txt");
                System.out.println("DEBUG: customers.txt exists: " + file.exists());
            } else {
                statusLabel.setText("Registration failed. ID may already exist.");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Exception during registration: " + e.getMessage());
            e.printStackTrace();
            statusLabel.setText("Error during registration: " + e.getMessage());
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleBack() {
        loadScene("/view/Login.fxml");
    }

    // ADD THIS METHOD - Clear all form fields
    private void clearFields() {
        idField.clear();
        passwordField.clear();
        firstNameField.clear();
        surnameField.clear();
        dobPicker.setValue(null);
        nationalityBox.setValue(null);
        addressField.clear();
        employmentStatusBox.setValue(null);
        occupationField.clear();
        incomeSourceBox.setValue(null);
        monthlyIncomeField.clear();
        phoneField.clear();
        emailField.clear();
        statusLabel.setText("");
    }

    // ADD THIS METHOD - Load scenes
    private void loadScene(String fxmlPath) {
        try {
            Stage stage = (Stage) idField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}