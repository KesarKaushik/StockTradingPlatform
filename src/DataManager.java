import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class DataManager {
    private static final String FILE = "data/portfolio.txt";

    public static void save(User user) {
        try {
            Files.createDirectories(Paths.get("data"));
            try (PrintWriter out = new PrintWriter(new FileWriter(FILE))) {
                out.println("USERNAME|" + user.getUsername());
                out.println("CASH|" + user.getCashBalance());
                out.println("REALIZED|" + user.getRealizedProfitLoss());

                for (var e : user.getPortfolio().getHoldings().entrySet()) {
                    Portfolio.Holding h = e.getValue();
                    out.println("HOLDING|" + e.getKey() + "|" + h.getQuantity()
                            + "|" + h.getTotalCost());
                }

                for (Transaction t : user.getTransactions()) {
                    out.println("TRANSACTION|" + t.toFileString());
                }
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    public static User load() {
        Path path = Paths.get(FILE);
        if (!Files.exists(path)) return null;

        try {
            String username = "Guest";
            double cash = 100000.0;
            double realized = 0.0;
            Portfolio portfolio = new Portfolio();
            ArrayList<Transaction> transactions = new ArrayList<>();

            for (String line : Files.readAllLines(path)) {
                if (line.startsWith("USERNAME|")) username = line.substring(9);
                else if (line.startsWith("CASH|")) cash = Double.parseDouble(line.substring(5));
                else if (line.startsWith("REALIZED|")) realized = Double.parseDouble(line.substring(9));
                else if (line.startsWith("HOLDING|")) {
                    String[] p = line.split("\\|");
                    if (p.length == 4) {
                        portfolio.buy(p[1], Integer.parseInt(p[2]), Double.parseDouble(p[3]));
                    }
                } else if (line.startsWith("TRANSACTION|")) {
                    transactions.add(Transaction.fromFileString(line.substring(12)));
                }
            }

            System.out.println("Saved data loaded successfully.");
            return new User(username, cash, portfolio, transactions, realized);
        } catch (Exception e) {
            System.out.println("Save file could not be loaded. Starting a new account.");
            return null;
        }
    }
}
