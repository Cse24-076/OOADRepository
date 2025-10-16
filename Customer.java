import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerID;
    protected String password;
    protected String address;
    protected List<Account> accounts = new ArrayList<>();

    public Customer(String customerID, String password, String address) {
        this.customerID = customerID;
        this.password = password;
        this.address = address;
    }

    public boolean loginWithPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getAddress() {
        return address;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public abstract String getDetails();
}
