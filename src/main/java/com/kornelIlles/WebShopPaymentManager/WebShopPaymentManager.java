package com.kornelIlles.WebShopPaymentManager;

import com.kornelIlles.CSVReader.CsvReader;
import com.kornelIlles.model.customer.Customer;
import com.kornelIlles.model.payment.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebShopPaymentManager {

    private static WebShopPaymentManager webShopPaymentManager;

    private final HashMap<Customer, List<Payment>> customerPayments;
    private final CsvReader csvReader;


    private WebShopPaymentManager() {
        this.csvReader = CsvReader.getInstance();
        customerPayments = new HashMap<>();
        Map<String, Customer> customers = csvReader.readCustomerCSV();
        List<Payment> payments = csvReader.readPaymentCSV();
        fillTheCustomerPayments(customers, payments);
    }

    public static WebShopPaymentManager getInstance() {
        if (webShopPaymentManager == null) {
            webShopPaymentManager = new WebShopPaymentManager();
        }
        return webShopPaymentManager;
    }

    private void fillTheCustomerPayments(Map<String, Customer> customers, List<Payment> payments) {
        for (Payment payment : payments) {
            String key = payment.getWebShopId() + "-" + payment.getClientId();
            Customer customer = customers.get(key);

            if (customer != null) {
                List<Payment> paymentList = customerPayments.getOrDefault(customer, new ArrayList<>());
                paymentList.add(payment);
                customerPayments.put(customer, paymentList);
            }
        }
    }
}
