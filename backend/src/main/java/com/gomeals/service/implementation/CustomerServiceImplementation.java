package com.gomeals.service.implementation;

import com.gomeals.model.Customer;
import com.gomeals.model.Subscriptions;
import com.gomeals.model.Supplier;
import com.gomeals.repository.CustomerRepository;
import com.gomeals.repository.SubscriptionRepository;
import com.gomeals.repository.SupplierRepository;
import com.gomeals.service.CustomerService;
import com.gomeals.utils.UserSecurity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;


import java.util.*;
/**
 * Implementation of customer service interface
 */
@Service
public class CustomerServiceImplementation implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    public CustomerServiceImplementation() {
        super();
    }

    UserSecurity userSecurity = new UserSecurity();
    /**
     * Create a new customer.
     *
     * @param customer The customer object to be created.
     * @return The created customer object.
     * @throws RuntimeException If the email already exists.
     */

    @Override
    public Customer createCustomer(Customer customer) {

        if (customerRepository.findByEmail(customer.getCust_email()) != null) {
            throw new RuntimeException("Email already exists");
        }
        customer.setCust_password(userSecurity.encryptData(customer.getCust_password()));
        return customerRepository.save(customer);
    }

    public String printCookie(@CookieValue(name = "loggedInUser", defaultValue = "") String loggedInUser) {
        return loggedInUser;
    }

    /**
     * Print the value of the "loggedInUser" cookie.
     *
     * @param customer The value of the "loggedInUser" cookie.
     * @return The value of the "loggedInUser" cookie.
     */
    @Override
    public Customer loginCustomer(Customer customer, HttpServletResponse response) {
        Customer customerData = customerRepository.findByEmail(customer.getCust_email());
        if (customerData == null) {
            throw new RuntimeException("User not Registered");
        } else {
            String password = customerRepository.passwordMatch(customer.getCust_email());
            if (Objects.equals(userSecurity.decryptData(password), customer.getCust_password())) {
                try {
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
                return customerData;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                customerData = null;
                System.out.println("Incorrect password");
            }
        }
        return customerData;
    }
    /**
     * Get a customer by ID with their associated suppliers and subscriptions.
     *
     * @param id The ID of the customer.
     * @return The customer object with associated suppliers and subscriptions.
     */
    @Override
    public Customer getCustomerById(int id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {

            List<Supplier> suppliers = new ArrayList<>();
            List<Subscriptions> subscriptions = new ArrayList<>();
            subscriptionRepository.findSubscriptionsByCustomerIdAndActiveStatus(customer.getCust_id(), 1).forEach(
                    subscription -> {
                        // For each supplier that the customer is subbed to, store the supplier details
                        Optional<Supplier> supplier = supplierRepository.findById(subscription.getSupplierId());
                        suppliers.add(unwrapSupplier(supplier));
                        // Store the subscription details
                        subscriptions.add(subscription);
                    }
            );
            customer.setSuppliers(suppliers);
            customer.setSubscriptions(subscriptions);

        }
        return customer;
    }

    /**
     * Update a customer's information.
     *
     * @param customer The customer object with updated information.
     * @return The updated customer object.
     */
    public Customer updateCustomer(Customer customer) {
        return customerRepository.findById(customer.getCust_id()).map(currentCustomer -> {
            currentCustomer.setCust_email(customer.getCust_email());
            currentCustomer.setCust_contact_number(customer.getCust_contact_number());
            currentCustomer.setCust_address(customer.getCust_address());
            return customerRepository.save(currentCustomer);
        }).orElse(null);
    }
    /**
     * Utility method to unwrap an Optional<Supplier> to Supplier object.
     *
     * @param entity The Optional<Supplier> object.
     * @return The unwrapped Supplier object or null if not present.
     */
    private static Supplier unwrapSupplier(Optional<Supplier> entity) {
        return entity.orElse(null);
    }


}
