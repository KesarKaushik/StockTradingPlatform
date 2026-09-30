public class Stock {
    private final String symbol;
    private final String companyName;
    private double currentPrice;
    private double priceChangePercent;

    public Stock(String symbol, String companyName, double currentPrice) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.priceChangePercent = 0.0;
    }

    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public double getCurrentPrice() { return currentPrice; }
    public double getPriceChangePercent() { return priceChangePercent; }

    public void updatePrice(double newPrice, double changePercent) {
        currentPrice = Math.max(0.01, newPrice);
        priceChangePercent = changePercent;
    }
}
