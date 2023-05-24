package com.kornelIlles.WebShopPaymentManager;

import com.kornelIlles.dto.CustomerPaymentReportDTO;
import com.kornelIlles.report.CsvReader;
import com.kornelIlles.model.customer.Customer;
import com.kornelIlles.model.payment.Payment;
import com.kornelIlles.report.CsvWriter;

import java.util.*;
import java.util.stream.Collectors;

public class WebShopPaymentManager {

    private static WebShopPaymentManager webShopPaymentManager;
    private final HashMap<Customer, List<Payment>> customerPayments;
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;


    private WebShopPaymentManager() {
        this.csvReader = CsvReader.getInstance();
        this.csvWriter = CsvWriter.getInstance();
        customerPayments = new HashMap<>();
        Map<String, Customer> customers = csvReader.readCustomerCSV();
        List<Payment> payments = csvReader.readPaymentCSV();
        fillTheCustomerPayments(customers, payments);
        csvWriter.writeClientPaymentSumReport(customerPayments);
        List<CustomerPaymentReportDTO> topCustomer = getTopCustomers(csvReader.readCustomerPaymentSum());
        csvWriter.writeTopCustomer(topCustomer);
        csvWriter.writeWebShopTotalRevenue(payments);
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

    private List<CustomerPaymentReportDTO> getTopCustomers(List<CustomerPaymentReportDTO> readCustomerPaymentSum) {
        List<CustomerPaymentReportDTO> sortedReadCustomerPaymentSum = readCustomerPaymentSum.stream()
                .sorted(Comparator.comparing(CustomerPaymentReportDTO::getSumOfPayment).reversed())
                .toList();

        return sortedReadCustomerPaymentSum.stream().limit(2).toList();
    }
}
