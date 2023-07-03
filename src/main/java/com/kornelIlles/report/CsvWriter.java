package com.kornelIlles.report;

import com.kornelIlles.dto.CustomerPaymentReportDTO;
import com.kornelIlles.model.customer.Customer;
import com.kornelIlles.model.payment.CardPayment;
import com.kornelIlles.model.payment.Payment;
import com.kornelIlles.model.payment.TransferPayment;
import com.kornelIlles.utils.Utils;
import jdk.jshell.execution.Util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvWriter {

    private static CsvWriter csvWriter;
    private final String customerPaymentSum;
    private final String topCustomerPayment;
    private final String webShopTotalRevenue;


    private CsvWriter() {
        this.customerPaymentSum = Utils.getCsvPath("csv.customerPaymentSum");
        this.topCustomerPayment = Utils.getCsvPath("csv.topCustomerPayment");
        this.webShopTotalRevenue = Utils.getCsvPath("csv.webShopTotalRevenue");
    }

    public static CsvWriter getInstance() {
        if (csvWriter == null) {
            csvWriter = new CsvWriter();
        }
        return csvWriter;
    }

    public void writeClientPaymentSumReport(Map<Customer, List<Payment>> customerPayments) {
        try (PrintWriter pw = new PrintWriter(Utils.getCsvOutputStream(customerPaymentSum))) {
            for (Map.Entry<Customer, List<Payment>> entry : customerPayments.entrySet()) {
                Customer customer = entry.getKey();
                List<Payment> payments = entry.getValue();
                String line = String.format("%s;%s;%.2f", customer.getClientName(), customer.getClientAddress(), sumPayment(payments));
                pw.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeTopCustomer(List<CustomerPaymentReportDTO> topCustomers) {
        try (PrintWriter pw = new PrintWriter(Utils.getCsvOutputStream(topCustomerPayment))) {
            for (CustomerPaymentReportDTO topCustomer : topCustomers) {
                String line = String.format("%s;%s;%.2f", topCustomer.getCustomerName(), topCustomer.getCustomerAddress(), topCustomer.getSumOfPayment());
                pw.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeWebShopTotalRevenue(List<Payment> payments) {
        Map<String, BigDecimal[]> webShopTotals = getWebShopIdsAndRevenues(payments);

        try (PrintWriter pw = new PrintWriter(Utils.getCsvOutputStream(webShopTotalRevenue))) {
            for (Map.Entry<String, BigDecimal[]> entry : webShopTotals.entrySet()) {
                String webShopId = entry.getKey();
                BigDecimal[] totals = entry.getValue();
                BigDecimal transactionTotal = totals[0];
                BigDecimal cardPurchaseTotal = totals[1];

                String line = String.format("%s;%.2f;%.2f", webShopId, cardPurchaseTotal, transactionTotal);
                pw.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Map<String, BigDecimal[]> getWebShopIdsAndRevenues(List<Payment> payments) {
        Map<String, BigDecimal[]> webShopTotals = new HashMap<>();

        for (Payment payment : payments) {
            String webShopId = payment.getWebShopId();
            BigDecimal paymentAmount = payment.getAmount();

            BigDecimal[] currentTotals = webShopTotals.getOrDefault(webShopId, new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO});

            if (payment instanceof TransferPayment && ((TransferPayment) payment).getPaymentType().equalsIgnoreCase("transfer")) {
                BigDecimal transactionTotal = currentTotals[0].add(paymentAmount);
                currentTotals[0] = transactionTotal;
            } else if (((CardPayment) payment).getPaymentType().equalsIgnoreCase("card")) {
                BigDecimal cardPurchaseTotal = currentTotals[1].add(paymentAmount);
                currentTotals[1] = cardPurchaseTotal;
            }

            webShopTotals.put(webShopId, currentTotals);
        }
        return webShopTotals;
    }

    private static BigDecimal sumPayment(List<Payment> payments) {
        return payments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}