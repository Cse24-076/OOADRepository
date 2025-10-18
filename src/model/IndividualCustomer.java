package model;

import java.time.LocalDate;

public class IndividualCustomer extends Customer {
    private String email;
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
    private String nationality;
    private String address;
    private String phone;
    private String employmentStatus;
    private String occupation;
    private String incomeSource;
    private double monthlyIncome;

    // 13-ARGUMENT CONSTRUCTOR (matches your controller)
    public IndividualCustomer(String customerID, String password, String firstName,
                              String surname, LocalDate dateOfBirth, String nationality,
                              String address, String employmentStatus, String occupation,
                              String incomeSource, double monthlyIncome, String phone,
                              String email) {
        super(customerID, password, firstName + " " + surname);
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.address = address;
        this.employmentStatus = employmentStatus;
        this.occupation = occupation;
        this.incomeSource = incomeSource;
        this.monthlyIncome = monthlyIncome;
        this.phone = phone;
        this.email = email;

        System.out.println("DEBUG: IndividualCustomer created with 13 arguments: " + customerID);
    }

    // Keep original 4-argument constructor for compatibility
    public IndividualCustomer(String customerID, String password, String name, String email) {
        super(customerID, password, name);
        this.email = email;
        System.out.println("DEBUG: IndividualCustomer created with 4 arguments: " + customerID);
    }

    // Getters
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getNationality() { return nationality; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getOccupation() { return occupation; }
    public String getIncomeSource() { return incomeSource; }
    public double getMonthlyIncome() { return monthlyIncome; }

    @Override
    public String toFileString() {
        return String.format("INDIVIDUAL|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%.2f|%s|%s",
                customerID, password,
                firstName != null ? firstName : "",
                surname != null ? surname : "",
                dateOfBirth != null ? dateOfBirth.toString() : "null",
                nationality != null ? nationality : "",
                address != null ? address : "",
                employmentStatus != null ? employmentStatus : "",
                occupation != null ? occupation : "",
                incomeSource != null ? incomeSource : "",
                monthlyIncome,
                phone != null ? phone : "",
                email);
    }

    @Override
    public String getType() {
        return "INDIVIDUAL";
    }

    public static IndividualCustomer fromString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length >= 13 && parts[0].equals("INDIVIDUAL")) {
            return new IndividualCustomer(
                    parts[1], // customerID
                    parts[2], // password
                    parts[3], // firstName
                    parts[4], // surname
                    parts[5].equals("null") ? null : LocalDate.parse(parts[5]), // dateOfBirth
                    parts[6], // nationality
                    parts[7], // address
                    parts[8], // employmentStatus
                    parts[9], // occupation
                    parts[10], // incomeSource
                    Double.parseDouble(parts[11]), // monthlyIncome
                    parts[12], // phone
                    parts[13]  // email
            );
        }
        // Fallback for old format
        else if (parts.length >= 5 && parts[0].equals("INDIVIDUAL")) {
            return new IndividualCustomer(parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}