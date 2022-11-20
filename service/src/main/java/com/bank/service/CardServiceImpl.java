package com.bank.service;

import com.bank.dao.CardRepository;
import com.bank.entity.Card;
import com.bank.entity.Status;
import com.bank.io.Parser;
import com.bank.io.Reader;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.bank.util.UtilityInform.MAX_SUM;

@Slf4j
public class CardServiceImpl implements CardService {

    private static CardService instance;
    private final CardRepository cardRepository;
    private final Parser parser;
    private final Reader reader;

    private CardServiceImpl() {
        this.cardRepository = CardRepository.getInstance();
        this.parser = Parser.getInstance();
        this.reader = Reader.getInstance();
    }

    public static CardService getInstance() {
        if (instance == null) {
            instance = new CardServiceImpl();
        }
        return instance;
    }

    @Override
    public void create(Card card) {
        cardRepository.create(card);
    }

    @Override
    public void add() {
        List<Card> cards = parser.parseCard(reader.readAll("service/data/card.txt"));
        for (Card card : cards) {
            if (cardRepository.findById(card.getId()).isEmpty()) {
                cardRepository.create(card);
            } else {
                log.warn("Card with such id exists");
            }
        }
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public void updateStatus(Long id, Status status) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) {
            card.get().setStatus(status);
        }
    }

    @Override
    public BigDecimal checkSum(Long id) {
        Optional<Card> card = cardRepository.findById(id);
        return card.get().getBalance();
    }

    @Override
    public void withdrawFunds(Long id, BigDecimal sum) {
        Optional<Card> maybeCard = cardRepository.findById(id);
        if (sum.compareTo(maybeCard.get().getBalance()) < 0) {
            BigDecimal balance = maybeCard.get().getBalance();
            maybeCard.get().setBalance(balance.subtract(sum));
        } else {
            log.error("Insufficient funds on the balance");
        }
    }

    @Override
    public Optional<Card> findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public void topUpCard(Long id, BigDecimal sum) {
        Optional<Card> maybeCard = cardRepository.findById(id);
        if (sum.compareTo(MAX_SUM) < 0) {
            BigDecimal balanceOld = maybeCard.get().getBalance();
            maybeCard.get().setBalance(balanceOld.add(sum));
        } else {
            log.error("Sum can be less than 1000000");
        }
    }
}
