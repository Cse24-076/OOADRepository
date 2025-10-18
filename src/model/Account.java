package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected List<String> transactions = new ArrayList<>();

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

    public List<String> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(String.format("Deposited BWP %.2f", amount));
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        transactions.add(String.format("Withdrew BWP %.2f", amount));
        return true;
    }

    public abstract void payInterest();
    public abstract String getAccountType();

    public int getType() {
        return switch (getAccountType()) {
            case "Savings" -> 1;
            case "Investment" -> 2;
            case "Cheque" -> 3;
            default -> 0;
        };
    }

    public String getSummary() {
        return String.format("%s | %s | Balance: BWP %.2f | Branch: %s",
                accountNumber, getAccountType(), balance, branch);
    }

    // File persistence methods
    public abstract String toFileString();

    public static Account fromString(String data, String customerID) {
        String[] parts = data.split("\\|");
        if (parts.length < 4) return null;

        String type = parts[0];
        String accNum = parts[1];
        double balance = Double.parseDouble(parts[2]);
        String branch = parts[3];

        switch (type) {
            case "SAVINGS":
                return new SavingsAccount(accNum, balance, branch);
            case "INVESTMENT":
                return new InvestmentAccount(accNum, balance, branch);
            case "CHEQUE":
                if (parts.length >= 6) {
                    return new ChequeAccount(accNum, balance, branch, parts[4], parts[5]);
                }
                return null;
            default:
                return null;
        }
    }
}