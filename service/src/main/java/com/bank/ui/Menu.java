package com.bank.ui;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"menuItems"})
@NoArgsConstructor
public class Menu {

    private String name;
    private List<MenuItem>menuItems=new ArrayList<>();
}
