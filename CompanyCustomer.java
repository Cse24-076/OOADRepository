public class CompanyCustomer extends Customer {
    private String companyName;
    private String contactPerson;

    public CompanyCustomer(String customerID, String password, String address,
                           String companyName, String contactPerson) {
        super(customerID, password, address);
        this.companyName = companyName;
        this.contactPerson = contactPerson;
    }

    @Override
    public String getDetails() {
        return companyName + " (Contact: " + contactPerson + ", ID: " + customerID + ") - " + address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }
}
