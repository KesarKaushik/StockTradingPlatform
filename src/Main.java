import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        User loaded = DataManager.load();
        User user;

        if (loaded != null) {
            user = loaded;
            System.out.println("Welcome back, " + user.getUsername() + "!");
        } else {
            System.out.println("==============================================");
            System.out.println("       STOCK TRADING PLATFORM");
            System.out.println("==============================================");
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            if (username.isEmpty()) username = "Guest";
            user = new User(username);
            System.out.println("New account created with virtual cash of ₹100,000.");
        }

        TradingPlatform platform = new TradingPlatform(user);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {
                case 1 -> platform.displayMarket();
                case 2 -> handleBuy(scanner, platform);
                case 3 -> handleSell(scanner, platform);
                case 4 -> platform.displayPortfolio();
                case 5 -> platform.displayTransactions();
                case 6 -> platform.simulateMarketUpdate();
                case 7 -> platform.displayAccountSummary();
                case 8 -> DataManager.save(user);
                case 9 -> {
                    DataManager.save(user);
                    System.out.println("Thank you for using Stock Trading Platform!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-9.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n============== MAIN MENU ==============");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stocks");
        System.out.println("3. Sell Stocks");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Transaction History");
        System.out.println("6. Simulate Market Update");
        System.out.println("7. View Account Summary");
        System.out.println("8. Save Data");
        System.out.println("9. Exit");
        System.out.println("========================================");
    }

    private static void handleBuy(Scanner scanner, TradingPlatform platform) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        int quantity = readInt(scanner, "Enter quantity: ");

        if (platform.buy(symbol, quantity))
            System.out.println("Purchase successful.");
        else
            System.out.println("Purchase failed. Check symbol, quantity, or available balance.");
    }

    private static void handleSell(Scanner scanner, TradingPlatform platform) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        int quantity = readInt(scanner, "Enter quantity: ");

        if (platform.sell(symbol, quantity))
            System.out.println("Sale successful.");
        else
            System.out.println("Sale failed. Check symbol, quantity, or owned shares.");
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) return value;
                System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
