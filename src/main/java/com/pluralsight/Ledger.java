package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    Scanner scanner = new Scanner(System.in);
    List<Transaction> transactions = new ArrayList<>();
    public void displayMenu() {
        boolean running = true;
        while (running) {
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

    public void displayTransactions(){
        try (BufferedReader bufReader = new BufferedReader(new FileReader("transaction.csv"))) {
            String line;
            while ((line = bufReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void displayTransactions(List<Transaction> transactions){
        for (Transaction t : transactions) {
            System.out.println(t);
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
}