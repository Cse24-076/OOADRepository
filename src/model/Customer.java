package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerID;
    protected String password;
    protected String name;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String customerID, String password, String name) {
        this.customerID = customerID;
        this.password = password;
        this.name = name;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean loginWithPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    // File persistence methods
    public abstract String toFileString();
    public abstract String getType();
}