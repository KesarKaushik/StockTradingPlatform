# Stock Trading Platform

A beginner-friendly **Java console-based stock trading simulator** created for educational purposes.

> **Important:** This project uses fictional stock prices and virtual money only. It does not connect to real stock markets, trading APIs, banks, or payment systems.

## Features

- User profile with ₹100,000 virtual starting balance
- 8 fictional sample stocks
- View simulated market prices and price changes
- Buy and sell stocks
- Portfolio and account summary
- Average purchase price
- Total invested amount
- Current portfolio value
- Realized and unrealized profit/loss
- Transaction history with date/time
- Simulated market price updates
- File I/O for saving and loading portfolio data
- Input validation and transaction checks

## Technologies

- Java
- Object-Oriented Programming
- ArrayList
- HashMap
- Scanner
- Java File I/O
- No external libraries

## Project Structure

```text
StockTradingPlatform/
├── src/
│   ├── Stock.java
│   ├── User.java
│   ├── Transaction.java
│   ├── Portfolio.java
│   ├── TradingPlatform.java
│   ├── DataManager.java
│   └── Main.java
├── data/
│   └── portfolio.txt
├── README.md
└── .gitignore
```

## Class Responsibilities

- **Stock.java** — Stores stock symbol, company name and simulated price.
- **User.java** — Stores username, virtual cash, portfolio and transactions.
- **Transaction.java** — Represents buy/sell transactions.
- **Portfolio.java** — Manages holdings and portfolio calculations.
- **TradingPlatform.java** — Handles market display, buying, selling and simulation.
- **DataManager.java** — Saves and loads user data using text file I/O.
- **Main.java** — Provides the menu-driven console interface.

## Requirements

Install Java JDK 17 or later.

Check Java:

```bash
java -version
javac -version
```

## Compile

From the project root:

### Windows PowerShell / CMD

```bash
javac -d out src\*.java
```

## Run

```bash
java -cp out Main
```

## Sample Flow

```text
1. View Market Data
2. Buy Stocks
3. Sell Stocks
4. View Portfolio
5. View Transaction History
6. Simulate Market Update
7. View Account Summary
8. Save Data
9. Exit

Enter your choice: 2
Enter stock symbol: ALPH
Enter quantity: 5
Purchase successful.
```

## Educational Note

All stock names and prices are fictional. The application is a programming exercise demonstrating OOP, collections, calculations, validation and file handling.
