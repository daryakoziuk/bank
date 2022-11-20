package com.bank.io;

import com.bank.exception.CustomException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Reader {

    private static Reader instance;

    private Reader() {
    }

    public static Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }
        return instance;
    }

    public List<String> readAll(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new CustomException("Exception while reading data from file", e);
        }
    }
}
