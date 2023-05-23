package com.kornelIlles.model.payment;

import java.math.BigDecimal;
import java.util.Date;

public abstract class Payment {

    private final String webShopId;

    private final String clientId;

    private final BigDecimal amount;

    private final Date paymentDate;

    public Payment(String webShopId, String clientId, BigDecimal amount, Date paymentDate) {
        this.webShopId = webShopId;
        this.clientId = clientId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public String getWebShopId() {
        return webShopId;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }
}