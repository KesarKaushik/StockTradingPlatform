import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    public static class Holding {
        private int quantity;
        private double totalCost;

        public Holding(int quantity, double totalCost) {
            this.quantity = quantity;
            this.totalCost = totalCost;
        }

        public int getQuantity() { return quantity; }
        public double getTotalCost() { return totalCost; }
        public double getAveragePurchasePrice() {
            return quantity == 0 ? 0 : totalCost / quantity;
        }

        public void buy(int qty, double amount) {
            quantity += qty;
            totalCost += amount;
        }

        public void sell(int qty, double salePrice) {
            double avg = getAveragePurchasePrice();
            quantity -= qty;
            totalCost -= avg * qty;
            if (quantity == 0) totalCost = 0;
        }
    }

    private final HashMap<String, Holding> holdings = new HashMap<>();

    public boolean hasStock(String symbol) {
        return holdings.containsKey(symbol) && holdings.get(symbol).getQuantity() > 0;
    }

    public int getQuantity(String symbol) {
        return holdings.containsKey(symbol) ? holdings.get(symbol).getQuantity() : 0;
    }

    public void buy(String symbol, int quantity, double amount) {
        holdings.computeIfAbsent(symbol, k -> new Holding(0, 0));
        holdings.get(symbol).buy(quantity, amount);
    }

    public void sell(String symbol, int quantity, double price) {
        Holding h = holdings.get(symbol);
        if (h == null || h.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough shares.");
        }
        h.sell(quantity, price);
        if (h.getQuantity() == 0) holdings.remove(symbol);
    }

    public Map<String, Holding> getHoldings() {
        return holdings;
    }

    public double getTotalInvested() {
        double total = 0;
        for (Holding h : holdings.values()) total += h.getTotalCost();
        return total;
    }

    public double getCurrentValue(HashMap<String, Stock> market) {
        double total = 0;
        for (Map.Entry<String, Holding> e : holdings.entrySet()) {
            Stock stock = market.get(e.getKey());
            if (stock != null) total += e.getValue().getQuantity() * stock.getCurrentPrice();
        }
        return total;
    }

    public double getUnrealizedProfitLoss(HashMap<String, Stock> market) {
        return getCurrentValue(market) - getTotalInvested();
    }
}
