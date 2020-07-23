package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.util.CustomerConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author svku0919
 */
@Service
public class CustomerDBProcessorImpl implements CustomerDBProcessor {
    private final Integer moneyPrecision;

    public CustomerDBProcessorImpl(CustomerConfigurationProperties customerConfigurationProperties) {
        moneyPrecision = customerConfigurationProperties.getMoneyPrecision();
    }

    @Override
    public Customer processCustomerToDB(Customer customer) {
        final double money = (long) (customer.getMoney() * moneyPrecision);
        return customer.setMoney(money);
    }

    @Override
    public Customer processCustomerFromDB(Customer customer) {
        final double money = (long) (customer.getMoney() / moneyPrecision);
        return customer.setMoney(money);
    }
}
