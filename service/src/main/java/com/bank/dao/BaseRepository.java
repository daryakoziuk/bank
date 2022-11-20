package com.bank.dao;

import com.bank.entity.Card;
import com.bank.entity.Status;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, K> {

    void add(Card card);

    void create(T t);

    Optional<T> findByNumber(String number);

    List<T> findAll();

    Optional<T> findById(K id);

    void delete(T t);

}
