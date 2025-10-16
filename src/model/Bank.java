package model;

import java.util.*;

public class Bank {
    private final List<Customer> customers = new ArrayList<>();
    private int accountCounter = 1000;

    // 🔐 Login with safe string comparison
    public Customer login(String id, String password) {
        if (id == null || password == null) return null;
        for (Customer c : customers) {
            if (c.getCustomerID().trim().equalsIgnoreCase(id.trim()) &&
                    c.loginWithPassword(password.trim())) {
                return c;
            }
        }
        return null;
    }

    // ✅ Register customer if ID is unique
    public boolean registerCustomer(Customer customer) {
        if (customer == null || findCustomerByID(customer.getCustomerID()) != null) {
            return false;
        }
        customers.add(customer);
        return true;
    }

    // 🏦 Open account based on type
    public boolean openAccount(Customer cust, int choice, String branch, double deposit,
                               String employer, String employerAddress) {
        if (cust == null || branch == null || branch.isBlank()) return false;

        String accNum = generateAccountNumber();

        return switch (choice) {
            case 1 -> {
                cust.addAccount(new SavingsAccount(accNum, 0, branch));
                yield true;
            }
            case 2 -> {
                if (deposit >= InvestmentAccount.MIN_OPENING_BALANCE) {
                    cust.addAccount(new InvestmentAccount(accNum, deposit, branch));
                    yield true;
                }
                yield false;
            }
            case 3 -> {
                if (cust instanceof IndividualCustomer &&
                        employer != null && !employer.isBlank() &&
                        employerAddress != null && !employerAddress.isBlank()) {
                    cust.addAccount(new ChequeAccount(accNum, 0, branch, employer, employerAddress));
                    yield true;
                }
                yield false;
            }
            default -> false;
        };
    }

    // 💰 Deposit funds
    public boolean deposit(Customer cust, String accNum, double amount) {
        if (cust == null || accNum == null || accNum.isBlank() || amount <= 0) return false;
        Account target = findAccountByNumber(cust.getAccounts(), accNum);
        if (target == null) return false;
        target.deposit(amount);
        return true;
    }

    // 💸 Withdraw funds
    public boolean withdraw(Customer cust, String accNum, double amount) {
        if (cust == null || accNum == null || accNum.isBlank() || amount <= 0) return false;
        Account target = findAccountByNumber(cust.getAccounts(), accNum);
        if (target instanceof Withdraw w) {
            w.withdraw(amount);
            return true;
        }
        return false;
    }

    // 📈 Apply interest to all accounts
    public boolean payInterest(Customer cust) {
        if (cust == null || cust.getAccounts().isEmpty()) return false;
        cust.getAccounts().forEach(Account::payInterest);
        return true;
    }

    // 📋 Get account summaries
    public List<String> getAccountSummaries(Customer cust) {
        List<String> summaries = new ArrayList<>();
        for (Account acc : cust.getAccounts()) {
            summaries.add(acc.getAccountNumber() + " - " +
                    acc.getAccountType() + " | Balance: BWP" + acc.getBalance());
        }
        return summaries;
    }

    // 🔍 Find account by number
    public Account findAccountByNumber(List<Account> accounts, String accNum) {
        if (accNum == null) return null;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accNum.trim())) {
                return acc;
            }
        }
        return null;
    }

    // 🔍 Find customer by ID
    public Customer findCustomerByID(String id) {
        if (id == null) return null;
        for (Customer c : customers) {
            if (c.getCustomerID().equalsIgnoreCase(id.trim())) {
                return c;
            }
        }
        return null;
    }

    // 🔢 Generate unique account number
    public String generateAccountNumber() {
        return "ACC" + (accountCounter++);
    }

    // 📦 Get all accounts
    public List<Account> getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        for (Customer c : customers) {
            allAccounts.addAll(c.getAccounts());
        }
        return allAccounts;
    }

    // 📦 Get all customers
    public List<Customer> getAllCustomers() {
        return customers;
    }
}
