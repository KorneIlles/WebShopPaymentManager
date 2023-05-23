package com.kornelIlles.dto;

import java.math.BigDecimal;

public class CustomerPaymentReportDTO {

    private final String customerName;
    private final String customerAddress;
    private final BigDecimal sumOfPayment;

    public CustomerPaymentReportDTO(String customerName, String customerAddress, BigDecimal sumOfPayment) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.sumOfPayment = sumOfPayment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public BigDecimal getSumOfPayment() {
        return sumOfPayment;
    }
}
