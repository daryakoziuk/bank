package com.bank.ui;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItem {

    private String title;
    private Action action;
    private Menu nextMenu;

    public MenuItem(String title){
        this.title=title;
    }

    public void doAction() {
        action.execute();
    }
}
