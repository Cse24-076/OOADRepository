public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;

    public Account(String accountNumber, double balance, String branch) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getBranch() {
        return branch;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("✅ Deposit successful. New balance: BWP" + balance);
        } else {
            System.out.println("❌ Invalid deposit amount.");
        }
    }

    public abstract void withdraw(double amount);
    public abstract void payInterest();
    public abstract String getAccountType();

    @Override
    public String toString() {
        return accountNumber + " | Balance: BWP" + balance + " | Branch: " + branch;
    }
}
