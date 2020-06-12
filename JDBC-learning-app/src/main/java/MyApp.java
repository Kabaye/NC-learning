import shop.Customer;
import shop.Order;
import utils.business.ShopManager;

import java.util.ArrayList;
import java.util.List;

public class MyApp {

    public static void main(String[] args) {
        ShopManager manager = new ShopManager();

        long customerID = 0;
        long orderID = 0;
        String surnameName = "";
        long phone = 0;
        long bankAccount = 0;
        String accountCurrency = "";
        List<Long> goodIDs = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();


        System.out.println("All orders");
        for (Order elem : manager.getAllOrders()) {
            System.out.println(elem);
        }
        System.out.println();

        customerID = 3;

        goodIDs.add(9l);
        goodIDs.add(2l);
        goodIDs.add(3l);

        amounts.add(5);
        amounts.add(10);
        amounts.add(15);

        System.out.println("Create new order with existent good's IDs and customerID");
        System.out.println(manager.addOrder(customerID, goodIDs, amounts));
        System.out.println();

        customerID = 100;
        System.out.println("Create new order with non-existent customerID");
        System.out.println(manager.addOrder(customerID, goodIDs, amounts));
        System.out.println();

        orderID = 4;
        System.out.println("Get order by ID (existent and non-existent ID)");
        System.out.println(manager.getOrder(orderID));
        orderID = 100;
        System.out.println(manager.getOrder(orderID));
        System.out.println();

        orderID = 8l;
        goodIDs.clear();
        amounts.clear();

        goodIDs.add(8l);
        goodIDs.add(4l);
        goodIDs.add(1l);

        amounts.add(3);
        amounts.add(5);
        amounts.add(1);

        System.out.println("Update some existent order");
        System.out.println(manager.updateOrder(orderID, goodIDs, amounts));
        System.out.println();

        orderID = 6;
        System.out.println("Delete order by ID (existent and non-existent ID)");
        manager.deleteOrder(orderID);
        orderID = 555;
        manager.deleteOrder(orderID);

        System.out.println("All orders");
        for (Order elem : manager.getAllOrders()) {
            System.out.println(elem);
        }
        System.out.println();

        System.out.println("All customers");
        for (Customer elem : manager.getAllCustomers()) {
            System.out.println(elem);
        }
        System.out.println();

        surnameName = "Якшин Никита";
        phone = 375446589652L;
        bankAccount = 4125800690785362L;
        accountCurrency = "BYN";
        System.out.println("Add new customer");
        System.out.println(manager.addCustomer(surnameName, phone, bankAccount, accountCurrency));
        System.out.println();

        System.out.println("All customers");
        for (Customer elem : manager.getAllCustomers()) {
            System.out.println(elem);
        }
        System.out.println();

        System.out.println("Get customer by ID (existent and non-existent)");
        customerID = 5l;
        System.out.println(manager.getCustomer(customerID));
        customerID = 100l;
        System.out.println(manager.getCustomer(customerID));
        System.out.println();

        System.out.println("Update customer existent and non-existent ID");
        surnameName = "Гаврилов Владимир";
        phone = 375295874165L;
        bankAccount = 4219_0007_1236_5233L;
        accountCurrency = "USD";
        customerID = 5l;
        System.out.println(manager.updateCustomer(customerID, surnameName, phone, bankAccount, accountCurrency));
        customerID = 100l;
        System.out.println(manager.updateCustomer(customerID, surnameName, phone, bankAccount, accountCurrency));
        System.out.println();

        System.out.println("Delete user by ID");
        customerID = 8l;
        manager.deleteCustomer(customerID);
        customerID = 100l;
        manager.deleteCustomer(customerID);
        System.out.println("All customers");
        for (Customer elem : manager.getAllCustomers()) {
            System.out.println(elem);
        }
        System.out.println();


    }
}
