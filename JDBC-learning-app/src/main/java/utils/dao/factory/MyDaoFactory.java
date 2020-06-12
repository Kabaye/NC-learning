package utils.dao.factory;

import utils.dao.DAO;
import utils.dao.customer.CustomerDaoImpl;
import utils.dao.order.OrderDaoImpl;

public class MyDaoFactory implements DaoFactory {
    @Override
    public DAO getOrderDAO() {
        return new OrderDaoImpl();
    }

    @Override
    public DAO getCustomerDAO() {
        return new CustomerDaoImpl();
    }
}
