import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TradingPlatform {
    private final User user;
    private final HashMap<String, Stock> market = new HashMap<>();
    private final Random random = new Random();

    public TradingPlatform(User user) {
        this.user = user;
        loadDefaultMarket();
    }

    private void loadDefaultMarket() {
        addStock(new Stock("ALPH", "Alpha Tech", 1250.00));
        addStock(new Stock("BETA", "Beta Motors", 860.00));
        addStock(new Stock("CYBR", "Cyber Systems", 1740.00));
        addStock(new Stock("DGEN", "Digital Energy", 920.00));
        addStock(new Stock("EVRX", "Everex Labs", 640.00));
        addStock(new Stock("FINT", "FinTech Works", 1125.00));
        addStock(new Stock("GRNX", "GreenNext", 780.00));
        addStock(new Stock("NOVA", "Nova Retail", 510.00));
    }

    private void addStock(Stock stock) {
        market.put(stock.getSymbol(), stock);
    }

    public HashMap<String, Stock> getMarket() { return market; }

    public void displayMarket() {
        System.out.println("\n========== SIMULATED MARKET DATA ==========");
        System.out.printf("%-8s %-22s %15s %12s%n", "SYMBOL", "COMPANY", "PRICE", "CHANGE");
        System.out.println("--------------------------------------------------------------");
        for (Stock s : market.values()) {
            System.out.printf("%-8s %-22s ₹%13.2f %11.2f%%%n",
                    s.getSymbol(), s.getCompanyName(), s.getCurrentPrice(),
                    s.getPriceChangePercent());
        }
        System.out.println("(All prices are fictional and simulated for education.)");
    }

    public boolean buy(String symbol, int quantity) {
        Stock stock = market.get(symbol.toUpperCase());
        if (stock == null || quantity <= 0) return false;

        double total = stock.getCurrentPrice() * quantity;
        if (user.getCashBalance() < total) return false;

        user.subtractCash(total);
        user.getPortfolio().buy(stock.getSymbol(), quantity, total);
        user.addTransaction(new Transaction("BUY", stock.getSymbol(), quantity,
                stock.getCurrentPrice(), total));
        return true;
    }

    public boolean sell(String symbol, int quantity) {
        Stock stock = market.get(symbol.toUpperCase());
        if (stock == null || quantity <= 0 ||
                user.getPortfolio().getQuantity(stock.getSymbol()) < quantity) {
            return false;
        }

        double total = stock.getCurrentPrice() * quantity;
        Portfolio.Holding h = user.getPortfolio().getHoldings().get(stock.getSymbol());
        double costBasis = h.getAveragePurchasePrice() * quantity;

        user.getPortfolio().sell(stock.getSymbol(), quantity, stock.getCurrentPrice());
        user.addCash(total);
        user.addRealizedProfitLoss(total - costBasis);
        user.addTransaction(new Transaction("SELL", stock.getSymbol(), quantity,
                stock.getCurrentPrice(), total));
        return true;
    }

    public void simulateMarketUpdate() {
        for (Stock stock : market.values()) {
            double change = -5.0 + random.nextDouble() * 10.0;
            double newPrice = stock.getCurrentPrice() * (1 + change / 100.0);
            stock.updatePrice(newPrice, change);
        }
        System.out.println("\nMarket update complete. Prices are still simulated values.");
    }

    public void displayPortfolio() {
        Portfolio p = user.getPortfolio();
        System.out.println("\n================ PORTFOLIO ================");
        if (p.getHoldings().isEmpty()) {
            System.out.println("No stocks currently held.");
        } else {
            System.out.printf("%-8s %8s %15s %15s %15s%n",
                    "SYMBOL", "SHARES", "AVG BUY", "CURRENT", "VALUE");
            System.out.println("----------------------------------------------------------------");
            for (Map.Entry<String, Portfolio.Holding> e : p.getHoldings().entrySet()) {
                Stock s = market.get(e.getKey());
                Portfolio.Holding h = e.getValue();
                double value = h.getQuantity() * s.getCurrentPrice();
                System.out.printf("%-8s %8d ₹%13.2f ₹%13.2f ₹%13.2f%n",
                        e.getKey(), h.getQuantity(), h.getAveragePurchasePrice(),
                        s.getCurrentPrice(), value);
            }
        }

        double current = p.getCurrentValue(market);
        System.out.printf("%nTotal invested:       ₹%.2f%n", p.getTotalInvested());
        System.out.printf("Current portfolio:    ₹%.2f%n", current);
        System.out.printf("Unrealized P/L:       ₹%.2f%n", p.getUnrealizedProfitLoss(market));
        System.out.printf("Realized P/L:         ₹%.2f%n", user.getRealizedProfitLoss());
    }

    public void displayAccountSummary() {
        double portfolioValue = user.getPortfolio().getCurrentValue(market);
        System.out.println("\n============== ACCOUNT SUMMARY ==============");
        System.out.println("User: " + user.getUsername());
        System.out.printf("Available cash:       ₹%.2f%n", user.getCashBalance());
        System.out.printf("Portfolio value:      ₹%.2f%n", portfolioValue);
        System.out.printf("Total account value:  ₹%.2f%n",
                user.getCashBalance() + portfolioValue);
        System.out.printf("Realized P/L:         ₹%.2f%n", user.getRealizedProfitLoss());
        System.out.printf("Unrealized P/L:       ₹%.2f%n",
                user.getPortfolio().getUnrealizedProfitLoss(market));
    }

    public void displayTransactions() {
        System.out.println("\n================ TRANSACTIONS ================");
        if (user.getTransactions().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.printf("%-6s %-8s %8s %15s %15s %-18s%n",
                "TYPE", "SYMBOL", "QTY", "PRICE", "TOTAL", "DATE/TIME");
        System.out.println("----------------------------------------------------------------------------");
        ArrayList<Transaction> list = user.getTransactions();
        for (Transaction t : list) System.out.println(t.display());
    }
}
