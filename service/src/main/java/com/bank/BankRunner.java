package com.bank;

import com.bank.ui.MenuController;

public class BankRunner {

    public static void main(String[] args) {
        MenuController menuController = MenuController.getInstance();
        menuController.run();
    }
}
