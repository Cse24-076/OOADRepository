public class InvestmentAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.05; // 5% monthly
    public static final double MIN_OPENING_BALANCE = 500.00;

    public InvestmentAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, Math.max(balance, MIN_OPENING_BALANCE), branch);
        if (balance < MIN_OPENING_BALANCE) {
            System.out.println("⚠️ Initial deposit less than BWP" + MIN_OPENING_BALANCE + 
                               ". Account opened with minimum required balance.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("✅ Withdrawn BWP" + amount + " from " + accountNumber);
            System.out.println("   New balance: BWP" + balance);
        } else if (amount > balance) {
            System.out.println("❌ Insufficient funds. Available: BWP" + balance);
        } else {
            System.out.println("❌ Invalid withdrawal amount.");
        }
    }

    @Override
    public void payInterest() {
        double interest = calculateInterest();
        balance += interest;
        System.out.println("💰 Interest of BWP" + String.format("%.2f", interest) + 
                           " applied to " + accountNumber);
        System.out.println("   New balance: BWP" + String.format("%.2f", balance));
    }

    @Override
    public double calculateInterest() {
    return balance * INTEREST_RATE;
}

    @Override
    public String getAccountType() {
        return "Investment";
    }

    @Override
    public String toString() {
        return "Investment Account | " + super.toString() + 
               " | Interest: 5% monthly | Min deposit: BWP" + MIN_OPENING_BALANCE;
    }
}
