package com.teachmeskills.lesson_33.validation;

import com.teachmeskills.lesson_33.exception.AccountValidationException;
import com.teachmeskills.lesson_33.service.AccountService;

public class AccountValidator {

    private final AccountService accountService;

    public AccountValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    public void validateTransfer(String fromAccount, String toAccount, double amount) throws AccountValidationException {

        if (accountService.getBalance(fromAccount) < 0) {
            throw new AccountValidationException("Sender's account does not exist.");
        }

        if (accountService.getBalance(toAccount) < 0) {
            throw new AccountValidationException("Recipient's account does not exist.");
        }

        if (amount <= 0) {
            throw new AccountValidationException("Transfer amount must be positive.");
        }

        double senderBalance = accountService.getBalance(fromAccount);

        if (senderBalance <= 0) {
            throw new AccountValidationException("Sender's balance must be positive.");
        }

        if (senderBalance < amount) {
            throw new AccountValidationException("Insufficient funds for the transfer.");
        }

    }


}
