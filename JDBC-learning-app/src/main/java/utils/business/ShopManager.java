package utils.business;

import shop.Customer;
import shop.Order;
import shop.PaymentData;
import utils.dao.DAO;
import utils.dao.customer.CustomerDaoImpl;
import utils.dao.factory.DaoFactory;
import utils.dao.factory.MyDaoFactory;

import java.util.List;

public class ShopManager {
    private final DaoFactory daoFactory = new MyDaoFactory();
    private final DAO<Order> orderDAO = daoFactory.getOrderDAO();
    private final DAO<Customer> customerDAO = daoFactory.getCustomerDAO();

    public Order addOrder(long customerID, List<Long> goodIDs, List<Integer> amounts) {
        Order order = null;
        if ((goodIDs.size() != amounts.size()) || (goodIDs.size() == 0 && amounts.size() == 0)) {
            System.out.println("You try to add invalid data to order (size of list of goods not equal to size of list of amounts OR they are empty), please check your data and try again.");
        } else {
            if (((CustomerDaoImpl) customerDAO).hasID(customerID)) {
                order = orderDAO.create(new Order(customerID, goodIDs, amounts));
            } else {
                System.out.println("There is no customer with such id. Add information about you!");
            }
        }
        return order;
    }

    public Order getOrder(long id) {
        Order order = null;
        order = orderDAO.read(id);
        return order;
    }

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    public Order updateOrder(long orderID, List<Long> goodIDs, List<Integer> amounts) {
        Order order = orderDAO.read(orderID);
        if ((goodIDs.size() != amounts.size()) || (goodIDs.size() == 0 && amounts.size() == 0)) {
            System.out.println("You try to update order with invalid data (size of list of goods not equal to size of list of amounts OR they are empty), please check your data and try again.");
            return null;
        }
        if (order == null) {
            System.out.println("There is no order with such ID. Please, firstly add order with such id!");
            return null;
        }

        order.setGoodIDs(goodIDs);
        order.setAmounts(amounts);

        if (goodIDs.size() == order.getIDs().size()) {
            order = orderDAO.update(order);
        } else {
            orderDAO.delete(orderID);
            orderDAO.create(order);
        }

        return order;
    }

    public void deleteOrder(long orderID) {
        orderDAO.delete(orderID);
    }

    public Customer addCustomer(String surnameName, long phone, long bankAccount, String accountCurrency) {
        return customerDAO.create(new Customer(surnameName, phone, new PaymentData(bankAccount, accountCurrency)));
    }

    public Customer getCustomer(long customerID) {
        return customerDAO.read(customerID);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    public Customer updateCustomer(long customerID, String surnameName, long phone, long bankAccount, String accountCurrency) {
        return customerDAO.update(Customer.of(customerID, surnameName, phone, PaymentData.of(customerID, bankAccount, accountCurrency)));
    }

    public void deleteCustomer(long customerID) {
        customerDAO.delete(customerID);
    }

}
