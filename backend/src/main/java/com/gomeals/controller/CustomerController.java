package com.gomeals.controller;

import com.gomeals.model.Customer;
import com.gomeals.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")

@CrossOrigin
/**
 * This controller contains the methods which performs CRUD operation on the
 * customer model
 */
public class CustomerController {
    @Autowired
    CustomerService CustomerService;

    /**
     * This method returns a single customer details based on its ID
     * 
     * @param id Add-on ID
     * @return customer object
     */
    @CrossOrigin

    @GetMapping("/get/{id}")
    public Customer getCustomerById(@PathVariable("id") int id) {
        return CustomerService.getCustomerById(id);
    }

    /**
     * This method accepts a customer object stores it in the database
     * and returns the object which is stored in the database
     * 
     * @param Customer object
     * @return customer object
     */
    @CrossOrigin

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer Customer) {
        return CustomerService.createCustomer(Customer);
    }

    /**
     * This method is used to authenticate the customer and approve the login
     * 
     * @param customer
     * @param response
     * @return customer object
     */
    @PostMapping("/login")
    public Customer loginCustomer(@RequestBody Customer customer, HttpServletResponse response) {
        return CustomerService.loginCustomer(customer, response);
    }

    /**
     * This method accepts a customer object updates its details in the database
     * and returns the object which is updated in the database
     * 
     * @param customer object
     * @return customer object
     */
    @PutMapping("/update")
    public Customer updateCustomer(@RequestBody Customer customer) {
        return CustomerService.updateCustomer(customer);
    }
}
