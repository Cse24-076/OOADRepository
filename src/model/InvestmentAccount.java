package model;

public class InvestmentAccount extends Account implements Withdraw {

    public static final double MIN_OPENING_BALANCE = 1000;

    public InvestmentAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.05;
        deposit(interest);
    }

    @Override
    public String getAccountType() {
        return "Investment";
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }
}
