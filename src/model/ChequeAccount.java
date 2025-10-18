package model;

public class ChequeAccount extends Account {
    private String employer;
    private String employerAddress;

    public ChequeAccount(String accountNumber, double balance, String branch, String employer, String employerAddress) {
        super(accountNumber, balance, branch);
        this.employer = employer;
        this.employerAddress = employerAddress;
    }

    @Override
    public void payInterest() {
        // Cheque accounts typically don't earn interest
    }

    @Override
    public String getAccountType() {
        return "Cheque";
    }

    public String getEmployer() {
        return employer;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    @Override
    public String toFileString() {
        return String.format("CHEQUE|%s|%.2f|%s|%s|%s",
                accountNumber, balance, branch, employer, employerAddress);
    }
}