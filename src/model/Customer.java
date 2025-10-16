package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerID;
    protected String password;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String customerID, String password) {
        this.customerID = customerID;
        this.password = password;
    }

    public String getCustomerID() {
        return customerID;
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
}
