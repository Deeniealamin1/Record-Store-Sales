package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Ledger {
    private Scanner scanner = new Scanner(System.in);
    private List<Transaction> transactions = new ArrayList<>();

    public Ledger() {
    }

    private void loadTransactions() {
        transactions.clear();
        File file = new File("transaction.csv");
        if (!file.exists()) return;

        try (BufferedReader bufReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            loadTransactions();
            System.out.println("\n--- LEDGER MENU ---");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().toUpperCase().trim();

            switch (choice) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    displayTransactions(filterByAmount(true));
                    break;
                case "P":
                    displayTransactions(filterByAmount(false));
                    break;
                case "R":
                    boolean goHome = runReports();
                    if (goHome) running = false;
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public void displayTransactions(List<Transaction> list) {
        if (list.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        List<Transaction> displayList = new ArrayList<>(list);
        Collections.reverse(displayList);
        for (Transaction t : displayList) {
            System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
        }
    }

    private List<Transaction> filterByAmount(boolean positive) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (positive && t.getAmount() > 0) {
                filtered.add(t);
            } else if (!positive && t.getAmount() < 0) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    private boolean runReports() {
        System.out.println("-------Reports-------");
        System.out.println("1. Month To Date");
        System.out.println("2. Previous Month");
        System.out.println("3. Year To Date");
        System.out.println("4. Previous Year");
        System.out.println("5. Search By Vendor");
        System.out.println("6. Custom Search");
        System.out.println("0. Back to Ledger Page");
        System.out.println("H. Home");
        System.out.print("Select an option: ");

        String reportChoice = scanner.nextLine().toUpperCase().trim();
        LocalDate now = LocalDate.now();
        List<Transaction> result = new ArrayList<>();

        switch (reportChoice) {
            case "1":
                for (Transaction t : transactions) {
                    if (t.getDate().getMonth() == now.getMonth() && t.getDate().getYear() == now.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
                break;
            case "2":
                LocalDate prevMonth = now.minusMonths(1);
                for (Transaction t : transactions) {
                    if (t.getDate().getMonth() == prevMonth.getMonth() && t.getDate().getYear() == prevMonth.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
                break;
            case "3":
                for (Transaction t : transactions) {
                    if (t.getDate().getYear() == now.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
                break;
            case "4":
                int prevYear = now.minusYears(1).getYear();
                for (Transaction t : transactions) {
                    if (t.getDate().getYear() == prevYear) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
                break;
            case "5":
                System.out.print("Enter Vendor Name: ");
                String vendorName = scanner.nextLine();
                for (Transaction t : transactions) {
                    if (t.getVendor().equalsIgnoreCase(vendorName)) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
                break;
            case "6":
                customSearch();
                break;
            case "0":
                return false;
            case "H":
                return true;
            default:
                System.out.println("Invalid option.");
        }
        return false;
    }

    private void customSearch() {
        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine().trim();
        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();
        System.out.print("Amount: ");
        String amountInput = scanner.nextLine().trim();

        double amount = amountInput.isEmpty() ? 0 : Double.parseDouble(amountInput);
        LocalDate start = startDate.isEmpty() ? null : LocalDate.parse(startDate);
        LocalDate end = endDate.isEmpty() ? null : LocalDate.parse(endDate);

        System.out.println("\n--- Custom Search Results ---");
        for (Transaction transaction : transactions) {
            boolean matches = true;
            if (start != null && transaction.getDate().isBefore(start)) matches = false;
            if (end != null && transaction.getDate().isAfter(end)) matches = false;
            if (matches && !description.isEmpty() && !transaction.getDescription().toLowerCase().contains(description.toLowerCase())) matches = false;
            if (matches && !vendor.isEmpty() && !transaction.getVendor().toLowerCase().contains(vendor.toLowerCase())) matches = false;
            if (matches && !amountInput.isEmpty() && transaction.getAmount() != amount) matches = false;

            if (matches) System.out.println(transaction);
        }
    }
}