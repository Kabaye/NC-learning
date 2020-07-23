package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;
import org.springframework.stereotype.Service;

/**
 * @author svku0919
 */
@Service
public class CustomerDBProcessorImpl implements CustomerDBProcessor {
    @Override
    public Customer processCustomerToDB(Customer customer) {
        final double money = (long) (customer.getMoney() * 1000D);
        return customer.setMoney(money);
    }

    @Override
    public Customer processCustomerFromDB(Customer customer) {
        final double money = ((long) (customer.getMoney() / 10)) / 100D;
        return customer.setMoney(money);
    }
}
