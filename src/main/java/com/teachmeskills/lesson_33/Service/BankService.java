package com.teachmeskills.lesson_33.Service;

import com.teachmeskills.lesson_33.SQLQuery;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankService {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tmc_c32_hm_lesson_33";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static final Logger LOGGER = Logger.getLogger(BankService.class.getName());

    public static void createAccount(String accountNumber, double initialBalance) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQLQuery.CREATE_ACCOUNT)) {
            pstmt.setString(1, accountNumber);
            pstmt.setDouble(2, initialBalance);
            pstmt.executeUpdate();
        }
    }

    public static double getBalance(String accountNumber) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SQLQuery.GET_BALANCE)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        }
        throw new SQLException("Account not found");
    }


    public static void transferFunds(String fromAccount, String toAccount, double amount) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        Savepoint savepoint = null;
        try {
            conn.setAutoCommit(false);
            savepoint = conn.setSavepoint("BeforeTransfer");

            double fromBalance = getBalance(fromAccount);
            if (fromBalance < amount) {
                throw new SQLException("Insufficient funds for transfer");
            }

            // Вычитаем средства
            updateBalance(conn, fromAccount, fromBalance - amount);

            // Добавляем средства
            double toBalance = getBalance(toAccount);
            updateBalance(conn, toAccount, toBalance + amount);

            // Записываем в транзакции
            logTransaction(fromAccount, toAccount, amount, conn);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(savepoint);
                LOGGER.log(Level.SEVERE, "Transaction failed and rolled back", e);
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private static void updateBalance(Connection conn, String accountNumber, double newBalance) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQLQuery.UPDATE_BALANCE)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
        }
    }

    private static void logTransaction(String fromAccount, String toAccount, double amount, Connection conn) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(SQLQuery.TRANSACTION)) {
            pstmt.setString(1, fromAccount);
            pstmt.setString(2, toAccount);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
        }

    }

}