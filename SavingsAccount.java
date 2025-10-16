public class SavingsAccount extends Account implements InterestBearing {
    private static final double INTEREST_RATE = 0.025;

    public SavingsAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, balance, branch);
    }

    @Override
    public void payInterest() {
        balance += calculateInterest();
    }

    @Override
    public double calculateInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
