package com.teachmeskills.lesson_33.utils;

public interface SQLQuery {
    String ACCOUNT = "INSERT INTO accounts (account_number, balance, username, created) VALUES (?, ?, ?, ?)";
    String BALANCE = "SELECT balance FROM customer WHERE account_number = ?";
    String WITHDRAWAL = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
    String DEPOSIT = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
    String TRANSACTIONS = "INSERT INTO transactions (account_number, transaction_date, transaction_type, amount) VALUES (?, ?, ?, ?)";
    String TRANSACTION_LOG = "INSERT INTO transaction_log (transaction_id, log_date, log_message) VALUES (?, ?, ?)";
}
