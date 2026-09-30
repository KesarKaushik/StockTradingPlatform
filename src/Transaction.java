import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final String symbol;
    private final int quantity;
    private final double price;
    private final double totalAmount;
    private final LocalDateTime dateTime;

    public Transaction(String type, String symbol, int quantity, double price, double totalAmount) {
        this(type, symbol, quantity, price, totalAmount, LocalDateTime.now());
    }

    public Transaction(String type, String symbol, int quantity, double price,
                       double totalAmount, LocalDateTime dateTime) {
        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
    }

    public String getType() { return type; }
    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDateTime getDateTime() { return dateTime; }

    public String toFileString() {
        return type + "," + symbol + "," + quantity + "," + price + "," +
               totalAmount + "," + dateTime;
    }

    public static Transaction fromFileString(String line) {
        String[] p = line.split(",", -1);
        if (p.length != 6) throw new IllegalArgumentException("Invalid transaction");
        return new Transaction(
            p[0], p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3]),
            Double.parseDouble(p[4]), LocalDateTime.parse(p[5])
        );
    }

    public String display() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format("%-6s %-8s %8d ₹%12.2f ₹%12.2f %-18s",
                type, symbol, quantity, price, totalAmount, dateTime.format(f));
    }
}
