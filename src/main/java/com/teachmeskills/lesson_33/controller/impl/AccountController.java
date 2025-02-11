package com.teachmeskills.lesson_33.controller.impl;

import com.teachmeskills.lesson_33.controller.IAccountController;
import com.teachmeskills.lesson_33.exception.InputValidationException;
import com.teachmeskills.lesson_33.model.Account;
import com.teachmeskills.lesson_33.service.AccountService;
import com.teachmeskills.lesson_33.validation.InputValidator;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountController implements IAccountController {

    private static final Logger logger = Logger.getLogger(AccountController.class.getName());
    private final AccountService accountService = new AccountService();
    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator inputValidator = new InputValidator(scanner);

    @Override
    public void viewBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        double balance = accountService.getBalance(accountNumber);
        logger.info("Balance for account " + accountNumber + " -> " + balance);
        System.out.println("Balance for account " + accountNumber + ": " + balance);
    }

    @Override
    public void transferMoney() {
        try {
            String fromAccount = inputValidator.getAccountNumber();
            String toAccount = inputValidator.getAccountNumber();
            double amount = inputValidator.getTransferAmount();
            accountService.transferMoney(fromAccount, toAccount, amount);
            logger.info("Transfer completed -> " + amount + " from " + fromAccount + " to " + toAccount);
            System.out.println("Transfer completed successfully.");
        } catch (InputValidationException e) {
            logger.severe("Input validation error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Error during fund transfer -> " + e.getMessage());
            System.out.println("Error during fund transfer: " + e.getMessage());
        }
    }

    @Override
    public void createAccount() {
        try {
            String accountNumber = inputValidator.getAccountNumber ();
            double initialBalance = inputValidator.getInitialBalance();
            String username = inputValidator.getUsername();
            Account account = new Account(0, accountNumber, initialBalance, username, LocalDateTime.now());
            accountService.createAccount(account);
            logger.info("Account created -> " + accountNumber);
        } catch (InputValidationException e) {
            logger.severe("Input validation error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void startApplication() {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. View Balance");
            System.out.println("3. Transfer Funds");
            System.out.println("4. Exit");
            System.out.print("Select an action: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewBalance();
                case 3 -> transferMoney();
                case 4 -> {
                    logger.info("Exiting the program.");
                    return;
                }
                default -> logger.warning("Invalid choice. Please try again.");
            }
        }
    }
}
