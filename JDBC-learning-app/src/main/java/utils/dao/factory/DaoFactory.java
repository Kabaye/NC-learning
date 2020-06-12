package utils.dao.factory;

import utils.dao.DAO;

public interface DaoFactory {
    DAO getOrderDAO();

    DAO getCustomerDAO();
}
