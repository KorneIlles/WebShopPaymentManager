package com.kornelIlles.model.payment;

import java.math.BigDecimal;
import java.util.Date;

public class TransferPayment extends Payment {

    private final String paymentType;

    private final String accountNumber;

    public TransferPayment(String webShopId, String clientId, BigDecimal amount, Date paymentDate, String paymentType, String accountNumber) {
        super(webShopId, clientId, amount, paymentDate);
        this.paymentType = paymentType;
        this.accountNumber = accountNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


    @Override
    public String toString() {
        return "TransferPayment{" +
                "paymentType='" + paymentType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
