package com.gomeals.service.implementation;

import com.gomeals.model.Customer;
import com.gomeals.model.Subscriptions;
import com.gomeals.repository.CustomerRepository;
import com.gomeals.repository.SubscriptionRepository;
import com.gomeals.repository.SupplierRepository;
import com.gomeals.model.Supplier;
import com.gomeals.repository.SupplierReviewRepository;
import com.gomeals.service.SupplierService;
import com.gomeals.utils.UserSecurity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of supplier service interface
 */
@Service
public class SupplierServiceImplementation implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SupplierReviewRepository supplierReviewRepository;

    UserSecurity userSecurity = new UserSecurity();

    /**
     * Retrieve the details of a supplier by supplier ID
     *
     * @param id The ID of the supplier
     * @return The Supplier object containing the details of the supplier
     */
    public Supplier getSupplierDetails(int id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if (supplier != null) {

            supplier.setSupplierRating(supplierReviewRepository.findSupplierAverage(id));

            List<Customer> customers = new ArrayList<>();
            List<Subscriptions> subscriptions = new ArrayList<>();

            subscriptionRepository.findSubscriptionsBySupplierIdAndActiveStatus(id, 1).forEach(
                    subscribedCustomer -> {
                        Optional<Customer> customer = customerRepository.findById(subscribedCustomer.getCustomerId());
                        customers.add(unwrapCustomer(customer));
                        // Store the subscription details
                        subscriptions.add(subscribedCustomer);
                    });
            supplier.setCustomers(customers);
            supplier.setSubscriptions(subscriptions);
        }
        return supplier;
    }

    /**
     * Retrieve details of all suppliers
     *
     * @return List of Supplier objects containing the details of all suppliers
     */
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();

        supplierRepository.findAll().forEach(supplier -> {
            Double rating = supplierReviewRepository.findSupplierAverage(supplier.getSupId());
            supplier.setSupplierRating(rating);
            suppliers.add(supplier);
        });
        return suppliers;
    }

    /**
     * Register a new supplier
     *
     * @param supplier The Supplier object containing the details of the supplier to be registered
     * @return The registered Supplier object
     * @throws RuntimeException If the supplier email already exists
     */
    public Supplier registerSupplier(Supplier supplier) {
        if (supplierRepository.findSupplierByEmail(supplier.getSupEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }
        supplier.setPassword(userSecurity.encryptData(supplier.getPassword()));
        return supplierRepository.save(supplier);
    }

    /**
     * Update the details of a supplier
     *
     * @param supplier The Supplier object containing the updated details of the supplier
     * @return The updated Supplier object
     */
    public Supplier updateSupplier(@RequestBody Supplier supplier) {
        Supplier s = supplierRepository.findById(supplier.getSupId()).orElse(null);
        s.setSupName(supplier.getSupName());
        s.setSupContactNumber(supplier.getSupContactNumber());
        s.setSupEmail(supplier.getSupEmail());
        s.setSupAddress(supplier.getSupAddress());
        s.setGovtIssuedId(supplier.getGovtIssuedId());
        s.setPassword(supplier.getPassword());
        supplierRepository.save(s);
        return supplier;

    }

    /**
     * Delete a supplier by supplier ID
     *
     * @param id The ID of the supplier to be deleted
     * @return A String indicating that the supplier has been deleted
     */
    public String deleteSupplier(int id) {
        supplierRepository.deleteById(id);
        return "Supplier deleted";
    }

    /**
     * Login a supplier
     *
     * @param supplier The Supplier object containing the email and password of the supplier for login
     * @param response The HttpServletResponse object for setting the response status
     * @return The logged-in Supplier object
     * @throws RuntimeException If the supplier is not registered or password does not match
     */
    public Supplier loginSupplier(Supplier supplier, HttpServletResponse response) {
        Supplier supplierData = supplierRepository.findSupplierByEmail(supplier.getSupEmail());
        if (supplierData == null) {
            throw new RuntimeException("Supplier not Registered");
        } else {
            String password = supplierRepository.supplierPasswordMatch(supplier.getSupEmail());
            if (Objects.equals(userSecurity.decryptData(password), supplier.getPassword())) {
                try {
                    response.setStatus(HttpServletResponse.SC_OK);
                } catch (Exception e) {

                    System.out.println(e.getMessage());
                }
                return supplierData;
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                supplierData = null;
            }
        }
        return supplierData;
    }

    /**
     * Utility method to unwrap an Optional<Customer> entity
     *
     * @param entity The Optional<Customer> entity to be unwrapped
     * @return The unwrapped Customer object, or null if not present
     */
    private static Customer unwrapCustomer(Optional<Customer> entity) {
        return entity.orElse(null);
    }

}