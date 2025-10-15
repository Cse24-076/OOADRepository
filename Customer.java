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
        System.out.println("✅ Account " + account.getAccountNumber() + " added to customer " + customerID);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
            return;
        }
        System.out.println("\n=== ACCOUNTS FOR " + getDetails() + " ===");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }
    }

    public abstract String getDetails();
}
