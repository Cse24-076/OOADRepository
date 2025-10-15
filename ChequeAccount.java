public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance, String branch, 
                         String employerName, String employerAddress) {
        super(accountNumber, balance, branch);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("✅ Withdrawn BWP" + amount + " from " + accountNumber);
            System.out.println("   New balance: BWP" + balance);
        } else if (amount > balance) {
            System.out.println("❌ Insufficient funds. Available: BWP" + balance);
        } else {
            System.out.println("❌ Invalid withdrawal amount.");
        }
    }

    @Override
    public void payInterest() {
        System.out.println("ℹ️ Cheque Account " + accountNumber + " does not earn interest.");
    }

    @Override
    public String getAccountType() {
        return "Cheque";
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    @Override
    public String toString() {
        return "Cheque Account | " + super.toString() + 
               " | Employer: " + employerName + " | No interest";
    }
}
