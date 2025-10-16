package model;

public class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.03;
        deposit(interest);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
