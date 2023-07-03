package com.kornelIlles;

import com.kornelIlles.webshoppaymentmanager.WebShopPaymentManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Start webShop payment manager!");
        if (args.length == 0) {
             WebShopPaymentManager.getInstance();
        }
        else if (args.length == 2){
            WebShopPaymentManager.getInstance(args);
        }
        else {
            System.out.println("You need to provide 0 or 2 arguments!");
        }
    }
}