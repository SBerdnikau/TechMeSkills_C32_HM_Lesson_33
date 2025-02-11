package com.teachmeskills.lesson_33.manager.impl;


import com.teachmeskills.lesson_33.manager.ILogManager;
import com.teachmeskills.lesson_33.model.LogTransaction;
import com.teachmeskills.lesson_33.utils.DatabaseConnect;
import com.teachmeskills.lesson_33.utils.SQLQuery;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager implements ILogManager {
    private static final Logger logger = Logger.getLogger(LogManager.class.getName());

    @Override
    public void logTransactionManager(LogTransaction logTransaction) {
        try (Connection conn = DriverManager.getConnection(DatabaseConnect.getInstance().getUrl(),
                DatabaseConnect.getInstance().getUser (),
                DatabaseConnect.getInstance().getPassword());
             PreparedStatement preparedStatement = conn.prepareStatement(SQLQuery.TRANSACTION_LOG)) {
            preparedStatement.setInt(1, logTransaction.getTransactionId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(logTransaction.getLogTime()));
            preparedStatement.setString(3, logTransaction.getLogMessage());
            preparedStatement.executeUpdate();

            logger.info("Transaction log entry added successfully -> " + logTransaction.getLogMessage());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error when writing to the transaction log -> " + e.getMessage(), e);
        }
    }

}