package com.kornelIlles.model.payment;

import java.math.BigDecimal;
import java.util.Date;

public class CardPayment extends Payment {

    private final String paymentType;

    private final String cardNumber;

    public CardPayment(String webShopId, String clientId, BigDecimal amount, Date paymentDate, String paymentType, String cardNumber) {
        super(webShopId, clientId, amount, paymentDate);
        this.paymentType = paymentType;
        this.cardNumber = cardNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "CardPayment{" +
                "paymentType='" + paymentType + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
