package com.bank.service;

public interface AtmService {

    void downloadAllCard();

    boolean authenticationCard(String number, Integer pin);

    void writeToFile();

    boolean blockedCard(Long id);

    void unblockedCard();

}
