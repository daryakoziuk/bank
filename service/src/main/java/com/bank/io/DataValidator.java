package com.bank.io;

import java.util.regex.Pattern;

public class DataValidator {

    private static final String REGEX_NUMBER = "\\d{4}\\-\\d{4}\\-\\d{4}\\-\\d{4}";
    private static final String REGEX_PIN = "\\d{4}";
    private static final String REGEX_ID = "\\d{1,}";
    private static final String REGEX_BALANCE = "\\d{1,}";
    private static final String REGEX_STATUS = "(ACTIVE|BLOCKED)";
    private static final String REGEX_DATE_TIME = "\\d{4}\\-\\d{2}\\-\\d{2}\\d{2}\\:\\d{2}";

    public static boolean checkDate(String date) {
        return Pattern.matches(REGEX_DATE_TIME, date);
    }

    public static boolean checkStatus(String status) {
        return Pattern.matches(REGEX_STATUS, status);
    }

    public static boolean checkNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }

    public static boolean checkPin(String pin) {
        return Pattern.matches(REGEX_PIN, pin);
    }

    public static boolean checkId(String id) {
        return Pattern.matches(REGEX_ID, id);
    }

    public static boolean checkBalance(String balance) {
        return Pattern.matches(REGEX_BALANCE, balance);
    }
}
