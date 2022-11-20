package com.bank.service;

import com.bank.entity.Card;
import com.bank.entity.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CardService {

    void create(Card card);

    void add();

    Optional<Card> findById(Long id);

    List<Card> findAll();

    void updateStatus(Long id, Status status);

    BigDecimal checkSum(Long id);

    void withdrawFunds(Long id, BigDecimal sum);

    Optional<Card> findByNumber(String number);

    void topUpCard(Long id, BigDecimal sum);

}
