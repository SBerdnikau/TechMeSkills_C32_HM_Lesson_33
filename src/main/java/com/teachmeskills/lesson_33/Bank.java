package com.teachmeskills.lesson_33;


import com.teachmeskills.lesson_33.Service.BankService;

import java.sql.SQLException;

public class Bank {

    public static void main(String[] args) {
        try {
            BankService.createAccount("1234567890", 1000.00);
            System.out.println("Initial Balance: " + BankService.getBalance("1234567890"));
            BankService.createAccount("0987654321", 500.00);
            BankService.transferFunds("1234567890", "0987654321", 200.00);
            System.out.println("Balance after transfer: ");
            System.out.println("Account 1: " + BankService.getBalance("1234567890"));
            System.out.println("Account 2: " + BankService.getBalance("0987654321"));
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

}
