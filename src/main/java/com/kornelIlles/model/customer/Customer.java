package com.kornelIlles.model.customer;


public class Customer {
    private final String webShopId;
    private final String clientId;
    private final String clientName;
    private final String clientAddress;

    public Customer(String webShopId, String clientId, String clientName, String clientAddress) {
        this.webShopId = webShopId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }

    public String getWebShopId() {
        return webShopId;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }
}