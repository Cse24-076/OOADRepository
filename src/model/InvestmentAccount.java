package model;

public class InvestmentAccount extends Account {
    public static final double MIN_OPENING_BALANCE = 500;

    public InvestmentAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void payInterest() {
        double interest = balance * 0.05; // 5% monthly
        deposit(interest);
    }

    @Override
    public String getAccountType() {
        return "Investment";
    }

    @Override
    public String toFileString() {
        return String.format("INVESTMENT|%s|%.2f|%s",
                accountNumber, balance, branch);
    }
}