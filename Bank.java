import java.util.*;

public class Bank {
    private List<Customer> customers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int accountCounter = 1000;

    public Bank() {
        customers.add(new IndividualCustomer("C001", "12345", "Gaborone", "John", "Mogomotsi"));
        customers.add(new IndividualCustomer("C002", "abcde", "Francistown", "Mary", "Dube"));
        // Optional: Add company customers if needed
        // customers.add(new CompanyCustomer("C003", "pass123", "Maun", "TechBots", "Kabelo"));
    }

    public Customer login(String id, String password) {
        for (Customer c : customers) {
            if (c.customerID.equals(id) && c.loginWithPassword(password)) {
                return c;
            }
        }
        return null;
    }

    public void openAccount(Customer cust) {
        System.out.println("\n=== OPEN NEW ACCOUNT ===");
        System.out.println("1. Savings Account (0.05% interest, NO withdrawals)");
        System.out.println("2. Investment Account (5% interest, min BWP500 deposit)");
        System.out.println("3. Cheque Account (NO interest, requires employment info)");
        System.out.print("Choose account type: ");

        int choice = getIntInput();

        System.out.print("Enter branch name: ");
        String branch = sc.nextLine();

        String accNum = generateAccountNumber();

        switch (choice) {
            case 1 -> {
                SavingsAccount savings = new SavingsAccount(accNum, 0, branch);
                cust.addAccount(savings);
                System.out.println("✅ SAVINGS ACCOUNT " + accNum + " CREATED!");
            }
            case 2 -> {
                System.out.print("Enter initial deposit (minimum BWP" + InvestmentAccount.MIN_OPENING_BALANCE + "): ");
                double deposit = getDoubleInput();

                if (deposit >= InvestmentAccount.MIN_OPENING_BALANCE) {
                    InvestmentAccount investment = new InvestmentAccount(accNum, deposit, branch);
                    cust.addAccount(investment);
                    System.out.println("✅ INVESTMENT ACCOUNT " + accNum + " CREATED!");
                } else {
                    System.out.println("❌ Minimum deposit is BWP" + InvestmentAccount.MIN_OPENING_BALANCE);
                }
            }
            case 3 -> {
                if (cust instanceof IndividualCustomer) {
                    System.out.print("Enter employer name: ");
                    String employer = sc.nextLine();
                    System.out.print("Enter employer address: ");
                    String empAddress = sc.nextLine();

                    ChequeAccount cheque = new ChequeAccount(accNum, 0, branch, employer, empAddress);
                    cust.addAccount(cheque);
                    System.out.println("✅ CHEQUE ACCOUNT " + accNum + " CREATED!");
                } else {
                    System.out.println("❌ Cheque accounts are only for individual customers.");
                }
            }
            default -> System.out.println("❌ Invalid account type.");
        }
    }

    public void deposit(Customer cust) {
        List<Account> accounts = cust.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts available for deposit.");
            return;
        }

        System.out.println("\n=== MAKE DEPOSIT ===");
        displayCustomerAccounts(cust);

        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();

        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNum)) {
                System.out.print("Enter deposit amount: BWP");
                double amount = getDoubleInput();

                if (amount <= 0) {
                    System.out.println("❌ Amount must be greater than zero.");
                    return;
                }

                acc.deposit(amount);
                System.out.println("✅ Deposit complete. New balance: BWP" + acc.getBalance());
                return;
            }
        }
        System.out.println("❌ Account not found: " + accNum);
    }

    public void withdraw(Customer cust) {
        List<Account> accounts = cust.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts available for withdrawal.");
            return;
        }

        System.out.println("\n=== MAKE WITHDRAWAL ===");
        displayCustomerAccounts(cust);

        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();

        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNum)) {
                System.out.print("Enter withdrawal amount: BWP");
                double amount = getDoubleInput();

                if (amount <= 0) {
                    System.out.println("❌ Amount must be greater than zero.");
                    return;
                }

                acc.withdraw(amount);
                System.out.println("✅ Withdrawal complete. New balance: BWP" + acc.getBalance());
                return;
            }
        }
        System.out.println("❌ Account not found: " + accNum);
    }

    public void payInterest(Customer cust) {
        List<Account> accounts = cust.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts available for interest payment.");
            return;
        }

        System.out.println("\n=== APPLY MONTHLY INTEREST ===");
        for (Account acc : accounts) {
            acc.payInterest();
        }
        System.out.println("✅ Interest applied to all eligible accounts.");
    }

    public void displayCustomerAccounts(Customer cust) {
        List<Account> accounts = cust.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts found.");
            return;
        }

        System.out.println("\n=== YOUR ACCOUNTS ===");
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.println((i + 1) + ". " + acc.getAccountNumber() + " - " +
                               acc.getAccountType() + " Account | Balance: BWP" + acc.getBalance());
        }
    }

    private String generateAccountNumber() {
        return "ACC" + (accountCounter++);
    }

    private int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("❌ Please enter a valid number.");
            sc.nextLine();
        }
        int value = sc.nextInt();
        sc.nextLine(); // clear buffer
        return value;
    }

    private double getDoubleInput() {
        while (!sc.hasNextDouble()) {
            System.out.println("❌ Please enter a valid amount.");
            sc.nextLine();
        }
        double value = sc.nextDouble();
        sc.nextLine(); // clear buffer
        return value;
    }
}