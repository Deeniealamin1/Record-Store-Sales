package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class HomeScreen {
    private Scanner scanner = new Scanner(System.in);
    private Ledger ledger = new Ledger();
    public void display() throws IOException {
        boolean running = true;

        while (running) {
            System.out.println("-----Home Screen------");
            System.out.println("D. Add Deposit");
            System.out.println("P. Make a payment(Debit)");
            System.out.println("L. Ledger");
            System.out.println("X. Exit");

            String userChoice = scanner.nextLine().toUpperCase();

            switch (userChoice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    ledger.displayMenu();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }private void addDeposit() {
        System.out.println("Enter Deposit Information");
        System.out.println("Enter Description");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor Name");
        String vendorName = scanner.nextLine();
        System.out.println("Enter Amount");
        double amountInfo = scanner.nextDouble();
        scanner.nextLine();

        String transactionInfo = LocalDate.now() + "|" + LocalTime.now() + "|" + description + "|" + vendorName + "|" + amountInfo;
        saveTransaction(transactionInfo);
    }

    private void makePayment() {
        System.out.println("Enter Payment Information");
        System.out.println("Enter Description");
        String description = scanner.nextLine();
        System.out.println("Enter Vendor Name");
        String vendorName = scanner.nextLine();
        System.out.println("Enter Amount");
        double amountInfo = scanner.nextDouble();
        scanner.nextLine();

        double paymentAmount = amountInfo * -1;

        String transactionInfo = LocalDate.now() + "|" + LocalTime.now() + "|" + description + "|" + vendorName + "|" + paymentAmount;
        saveTransaction(transactionInfo);
    }

}