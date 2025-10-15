public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.0005; // 0.05% monthly

    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("❌ Withdrawals not allowed from Savings Account.");
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
        return "Savings";
    }

    @Override
    public String toString() {
        return "Savings Account | " + super.toString() + 
               " | Interest: 0.05% monthly | No withdrawals";
    }
}
