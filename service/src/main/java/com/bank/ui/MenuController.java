package com.bank.ui;

import java.util.Scanner;

public class MenuController {

    private static MenuController instance;
    private Builder builder;
    private Navigator navigator;

    Scanner scanner = new Scanner(System.in);

    private MenuController() {
        this.builder = Builder.getInstance();
        this.navigator = new Navigator();
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    public void run() {
        builder.buildMenu();
        navigator.setCurrentMenu(builder.getRootMenu());
        while (navigator.getCurrentMenu() != null) {
            navigator.printMenu();
            System.out.println("Please, make a choice:\n");
            Integer choice = scanner.nextInt();
            navigator.navigate(choice);
        }
    }
}
