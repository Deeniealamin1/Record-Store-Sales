# Record Store Sales - Transaction Tracker

A Java-based console application designed to manage and track financial transactions. This tool allows users to record deposits and payments, maintain a persistent ledger, and generate detailed financial reports.

## 🚀 Features

* **Transaction Management:** Record deposits and payments with automatic timestamps.
* **Persistent Storage:** Saves all data to a `transaction.csv` file using pipe-delimited formatting.
* **Interactive Ledger:** * View all recorded transactions.
    * Filter by Deposits or Payments.
* **Advanced Reporting:**
    * **Time-based:** Month-to-Date, Previous Month, Year-to-Date, and Previous Year.
    * **Vendor Search:** Filter history by specific vendor names.

## 🛠️ Technical Overview

* **Language:** Java
* **Package Structure:** `com.pluralsight`
* **Data Handling:** Uses `java.time` for precise scheduling and `java.io` for robust file management.
* **Architecture:** Follows Object-Oriented Programming principles with separate classes for logic, UI, and data modeling.

## 📂 File Structure

* `Main.java`: The application entry point.
* `HomeScreen.java`: Handles the primary menu and user input for new entries.
* `Ledger.java`: Contains the logic for data retrieval, filtering, and reporting.
* `Transaction.java`: The POJO (Plain Old Java Object) representing the transaction model.
* `transaction.csv`: The flat-file database (automatically generated).

## ⚙️ How to Run

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/YourUsername/record-store-sales.git](https://github.com/YourUsername/record-store-sales.git)
