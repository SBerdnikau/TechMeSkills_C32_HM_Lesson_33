package com.teachmeskills.lesson_33.app;

import com.teachmeskills.lesson_33.controller.impl.AccountController;

public class Runner {
    public static void main(String[] args) {
        AccountController account = new AccountController();
        account.startApplication();
    }
}
