package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    private Scanner scanner = new Scanner(System.in);
    private List<Transaction> transactions = new ArrayList<>();

    public Ledger() {
        loadTransactions();
    }

    private void loadTransactions() {
        transactions.clear();
        try (BufferedReader bufReader = new BufferedReader(new FileReader("transaction.csv"))) {
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
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A" -> displayTransactions();
                case "D" -> displayTransactions(filterByAmount(true));
                case "P" -> displayTransactions(filterByAmount(false));
                case "R" -> runReports();
                case "H" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void displayTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t.getDate() + "|" + t.getTime() + "|" + t.getDescription() + "|" + t.getVendor() + "|" + t.getAmount());
        }
    }

    public void displayTransactions(List<Transaction> transactions) {
        for (Transaction t : transactions) {
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

    private void runReports() {
        System.out.println("-------Reports-------");
        System.out.println("1. Month To Date");
        System.out.println("2. Previous Month");
        System.out.println("3. Year To Date");
        System.out.println("4. Previous Year");
        System.out.println("5. Search By Vendor");
        System.out.println("0. Back to Ledger Page");
        System.out.println("H. Home");
        String reportChoice = scanner.nextLine();

        LocalDate now = LocalDate.now();
        List<Transaction> result = new ArrayList<>();

        switch (reportChoice) {
            case "1" -> {
                for (Transaction t : transactions) {
                    if (t.getDate().getMonthValue() == now.getMonthValue() && t.getDate().getYear() == now.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
            }
            case "2" -> {
                LocalDate previousMonth = now.minusMonths(1);
                for (Transaction t : transactions) {
                    if (t.getDate().getMonthValue() == previousMonth.getMonthValue() && t.getDate().getYear() == previousMonth.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
            }
            case "3" -> {
                for (Transaction t : transactions) {
                    if (t.getDate().getYear() == now.getYear()) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
            }
            case "4" -> {
                for (Transaction t : transactions) {
                    if (t.getDate().getYear() == now.getYear() - 1) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
            }
            case "5" -> {
                System.out.println("Search by Vendor Name:");
                String vendorName = scanner.nextLine();
                for (Transaction t : transactions) {
                    if (t.getVendor().equalsIgnoreCase(vendorName)) {
                        result.add(t);
                    }
                }
                displayTransactions(result);
            }
            case "0" -> { }
            case "H" -> displayMenu();
            default -> System.out.println("Invalid option.");
        }
    }
}