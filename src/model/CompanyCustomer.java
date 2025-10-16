package model;

public class CompanyCustomer extends Customer {
    private String address;
    private String companyName;
    private String contactPerson;

    public CompanyCustomer(String customerID, String password, String address, String companyName, String contactPerson) {
        super(customerID, password);
        this.address = address;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}
