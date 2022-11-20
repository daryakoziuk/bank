package com.bank.service;

import com.bank.entity.Card;
import com.bank.entity.Status;
import com.bank.io.Parser;
import com.bank.io.Reader;
import com.bank.io.Writer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
public class AtmServiceImpl implements AtmService {

    private static AtmService instance;
    private final Reader reader;
    private final Parser parser;
    private final CardService cardService;
    private final Writer writer;

    private AtmServiceImpl() {
        this.reader = Reader.getInstance();
        this.parser = Parser.getInstance();
        this.cardService = CardServiceImpl.getInstance();
        this.writer = Writer.getInstance();
    }

    public static AtmService getInstance() {
        if (instance == null) {
            instance = new AtmServiceImpl();
        }
        return instance;
    }

    @Override
    public void downloadAllCard() {
        List<Card> cards = parser.parseCard(reader.readAll("service/data/card.txt"));
        for (Card card : cards) {
            if (cardService.findById(card.getId()).isEmpty()) {
                cardService.create(card);
            }
        }
    }

    @Override
    public boolean authenticationCard(String number, Integer pin) {
        Optional<Card> optionalCard = cardService.findByNumber(number);
        if (optionalCard.isPresent()) {
            if (!optionalCard.get().getPin().equals(pin)) {
                log.info("Wrong pin");
                return false;
            }
        }
        return true;
    }

    @Override
    public void writeToFile() {
        writer.writeToFile(parser.outputCard(cardService.findAll()), "service/data/card.txt");
    }

    @Override
    public boolean blockedCard(Long id) {
        Optional<Card> mayBeCard = cardService.findById(id);
        if (mayBeCard.isPresent()) {
            cardService.updateStatus(id, Status.BLOCKED);
            mayBeCard.get().setDateUnblocked(LocalDateTime.now().plusDays(1));
        }
        return mayBeCard.isPresent();
    }

    @Override
    public void unblockedCard() {
        List<Card> cards = cardService.findAll();
        for (Card card : cards) {
            if (card.getDateUnblocked().isBefore(LocalDateTime.now())) {
                cardService.updateStatus(card.getId(), Status.ACTIVE);
            }
        }
    }
}
