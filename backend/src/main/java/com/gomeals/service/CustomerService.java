package com.gomeals.service;

import com.gomeals.model.Customer;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer loginCustomer(Customer customer, HttpServletResponse response);
    Customer getCustomerById(int id);

    Customer updateCustomer(Customer customer);

}
