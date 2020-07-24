package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;
import edu.netcracker.customer.utils.CustomerConfigurationProperties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author svku0919
 */
@Component
public class CustomerDBProcessorImpl implements CustomerDBProcessor {

    @Getter
//    @Value("${customer.money-precision}")

    private Integer moneyPrecision;

    @Autowired
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
        final double money = customer.getMoney() / moneyPrecision;
        return customer.setMoney(money);
    }
}
