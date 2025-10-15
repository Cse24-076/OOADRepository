import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM ===");
        System.out.println("Welcome to the Bank Management System\n");

        // Preloaded customers for testing
        System.out.println("Preloaded Customers:");
        System.out.println("Customer ID: C001, Password: 12345 (John Mogomotsi)");
        System.out.println("Customer ID: C002, Password: abcde (Mary Dube)");
        System.out.println();

        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Customer Login");
            System.out.println("2. Exit System");
            System.out.print("Choose option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> loginMenu();
                case 2 -> {
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

    private static void loginMenu() {
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.print("Enter Customer ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Customer customer = bank.login(id, password);
        if (customer != null) {
            System.out.println("\n✅ Login successful!");
            System.out.println("Welcome " + customer.getDetails());
            customerMenu(customer);
        } else {
            System.out.println("❌ Login failed. Invalid Customer ID or Password.");
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
                case 1 -> bank.openAccount(customer);
                case 2 -> bank.deposit(customer);
                case 3 -> bank.withdraw(customer);
                case 4 -> bank.displayCustomerAccounts(customer);
                case 5 -> bank.payInterest(customer);
                case 6 -> System.out.println("🔒 Logging out...");
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        } while (choice != 6);
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
