package com.bank.ui;

import com.bank.entity.Card;
import com.bank.entity.Status;
import lombok.Data;
import com.bank.service.AtmService;
import com.bank.service.AtmServiceImpl;
import com.bank.service.CardService;
import com.bank.service.CardServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@Data
public class Builder {

    private static Builder instance;
    private Menu rootMenu;
    private final CardService cardService;
    private final AtmService atmService;
    private String number;
    private Integer pin;
    private boolean result;
    Scanner scanner = new Scanner(System.in);

    private Builder() {
        this.cardService = CardServiceImpl.getInstance();
        this.atmService = AtmServiceImpl.getInstance();
    }

    public static Builder getInstance() {
        if (instance == null) {
            instance = new Builder();
        }
        return instance;
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public void buildMenu() {
        rootMenu = createMenu(addMainMenu(), "Main menu");
        Menu authMenu = createMenu(addAuthorisationMenu(), "Authorisation:");
        authorisationMenuItems(authMenu, rootMenu);
        Menu cardMenu = createMenu(addCardMenu(), "Card menu:");
        cardMenuItems(cardMenu, rootMenu);
        rootMenu.getMenuItems().get(0).setNextMenu(authMenu);
        rootMenu.getMenuItems().get(1);
        authMenu.getMenuItems().get(1).setNextMenu(cardMenu);
        cardMenu.getMenuItems().get(3).setNextMenu(authMenu);
    }

    private Menu createMenu(List<String> titles, String nameMenu) {
        Menu menu = new Menu();
        List<MenuItem> menuItems = menu.getMenuItems();
        menu.setName(nameMenu);
        int index = 0;
        while (index < titles.size()) {
            MenuItem menuItem = new MenuItem(titles.get(index));
            menuItems.add(menuItem);
            index++;
        }
        menu.setMenuItems(menuItems);
        return menu;
    }

    private List<String> addMainMenu() {
        return List.of("1. Authorisation", "2. Exist");
    }

    private List<String> addAuthorisationMenu() {
        return List.of("1. Enter data", "2. Card menu", "3. Exit");
    }

    private List<String> addCardMenu() {
        return List.of("1. Check the balance", "2. Top up balance ",
                "3. Withdraw funds", "4. Authorisation ", "5. Exit");
    }

    private void authorisationMenuItems(Menu authMenu, Menu cardMenu) {
        author(authMenu.getMenuItems().get(0));
        authMenu.getMenuItems().get(1).setNextMenu(cardMenu);
        authMenu.getMenuItems().get(2);
    }

    private void cardMenuItems(Menu cardMenu, Menu authMenu) {
        checkSum(cardMenu.getMenuItems().get(0));
        topUpCard(cardMenu.getMenuItems().get(1));
        withdrawFunds(cardMenu.getMenuItems().get(2));
        cardMenu.getMenuItems().get(3).setNextMenu(authMenu);
        cardMenu.getMenuItems().get(4);
    }

    private void author(MenuItem menuItem) {
        menuItem.setAction(() -> {
            atmService.downloadAllCard();
            atmService.unblockedCard();
            System.out.println("Please enter card's number format XXXX-XXXX-XXXX-XXXX:");
            number = scanner.next();
            Optional<Card> card = cardService.findByNumber(number);
            if (card.isPresent()) {
                int i = 0;
                result = false;
                while (!result && i < 3) {
                    System.out.println("Please enter card's pin:");
                    pin = scanner.nextInt();
                    result = atmService.authenticationCard(number, pin);
                    i++;
                    if (i == 3) {
                        atmService.blockedCard(card.get().getId());
                        atmService.writeToFile();
                        log.info("Card has been blocked");
                    }
                }

            } else {
                log.info("Card with such number doesn't exist");
            }
        });
    }

    private void checkSum(MenuItem menuItem) {
        menuItem.setAction(() -> {
            if (result) {
                Optional<Card> card = cardService.findByNumber(number);
                if (card.isPresent() && card.get().getStatus().equals(Status.ACTIVE)) {
                    System.out.println(cardService.checkSum(card.get().getId()));
                    atmService.writeToFile();
                } else {
                    log.info("Your card is blocked");
                }
            } else {
                System.out.println("Please back to authorisation and enter data");
            }
        });
    }

    private void withdrawFunds(MenuItem menuItem) {
        menuItem.setAction(() -> {
            if (result) {
                Optional<Card> card = cardService.findByNumber(number);
                if (card.isPresent() && card.get().getStatus().equals(Status.ACTIVE)) {
                    System.out.println("Please enter sum that need to withdraw");
                    BigDecimal sum = scanner.nextBigDecimal();
                    cardService.withdrawFunds(card.get().getId(), sum);
                    atmService.writeToFile();
                } else {
                    log.info("Your card is blocked");
                }
            } else {
                System.out.println("Please back to authorisation and enter data");
            }
        });
    }

    private void topUpCard(MenuItem menuItem) {
        menuItem.setAction(() -> {
            if (result) {
                Optional<Card> card = cardService.findByNumber(number);
                if (card.isPresent() && card.get().getStatus().equals(Status.ACTIVE)) {
                    System.out.println("Please enter sum for top up card");
                    BigDecimal sum = scanner.nextBigDecimal();
                    cardService.topUpCard(card.get().getId(), sum);
                    atmService.writeToFile();
                } else {
                    log.info("Your card is blocked");
                }
            } else {
                System.out.println("Please back to authorisation and enter data");
            }
        });
    }
}
