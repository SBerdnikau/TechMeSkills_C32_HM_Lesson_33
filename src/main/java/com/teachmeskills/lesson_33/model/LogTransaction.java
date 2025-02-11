package com.teachmeskills.lesson_33.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class LogTransaction {
    private int transactionId;
    private int logId;
    private LocalDateTime logTime;
    private String logMessage;

    public LogTransaction(int transactionId, int logId, LocalDateTime logTime, String logMessage) {
        this.transactionId = transactionId;
        this.logId = logId ;
        this.logTime = logTime;
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public long getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LogTransaction that = (LogTransaction) o;
        return transactionId == that.transactionId && logId == that.logId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, logId);
    }
}
