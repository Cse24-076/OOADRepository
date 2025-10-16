public class ChequeAccount extends Account implements Withdraw {
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
        } else {
            System.out.println("❌ Invalid or insufficient withdrawal.");
        }
    }

    @Override
    public void payInterest() {
        System.out.println("Cheque Account does not earn interest.");
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
}
