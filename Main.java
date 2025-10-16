import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM ===");
        System.out.println("Welcome to the Bank Management System\n");

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Register New Customer");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit System");
            System.out.print("Choose option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> registerCustomer();
                case 2 -> loginMenu();
                case 3 -> {
                    System.out.print("Are you sure you want to exit? (Y/N): ");
                    String confirm = sc.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        System.out.println("👋 Thank you for using our Banking System. Goodbye!");
                        return;
                    }
                }
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void registerCustomer() {
        while (true) {
            System.out.println("\n=== REGISTER NEW CUSTOMER ===");
            System.out.println("1. Individual Customer");
            System.out.println("2. Company Customer");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose type: ");
            int type = getIntInput();

            if (type == 0) return;

            System.out.print("Enter Customer ID (or 0 to cancel): ");
            String id = sc.nextLine();
            if (id.equals("0")) continue;

            System.out.print("Enter Password (or 0 to cancel): ");
            String password = sc.nextLine();
            if (password.equals("0")) continue;

            System.out.print("Enter Address (or 0 to cancel): ");
            String address = sc.nextLine();
            if (address.equals("0")) continue;

            Customer newCustomer = null;

            if (type == 1) {
                System.out.print("Enter First Name (or 0 to cancel): ");
                String fname = sc.nextLine();
                if (fname.equals("0")) continue;

                System.out.print("Enter Surname (or 0 to cancel): ");
                String sname = sc.nextLine();
                if (sname.equals("0")) continue;

                newCustomer = new IndividualCustomer(id, password, address, fname, sname);
            } else if (type == 2) {
                System.out.print("Enter Company Name (or 0 to cancel): ");
                String company = sc.nextLine();
                if (company.equals("0")) continue;

                System.out.print("Enter Contact Person (or 0 to cancel): ");
                String contact = sc.nextLine();
                if (contact.equals("0")) continue;

                newCustomer = new CompanyCustomer(id, password, address, company, contact);
            } else {
                System.out.println("❌ Invalid customer type.");
                continue;
            }

            bank.addCustomer(newCustomer);
            System.out.println("✅ Customer registered successfully!");
            return;
        }
    }

    private static void loginMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER LOGIN ===");
            System.out.print("Enter Customer ID (or 0 to go back): ");
            String id = sc.nextLine();
            if (id.equals("0")) return;

            System.out.print("Enter Password (or 0 to go back): ");
            String password = sc.nextLine();
            if (password.equals("0")) return;

            Customer customer = bank.login(id, password);
            if (customer != null) {
                System.out.println("\n✅ Login successful!");
                System.out.println("Welcome " + customer.getDetails());
                customerMenu(customer);
                return;
            } else {
                System.out.println("❌ Login failed. Invalid Customer ID or Password.");
            }
        }
    }

    private static void customerMenu(Customer customer) {
        int choice;
        do {
            System.out.println("\n=== CUSTOMER DASHBOARD ===");
            System.out.println("Customer: " + customer.getDetails());
            System.out.println("Accounts: " + customer.getAccounts().size());
            System.out.println("==========================");
            System.out.println("1. Open New Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. View My Accounts");
            System.out.println("5. Pay Monthly Interest");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            choice = getIntInput();

            switch (choice) {
                case 1 -> openAccountMenu(customer);
                case 2 -> bank.deposit(customer);
                case 3 -> bank.withdraw(customer);
                case 4 -> bank.displayCustomerAccounts(customer);
                case 5 -> bank.payInterest(customer);
                case 6 -> System.out.println("🔒 Logging out...");
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        } while (choice != 6);
    }

    private static void openAccountMenu(Customer customer) {
        while (true) {
            System.out.println("\n=== OPEN NEW ACCOUNT ===");
            System.out.println("1. Savings Account");
            System.out.println("2. Investment Account");
            System.out.println("3. Cheque Account");
            System.out.println("0. Back to Dashboard");
            System.out.print("Choose account type: ");
            int choice = getIntInput();

            if (choice == 0) return;

            bank.openAccount(customer, choice);
        }
    }

    private static int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.println("❌ Please enter a valid number.");
            sc.nextLine();
        }
        int value = sc.nextInt();
        sc.nextLine(); // clear buffer
        return value;
    }
}
