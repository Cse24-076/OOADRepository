public class InvestmentAccount extends Account implements InterestBearing {
    public static final double MIN_OPENING_BALANCE = 500.00;
    private static final double INTEREST_RATE = 0.075;

    public InvestmentAccount(String accountNumber, double balance, String branch) {
        super(accountNumber, Math.max(balance, MIN_OPENING_BALANCE), branch);
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
        return "Investment";
    }
}
