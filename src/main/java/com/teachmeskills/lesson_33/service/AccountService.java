package com.teachmeskills.lesson_33.service;

import com.teachmeskills.lesson_33.exception.AccountValidationException;
import com.teachmeskills.lesson_33.exception.TransactionException;
import com.teachmeskills.lesson_33.manager.impl.LogManager;
import com.teachmeskills.lesson_33.model.Account;
import com.teachmeskills.lesson_33.model.LogTransaction;
import com.teachmeskills.lesson_33.model.Transaction;
import com.teachmeskills.lesson_33.utils.DatabaseConnect;
import com.teachmeskills.lesson_33.utils.SQLQuery;
import com.teachmeskills.lesson_33.validation.AccountValidator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AccountService {

    private static final Logger logger = Logger.getLogger(AccountService.class.getName());
    private final TransactionService transactionService = new TransactionService();
    private final LogManager logManager = new LogManager();
    private final AccountValidator accountValidator;
    private Connection conn;

    public AccountService() {
        this.accountValidator = new AccountValidator(this);
    }


    public void createAccount(Account account) {
        try (Connection conn = DriverManager.getConnection(DatabaseConnect.getInstance().getUrl(),
                DatabaseConnect.getInstance().getUser (),
                DatabaseConnect.getInstance().getPassword());
             PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.ACCOUNT)) {
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setString(3, account.getUsername());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(account.getCreated()));
            preparedStatement.executeUpdate();
            logger.info("Account created successfully: " + account.getAccountNumber());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while creating account: " + e.getMessage(), e);
        }
    }

    public double getBalance(String accountNumber) {
        try (Connection conn = DriverManager.getConnection(DatabaseConnect.getInstance().getUrl(),
                DatabaseConnect.getInstance().getUser (),
                DatabaseConnect.getInstance().getPassword());
             PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.BALANCE)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                logger.warning("Account not found: " + accountNumber);
                return 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving balance: " + e.getMessage(), e);
            return 0;
        }
    }

    public void transferMoney(String fromAccount, String toAccount, double amount) {
        try {
            accountValidator.validateTransfer(fromAccount, toAccount, amount);
        } catch (AccountValidationException e) {
            logger.warning("Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }

        Transaction transaction = new Transaction(0, fromAccount, LocalDateTime.now(), "Transfer", amount);
        LogTransaction logTransaction = new LogTransaction(0, 0, LocalDateTime.now(), "");

        try (Connection connection = DriverManager.getConnection(DatabaseConnect.getInstance().getUrl(),
                DatabaseConnect.getInstance().getUser (),
                DatabaseConnect.getInstance().getPassword())) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.WITHDRAWAL)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, fromAccount);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to withdraw funds from account: " + fromAccount);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DEPOSIT)) {
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, toAccount);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Failed to deposit funds into account: " + toAccount);
                }
            }

            transactionService.recordTransaction(transaction);

            logTransaction.setTransactionId(transaction.getId());
            logTransaction.setLogMessage("Transfer " + amount + " from " + fromAccount + " to " + toAccount);
            logManager.logTransactionManager(logTransaction);

            connection.commit();

            logger.info("Transfer completed successfully: " + amount + " from " + fromAccount + " to " + toAccount);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during fund transfer: " + e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                logger.log(Level.SEVERE, "Error during transaction rollback: " + rollbackEx.getMessage(), rollbackEx);
            }
        } catch (IllegalArgumentException e) {
            logger.warning("Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

}
