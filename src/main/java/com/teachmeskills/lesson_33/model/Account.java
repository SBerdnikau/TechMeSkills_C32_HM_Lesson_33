package com.teachmeskills.lesson_33.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Account {
    private int id;
    private String accountNumber;
    private double balance;
    private String username;
    private LocalDateTime created;

    public Account(int id, String accountNumber, double balance, String username, LocalDateTime created) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.username = username;
        this.created = created;
    }

    public long getId() {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, username);
    }
}
