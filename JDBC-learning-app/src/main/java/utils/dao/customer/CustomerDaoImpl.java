package utils.dao.customer;

import shop.Customer;
import shop.PaymentData;
import utils.connection.ConnectionBuilder;
import utils.connection.SimpleConnectionBuilder;
import utils.dao.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements DAO<Customer> {

    private static final String EXCEPTION_MESSAGE = "404 Error! No connection to database!";

    private static final String GET_MAX_ID
            = "SELECT MAX(customer_id) as MAX_ID FROM customers";

    private static final String INSERT
            = "INSERT INTO customers (customer_id, surname_name, phone) VALUES (?, ?, ?)";
    private static final String INSERT_PAYMENT_DATA
            = "INSERT INTO payment_data (customer_id, bank_account, account_currency) VALUES (?,?,?)";

    private static final String SELECT_CUSTOMER_AND_PAYMENT_DATA_BY_CUSTOMER_ID
            = "SELECT customers.customer_id, surname_name, phone, bank_account, account_currency FROM customers " +
            "INNER JOIN payment_data ON customers.customer_id = payment_data.customer_id where customers.customer_id=?";

    private static final String SELECT_ALL
            = "SELECT customers.customer_id, surname_name, phone, bank_account, account_currency FROM customers " +
            "INNER JOIN payment_data ON customers.customer_id = payment_data.customer_id ORDER BY customer_id";

    private static final String UPDATE_CUSTOMER
            = "UPDATE customers SET surname_name=?, phone=? where customer_id=?";

    private static final String UPDATE_PAYMENT_DATA
            = "UPDATE payment_data SET bank_account=?, account_currency=? where customer_id=?";

    private static final String DELETE_PAYMENT_DATA
            = "DELETE FROM payment_data WHERE customer_id=?";

    private static final String DELETE_CUSTOMER
            = "DELETE FROM customers WHERE customer_id=?";

    private static final String HAS_ID
            = "SELECT customer_id FROM customers WHERE customer_id=?";

    private ConnectionBuilder builder = new SimpleConnectionBuilder();

    @Override
    public Customer create(Customer customer) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(GET_MAX_ID);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                int counter = set.getInt("MAX_ID") + 1;

                pst.close();
                set.close();

                pst = con.prepareStatement(INSERT);
                pst.setLong(1, counter);

                customer.setCustomerID(counter);
                customer.getPaymentData().setCustomerId(counter);

                pst.setString(2, customer.getSurnameName());
                pst.setLong(3, customer.getPhone());
                pst.executeUpdate();

                pst.close();

                pst = con.prepareStatement(INSERT_PAYMENT_DATA);
                pst.setLong(1, customer.getCustomerID());
                pst.setLong(2, customer.getPaymentData().getBankAccount());
                pst.setString(3, customer.getPaymentData().getAccountCurrency());
                pst.executeUpdate();

                pst.close();
                con.close();
            }
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customer;
    }

    @Override
    public Customer read(long customerID) {
        Customer customer = null;
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_CUSTOMER_AND_PAYMENT_DATA_BY_CUSTOMER_ID);
            pst.setLong(1, customerID);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                customer = Customer.of(set.getLong("customer_id"), set.getString("surname_name"),
                        set.getLong("phone"), PaymentData.of(set.getLong("customer_id"),
                                set.getLong("bank_account"), set.getString("account_currency")));
            }

            set.close();
            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_ALL);
            ResultSet set = pst.executeQuery();
            while (set.next()) {
                customers.add(Customer.of(set.getLong("customer_id"), set.getString("surname_name"),
                        set.getLong("phone"), PaymentData.of(set.getLong("customer_id"),
                                set.getLong("bank_account"), set.getString("account_currency"))));
            }

            set.close();
            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customers;
    }

    @Override
    public Customer update(Customer customer) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE_CUSTOMER);
            pst.setString(1, customer.getSurnameName());
            pst.setLong(2, customer.getPhone());
            pst.setLong(3, customer.getCustomerID());
            pst.executeUpdate();

            pst.close();

            pst = con.prepareStatement(UPDATE_PAYMENT_DATA);
            pst.setLong(1, customer.getPaymentData().getBankAccount());
            pst.setString(2, customer.getPaymentData().getAccountCurrency());
            pst.setLong(3, customer.getCustomerID());
            pst.executeUpdate();

            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customer;
    }

    /*
    // delete customer carefully, because some orders int "orders" table can have customer_id same as you want to delete,
    // so it will cause problems!
    */
    @Override
    public void delete(long customerID) {
        try {
            Connection con;
            con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(DELETE_PAYMENT_DATA);
            pst.setLong(1, customerID);
            pst.executeUpdate();

            pst.close();

            pst = con.prepareStatement(DELETE_CUSTOMER);
            pst.setLong(1, customerID);
            pst.executeUpdate();

            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
    }

    public boolean hasID(long customerID) {
        boolean hasID = false;
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(HAS_ID);
            pst.setLong(1, customerID);
            ResultSet set = pst.executeQuery();

            if (set.next()) {
                hasID = true;
            }

            set.close();
            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return hasID;
    }
}
