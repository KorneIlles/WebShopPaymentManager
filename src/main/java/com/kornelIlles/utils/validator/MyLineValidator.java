package com.kornelIlles.utils.validator;

import com.kornelIlles.utils.logger.FileLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MyLineValidator implements LineValidator {
    @Override
    public boolean isValid(String[] line) {
        return isValidPaymentData(line) && isValidDate(line);
    }

    private boolean isValidPaymentData(String[] line) {
        String paymentType = line[2];
        String bankAccount = line[4];
        String cardNumber = line[5];
        if (paymentType.equalsIgnoreCase("card")) {
            if (bankAccount.isEmpty() && cardNumber.length() == 16) {
                return true;
            }
            FileLogger.logWrongLine(line, "incorrect card data");
        } else if (paymentType.equalsIgnoreCase("transfer")) {
            if (cardNumber.isEmpty() && bankAccount.length() == 16) {
                return true;
            }
            FileLogger.logWrongLine(line, "incorrect account number");
        }
        return false;
    }

    private boolean isValidDate(String[] line) {
        String dateString = line[6];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            FileLogger.logWrongLine(line, "incorrect date");
            return false;
        }
    }
}
