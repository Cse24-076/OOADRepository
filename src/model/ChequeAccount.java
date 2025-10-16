package model;

public class ChequeAccount extends Account implements Withdraw {
    private String employer;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance, String branch, String employer, String employerAddress) {
        super(accountNumber, balance, branch);
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    @Override
    public void payInterest() {
        // Cheque accounts typically don't earn interest
    }

    @Override
    public String getAccountType() {
        return "Cheque";
    }
}
