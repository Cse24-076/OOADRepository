package model;

public class IndividualCustomer extends Customer {
    private String address;
    private String firstName;
    private String surname;

    public IndividualCustomer(String customerID, String password, String address, String firstName, String surname) {
        super(customerID, password);
        this.address = address;
        this.firstName = firstName;
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public String getFullName() {
        return firstName + " " + surname;
    }
}
