package com.teachmeskills.lesson_33;

public interface SQLQuery {
    String CREATE_ACCOUNT = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";
    String GET_BALANCE = "SELECT balance FROM accounts WHERE account_number = ?";
    String UPDATE_BALANCE = "UPDATE accounts SET balance = ? WHERE account_number = ?";
    String TRANSACTION = "INSERT INTO transactions (from_account, to_account, amount) VALUES (?, ?, ?)";
}
