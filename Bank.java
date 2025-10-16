import java.util.*;

public class Bank {
    private List<Customer> customers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int accountCounter = 1000;

    public Customer login(String id, String password) {
        for (Customer c : customers) {
            if (c.customerID.equals(id) && c.loginWithPassword(password)) {
                return c;
            }
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void openAccount(Customer cust, int choice) {
        System.out.println("\n=== OPEN NEW ACCOUNT ===");

        System.out.print("Enter branch name (or 0 to cancel): ");
        String branch = sc.nextLine();
        if (branch.equals("0")) return;

        String accNum = generateAccountNumber();

        switch (choice) {
            case 1 -> {
                SavingsAccount savings = new SavingsAccount(accNum, 0, branch);
                cust.addAccount(savings);
                System.out.println("✅ SAVINGS ACCOUNT " + accNum + " CREATED!");
            }
            case 2 -> {
                System.out.print("Enter initial deposit (minimum BWP" + InvestmentAccount.MIN_OPENING_BALANCE + ", or 0 to cancel): ");
                double deposit = getDoubleInput();
                if (deposit == 0) return;

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
                    System.out.print("Enter employer name (or 0 to cancel): ");
                    String employer = sc.nextLine();
                    if (employer.equals("0")) return;

                    System.out.print("Enter employer address (or 0 to cancel): ");
                    String empAddress = sc.nextLine();
                    if (empAddress.equals("0")) return;

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

        System.out.print("Enter account number (or 0 to cancel): ");
        String accNum = sc.nextLine();
        if (accNum.equals("0")) return;

        Account target = findAccountByNumber(accounts, accNum);
        if (target == null) {
            System.out.println("❌ Account not found: " + accNum);
            return;
        }

        System.out.print("Enter deposit amount (or 0 to cancel): BWP");
        double amount = getDoubleInput();
        if (amount == 0) return;

        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than zero.");
            return;
        }

        target.deposit(amount);
        System.out.println("✅ Deposit complete. New balance: BWP" + target.getBalance());
    }

    public void withdraw(Customer cust) {
        List<Account> accounts = cust.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("❌ No accounts available for withdrawal.");
            return;
        }

        System.out.println("\n=== MAKE WITHDRAWAL ===");
        displayCustomerAccounts(cust);

        System.out.print("Enter account number (or 0 to cancel): ");
        String accNum = sc.nextLine();
        if (accNum.equals("0")) return;

        Account target = findAccountByNumber(accounts, accNum);
        if (target == null) {
            System.out.println("❌ Account not found: " + accNum);
            return;
        }

        System.out.print("Enter withdrawal amount (or 0 to cancel): BWP");
        double amount = getDoubleInput();
        if (amount == 0) return;

        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than zero.");
            return;
        }

        if (target instanceof Withdraw) {
            ((Withdraw) target).withdraw(amount);
            System.out.println("✅ Withdrawal complete. New balance: BWP" + target.getBalance());
        } else {
            System.out.println("❌ You cannot withdraw from this account.");
        }
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

    private Account findAccountByNumber(List<Account> accounts, String accNum) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equalsIgnoreCase(accNum.trim())) {
                return acc;
            }
        }
        return null;
    }

    private String generateAccountNumber() {
        return "ACC" + (accountCounter++);
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
