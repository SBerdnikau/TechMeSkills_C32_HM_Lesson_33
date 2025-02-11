package com.teachmeskills.lesson_33.service;

import com.teachmeskills.lesson_33.exception.TransactionException;
import com.teachmeskills.lesson_33.model.Transaction;
import com.teachmeskills.lesson_33.utils.DatabaseConnect;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.teachmeskills.lesson_33.utils.SQLQuery.TRANSACTIONS;

public class TransactionService {
    private static final Logger logger = Logger.getLogger(TransactionService.class.getName());

    public void recordTransaction(Transaction transaction) throws TransactionException {
        try (Connection conn = DriverManager.getConnection(DatabaseConnect.getInstance().getUrl(),
                DatabaseConnect.getInstance().getUser (),
                DatabaseConnect.getInstance().getPassword());
             PreparedStatement preparedStatement = conn.prepareStatement(TRANSACTIONS, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, transaction.getAccountNumber());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionTime()));
            preparedStatement.setString(3, transaction.getTransactionType());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getInt(1));
            }

            logger.info("The transaction was recorded successfully -> " + transaction.getId());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error when recording a transaction -> " + e.getMessage(), e);
            throw new TransactionException("Failed to record transaction");
        }
    }
}
