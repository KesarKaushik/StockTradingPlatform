import java.util.ArrayList;

public class User {
    private final String username;
    private double cashBalance;
    private final Portfolio portfolio;
    private final ArrayList<Transaction> transactions;
    private double realizedProfitLoss;

    public User(String username) {
        this(username, 100000.0, new Portfolio(), new ArrayList<>(), 0.0);
    }

    public User(String username, double cashBalance, Portfolio portfolio,
                ArrayList<Transaction> transactions, double realizedProfitLoss) {
        this.username = username;
        this.cashBalance = cashBalance;
        this.portfolio = portfolio;
        this.transactions = transactions;
        this.realizedProfitLoss = realizedProfitLoss;
    }

    public String getUsername() { return username; }
    public double getCashBalance() { return cashBalance; }
    public Portfolio getPortfolio() { return portfolio; }
    public ArrayList<Transaction> getTransactions() { return transactions; }
    public double getRealizedProfitLoss() { return realizedProfitLoss; }

    public void addCash(double amount) { cashBalance += amount; }
    public void subtractCash(double amount) { cashBalance -= amount; }
    public void addTransaction(Transaction t) { transactions.add(t); }
    public void addRealizedProfitLoss(double amount) { realizedProfitLoss += amount; }
}
