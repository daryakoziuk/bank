package com.bank.dao;

import com.bank.entity.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class CardRepository implements BaseRepository<Card, Long> {

    private static CardRepository instance;
    private final List<Card> cards = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    private CardRepository() {
    }

    public static CardRepository getInstance() {
        if (instance == null) {
            instance = new CardRepository();
        }
        return instance;
    }

    @Override
    public void add(Card card) {
        card.setId(counter.getAndIncrement());
    }

    @Override
    public void create(Card card) {
        cards.add(card);
    }

    @Override
    public Optional<Card> findByNumber(String number) {
        return cards.stream().filter(card -> card.getNumber().equals(number))
                .findFirst();
    }

    @Override
    public List<Card> findAll() {
        return cards;
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cards.stream().filter(card -> card.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(Card card) {
        cards.remove(card);
    }

}
