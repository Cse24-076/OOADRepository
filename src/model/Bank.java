package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers = new ArrayList<>();
    private int accountCounter = 1000;
    private static final String CUSTOMERS_FILE = "customers.txt";
    private static final String ACCOUNTS_FILE = "accounts.txt";

    public Bank() {
        loadCustomersFromFile();
        loadAccountsFromFile();
    }

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

    public boolean registerCustomer(Customer customer) {
        if (customer == null || findCustomerByID(customer.getCustomerID()) != null) {
            return false;
        }
        customers.add(customer);
        saveCustomersToFile();
        return true;
    }

    public boolean openAccount(Customer cust, int choice, String branch, double deposit,
                               String employer, String employerAddress) {
        if (cust == null || branch == null || branch.isBlank()) return false;

        String accNum = generateAccountNumber();
        boolean success = false;

        switch (choice) {
            case 1:
                cust.addAccount(new SavingsAccount(accNum, 0, branch));
                success = true;
                break;
            case 2:
                if (deposit >= InvestmentAccount.MIN_OPENING_BALANCE) {
                    cust.addAccount(new InvestmentAccount(accNum, deposit, branch));
                    success = true;
                }
                break;
            case 3:
                if (cust instanceof IndividualCustomer &&
                        employer != null && !employer.isBlank() &&
                        employerAddress != null && !employerAddress.isBlank()) {
                    cust.addAccount(new ChequeAccount(accNum, 0, branch, employer, employerAddress));
                    success = true;
                }
                break;
            default:
                success = false;
        }

        if (success) {
            saveAccountsToFile();
        }
        return success;
    }

    public boolean deposit(Customer cust, String accNum, double amount) {
        if (cust == null || accNum == null || accNum.isBlank() || amount <= 0) return false;
        Account target = findAccountByNumber(cust.getAccounts(), accNum);
        if (target == null) return false;
        target.deposit(amount);
        saveAccountsToFile();
        return true;
    }

    public boolean withdraw(Customer cust, String accNum, double amount) {
        if (cust == null || accNum == null || accNum.isBlank() || amount <= 0) return false;
        Account target = findAccountByNumber(cust.getAccounts(), accNum);
        if (target != null && target.withdraw(amount)) {
            saveAccountsToFile();
            return true;
        }
        return false;
    }

    public boolean payInterest(Customer cust) {
        if (cust == null || cust.getAccounts().isEmpty()) return false;
        cust.getAccounts().forEach(Account::payInterest);
        saveAccountsToFile();
        return true;
    }

    public List<String> getAccountSummaries(Customer cust) {
        List<String> summaries = new ArrayList<>();
        if (cust == null) return summaries;
        for (Account acc : cust.getAccounts()) {
            summaries.add(acc.getSummary());
        }
        return summaries;
    }

    public List<String> getCustomerTransactions(Customer cust) {
        List<String> transactions = new ArrayList<>();
        if (cust == null) return transactions;
        for (Account acc : cust.getAccounts()) {
            for (String t : acc.getTransactions()) {
                transactions.add(acc.getAccountNumber() + ": " + t);
            }
        }
        return transactions;
    }

    public Account findAccountByNumber(List<Account> accounts, String accNum) {
        if (accNum == null) return null;
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accNum.trim())) return acc;
        }
        return null;
    }

    public Customer findCustomerByID(String id) {
        if (id == null) return null;
        for (Customer c : customers) {
            if (c.getCustomerID().equalsIgnoreCase(id.trim())) return c;
        }
        return null;
    }

    public String generateAccountNumber() {
        return "ACC" + (accountCounter++);
    }

    public List<Account> getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        for (Customer c : customers) allAccounts.addAll(c.getAccounts());
        return allAccounts;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    // FILE PERSISTENCE METHODS
    private void saveCustomersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers) {
                writer.println(customer.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving customers: " + e.getMessage());
        }
    }

    private void saveAccountsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Customer customer : customers) {
                for (Account account : customer.getAccounts()) {
                    writer.println(customer.getCustomerID() + "|" + account.toFileString());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    private void loadCustomersFromFile() {
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = parseCustomerFromLine(line);
                if (customer != null) {
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading customers: " + e.getMessage());
        }
    }

    private void loadAccountsFromFile() {
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 2);
                if (parts.length == 2) {
                    String customerID = parts[0];
                    String accountData = parts[1];

                    Customer customer = findCustomerByID(customerID);
                    if (customer != null) {
                        Account account = Account.fromString(accountData, customerID);
                        if (account != null) {
                            customer.addAccount(account);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    private Customer parseCustomerFromLine(String line) {
        if (line.startsWith("INDIVIDUAL|")) {
            return IndividualCustomer.fromString(line);
        } else if (line.startsWith("COMPANY|")) {
            return CompanyCustomer.fromString(line);
        }
        return null;
    }
}