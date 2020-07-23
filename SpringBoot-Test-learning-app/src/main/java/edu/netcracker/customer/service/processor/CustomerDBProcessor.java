package edu.netcracker.customer.service.processor;

import edu.netcracker.customer.entity.Customer;

public interface CustomerDBProcessor {
    Customer processCustomerToDB(Customer customer);

    Customer processCustomerFromDB(Customer customer);
}
