package com.bank.io;

import com.bank.entity.Card;
import com.bank.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parser {

    private static Parser instance;

    private Parser() {
    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public List<String> outputCard(List<Card> cards) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> dataCard = new ArrayList<>();
        for (Card card : cards) {
            dataCard.add(String.valueOf(stringBuilder.append(card.getId()).append(" ")
                    .append(card.getNumber()).append(" ")
                    .append(card.getPin()).append(" ")
                    .append(card.getBalance()).append(" ")
                    .append(card.getStatus()).append(" ")
                    .append(card.getDateUnblocked().format(DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm"))).append(" ")));
            stringBuilder.setLength(0);
        }
        return dataCard;
    }

    public List<Card> parseCard(List<String> parameters) {
        List<Card> cards = new ArrayList<>();
        for (String parameter : parameters) {
            Card card = parseCardParameter(parameter.split(" "));
            cards.add(card);
        }
        return cards.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Card parseCardParameter(String[] cardParameters) {
        if (cardParameters.length != 6
                || !DataValidator.checkNumber(cardParameters[1])
                || !DataValidator.checkBalance(cardParameters[3])
                || !DataValidator.checkId(cardParameters[0])
                || !DataValidator.checkPin(cardParameters[2])
                || !DataValidator.checkStatus(cardParameters[4])
                || !DataValidator.checkDate(cardParameters[5])) {
            return null;
        } else {
            Card card = new Card(Integer.valueOf(cardParameters[2]), (cardParameters[1]),
                    new BigDecimal(cardParameters[3]), Status.valueOf(cardParameters[4]),
                    LocalDateTime.parse((cardParameters[5]), DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm")));
            card.setId(Long.parseLong(cardParameters[0]));
            return card;
        }
    }
}
