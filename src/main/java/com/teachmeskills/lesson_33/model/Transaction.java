package com.teachmeskills.lesson_33.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private int id;
    private String accountNumber;
    private LocalDateTime transactionTime;
    private String transactionType;
    private double amount;

    public Transaction(int id, String accountNumber, LocalDateTime transactionTime, String transactionType, double amount) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.transactionTime = transactionTime;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber);
    }
}
