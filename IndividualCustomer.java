public class IndividualCustomer extends Customer {
    private String firstname;
    private String surname;

    public IndividualCustomer(String customerID, String password, String address, 
                              String firstname, String surname) {
        super(customerID, password, address);
        this.firstname = firstname;
        this.surname = surname;
    }

    @Override
    public String getDetails() {
        return firstname + " " + surname + " (ID: " + customerID + ") - " + address;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Individual Customer: " + firstname + " " + surname + " (" + customerID + ")";
    }
}
