package com.teachmeskills.lesson_33.validation;

import com.teachmeskills.lesson_33.exception.InputValidationException;

import java.util.Scanner;

public class InputValidator {

    private final Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getAccountNumber() throws InputValidationException {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        if (accountNumber.isEmpty()) {
            throw new InputValidationException("Account number cannot be empty.");
        }
        return accountNumber;
    }

    public double getInitialBalance() throws InputValidationException {
        System.out.print("Enter initial balance: ");
        double initialBalance;
        try {
            initialBalance = Double.parseDouble(scanner.nextLine());
            if (initialBalance < 0) {
                throw new InputValidationException("Initial balance cannot be negative.");
            }
        } catch (NumberFormatException e) {
            throw new InputValidationException("Invalid input for initial balance.");
        }
        return initialBalance;
    }

    public String getUsername() throws InputValidationException {
        System.out.print("Enter owner's username: ");
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            throw new InputValidationException("Username cannot be empty.");
        }
        return username;
    }


    public double getTransferAmount() throws InputValidationException {
        System.out.print("Enter transfer amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                throw new InputValidationException("Transfer amount must be positive.");
            }
        } catch (NumberFormatException e) {
            throw new InputValidationException("Invalid input for transfer amount.");
        }
        return amount;
    }
}
