package com.bank.ui;

import com.bank.exception.CustomException;

import java.util.List;
import java.util.Scanner;

public class Navigator {
    private Menu currentMenu;

    public Navigator() {
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

    public void printMenu() {
        System.out.println(currentMenu.getName());
        int index=0;
        while (index< currentMenu.getMenuItems().size()){
            System.out.println(currentMenu.getMenuItems().get(index).getTitle());
            index++;
        }
    }

    public void navigate(Integer index) {
        int size = currentMenu.getMenuItems().size();
        if (index <= size && index > 0) {
            MenuItem menuItem = currentMenu.getMenuItems().get(index - 1);
            if (menuItem.getAction() == null) {
                currentMenu = menuItem.getNextMenu();
            } else {
                menuItem.doAction();
            }
        } else{
            throw new CustomException("The menuItem doesn't exit");
        }
    }
}
