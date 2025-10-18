package model;

public class CompanyCustomer extends Customer {
    private final String companyName;
    private String companyType;
    private String industry;
    private String businessAddress;
    private String postalAddress;
    private String contactPosition;
    private String contactPhone;
    private String contactEmail;
    private String bankAccount;
    private String sourceOfFunds;
    private int numberOfEmployees;

    // Full constructor with all FXML fields
    public CompanyCustomer(String customerID, String password, String contactName,
                           String companyName, String companyType, String industry,
                           String businessAddress, String postalAddress, String contactPosition,
                           String contactPhone, String contactEmail, String bankAccount,
                           String sourceOfFunds, int numberOfEmployees) {
        super(customerID, password, contactName);
        this.companyName = companyName;
        this.companyType = companyType;
        this.industry = industry;
        this.businessAddress = businessAddress;
        this.postalAddress = postalAddress;
        this.contactPosition = contactPosition;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.bankAccount = bankAccount;
        this.sourceOfFunds = sourceOfFunds;
        this.numberOfEmployees = numberOfEmployees;
    }

    // Original constructor for compatibility
    public CompanyCustomer(String customerID, String password, String name, String companyName) {
        super(customerID, password, name);
        this.companyName = companyName;
    }

    // Getters
    public String getCompanyName() { return companyName; }
    public String getCompanyType() { return companyType; }
    public String getIndustry() { return industry; }
    public String getBusinessAddress() { return businessAddress; }
    public String getPostalAddress() { return postalAddress; }
    public String getContactPosition() { return contactPosition; }
    public String getContactPhone() { return contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public String getBankAccount() { return bankAccount; }
    public String getSourceOfFunds() { return sourceOfFunds; }
    public int getNumberOfEmployees() { return numberOfEmployees; }

    @Override
    public String toFileString() {
        return String.format("COMPANY|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%d",
                customerID, password, name, companyName,
                companyType != null ? companyType : "",
                industry != null ? industry : "",
                businessAddress != null ? businessAddress : "",
                postalAddress != null ? postalAddress : "",
                contactPosition != null ? contactPosition : "",
                contactPhone != null ? contactPhone : "",
                contactEmail != null ? contactEmail : "",
                bankAccount != null ? bankAccount : "",
                sourceOfFunds != null ? sourceOfFunds : "",
                numberOfEmployees);
    }

    @Override
    public String getType() {
        return "COMPANY";
    }

    public static CompanyCustomer fromString(String data) {
        String[] parts = data.split("\\|");
        if (parts.length >= 14 && parts[0].equals("COMPANY")) {
            return new CompanyCustomer(
                    parts[1], // customerID
                    parts[2], // password
                    parts[3], // contactName
                    parts[4], // companyName
                    parts[5], // companyType
                    parts[6], // industry
                    parts[7], // businessAddress
                    parts[8], // postalAddress
                    parts[9], // contactPosition
                    parts[10], // contactPhone
                    parts[11], // contactEmail
                    parts[12], // bankAccount
                    parts[13], // sourceOfFunds
                    Integer.parseInt(parts[14]) // numberOfEmployees
            );
        }
        // Fallback for old format
        else if (parts.length >= 5 && parts[0].equals("COMPANY")) {
            return new CompanyCustomer(parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}