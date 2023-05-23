package com.kornelIlles.report;

import com.kornelIlles.dto.CustomerPaymentReportDTO;
import com.kornelIlles.model.customer.Customer;
import com.kornelIlles.model.payment.CardPayment;
import com.kornelIlles.model.payment.Payment;
import com.kornelIlles.model.payment.TransferPayment;
import com.kornelIlles.utils.Utils;
import com.kornelIlles.utils.validator.LineValidator;
import com.kornelIlles.utils.validator.MyLineValidator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class CsvReader {

    private final String paymentPath;
    private final String customerPath;
    private final String customerPaymentSum;
    private static CsvReader csvReader;

    private CsvReader() {
        this.paymentPath = Utils.getCsvPath("csv.payments");
        this.customerPath = Utils.getCsvPath("csv.customer");
        this.customerPaymentSum = Utils.getCsvPath("csv.customerPaymentSum");
    }

    public static CsvReader getInstance() {
        if (csvReader == null) {
            csvReader = new CsvReader();
        }
        return csvReader;
    }

    public Map<String, Customer> readCustomerCSV() {
        Map<String, Customer> customers = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(customerPath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Customer customer = convertToCustomer(nextLine[0].split(";"));
                String key = customer.getWebShopId() + "-" + customer.getClientId();
                customers.put(key, customer);
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public List<Payment> readPaymentCSV() {
        List<Payment> payments = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(paymentPath))) {
            String[] nextLine;
            LineValidator myLineValidator = new MyLineValidator();
            while ((nextLine = reader.readNext()) != null) {
                String[] lineFields = nextLine[0].split(";");
                if (myLineValidator.isValid(lineFields)) {
                    Payment payment = convertToPayment(lineFields);
                    payments.add(payment);
                }
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<CustomerPaymentReportDTO> readCustomerPaymentSum() {
        List<CustomerPaymentReportDTO> customersPayment = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(customerPaymentSum))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                CustomerPaymentReportDTO customerPaymentReportDTO = convertToCustomerPaymentReportDTO(nextLine[0].split(";"));
                customersPayment.add(customerPaymentReportDTO);

            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        }

        return customersPayment;
    }

    private CustomerPaymentReportDTO convertToCustomerPaymentReportDTO(String[] customerPaymentData) {
        String customerName = customerPaymentData[0];
        String customerAddress = customerPaymentData[1];
        BigDecimal sumOfPayments = new BigDecimal(customerPaymentData[2]);
        return new CustomerPaymentReportDTO(customerName, customerAddress, sumOfPayments);
    }

    private Payment convertToPayment(String[] PaymentData) {

        String webShopId = PaymentData[0];
        String clientId = PaymentData[1];
        String paymentMethod = PaymentData[2];
        BigDecimal amount = new BigDecimal(PaymentData[3]);
        Date date = Utils.stringToDate(PaymentData[6]);

        if (paymentMethod.equals("transfer")) {
            String accountNumber = PaymentData[4];
            return new TransferPayment(webShopId, clientId, amount, date, paymentMethod, accountNumber);
        } else if (paymentMethod.equals("card")) {
            String cardNumber = PaymentData[5];
            return new CardPayment(webShopId, clientId, amount, date, paymentMethod, cardNumber);
        }
        return null;
    }

    private Customer convertToCustomer(String[] customerData) {
        String webShopId = customerData[0];
        String clientId = customerData[1];
        String clientName = customerData[2];
        String clientAddress = customerData[3];
        return new Customer(webShopId, clientId, clientName, clientAddress);
    }
}
