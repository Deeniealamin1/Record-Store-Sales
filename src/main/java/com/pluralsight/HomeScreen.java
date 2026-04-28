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
    }
}