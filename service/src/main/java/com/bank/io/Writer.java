package com.bank.io;

import com.bank.exception.CustomException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    private static Writer instance;

    private Writer() {
    }

    public static Writer getInstance() {
        if (instance == null) {
            instance = new Writer();
        }
        return instance;
    }

    public void writeToFile(List<String> dataToWrite, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String s : dataToWrite) {
                bw.write(s + "\n");
            }
            bw.flush();
        } catch (IOException e) {
            throw new CustomException("Exception while writing data in file", e);
        }
    }
}
