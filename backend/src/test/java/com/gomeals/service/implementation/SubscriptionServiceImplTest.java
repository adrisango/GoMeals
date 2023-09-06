package com.gomeals.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gomeals.model.Customer;
import com.gomeals.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.gomeals.model.Subscriptions;
import com.gomeals.repository.SubscriptionRepository;

@SpringBootTest
public class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionServiceImpl;

    @Test
    public void testAddSubscription() {
        Subscriptions subscription = new Subscriptions();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        subscription.setSub_id(1);
        subscription.setMeals_remaining(10);
        subscription.setSub_date(date);

        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        String result = subscriptionServiceImpl.addSubscription(subscription);
        assertEquals("Subscription added to the subscription table", result);
    }

    @Test
    public void testGetSubscription() {
        Subscriptions subscription = new Subscriptions();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        subscription.setSub_id(1);
        subscription.setMeals_remaining(10);
        subscription.setSub_date(date);

        when(subscriptionRepository.findById(1)).thenReturn(java.util.Optional.of(subscription));

        Subscriptions result = subscriptionServiceImpl.getSubscription(1);
        assertNotNull(result);
        assertEquals(1, result.getSub_id());
        assertEquals(10, result.getMeals_remaining());
        assertEquals(date, result.getSub_date());
    }

    @Test
    public void testUpdateSubscription() {
        Subscriptions subscription = new Subscriptions();
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        subscription.setSub_id(1);
        subscription.setMeals_remaining(10);
        subscription.setSub_date(date);

        Subscriptions latestSubscription = new Subscriptions();
        latestSubscription.setSub_id(1);
        latestSubscription.setMeals_remaining(20);
        latestSubscription.setSub_date(date);

        when(subscriptionRepository.findById(1)).thenReturn(Optional.of(latestSubscription));
        when(subscriptionRepository.save(latestSubscription)).thenReturn(latestSubscription);

        String result = subscriptionServiceImpl.updateSubscription(subscription);
        assertEquals("Subscription updated successfully.", result);
        assertEquals(1, latestSubscription.getSub_id());
        assertEquals(10, latestSubscription.getMeals_remaining());
        assertEquals(date, latestSubscription.getSub_date());
    }

    @Test
    public void testDeleteSubscription() {
        Mockito.doNothing().when(subscriptionRepository).deleteById(1);

        String result = subscriptionServiceImpl.deleteSubscription(1);
        assertEquals("Subscription deleted successfully.", result);
    }

    @Test
    public void testGetCustomersIdForSupplier() {
        List<Integer> customers = new ArrayList<>();
        customers.add(1);
        customers.add(2);
        customers.add(3);

        when(subscriptionRepository.getCustomersIdForSupplier(1)).thenReturn(customers);

        List<Integer> result = subscriptionServiceImpl.getCustomersIdForSupplier(1);
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(customers, result);
    }

    @Test
    public void testGetAllCustomerSubscriptions() {
        // Set up the test data
        int customerId = 123;
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);

        List<Subscriptions> subscriptions = new ArrayList<>();

        subscriptions.add(new Subscriptions(1, 29, date, 1, customerId, 456));
        subscriptions.add(new Subscriptions(2, 13, date, 1, customerId, 789));
        when(subscriptionRepository.findSubscriptionsByCustomerIdAndActiveStatus(customerId, 1))
                .thenReturn(subscriptions);

        // Calling the getAllCustomerSubscriptions() under test
        List<Integer> supplierIds = subscriptionServiceImpl.getAllCustomerSubscriptions(customerId);

        // Verifying the results returned by getAllCustomerSubscriptions()
        assertEquals(2, supplierIds.size());
        assertTrue(supplierIds.contains(456));
        assertTrue(supplierIds.contains(789));
    }

    @Test
    public void testGetAllCustomerSubscriptionsNoSubscriptionsFound() {
        // Set up the test data
        int customerId = 123;
        when(subscriptionRepository.findSubscriptionsByCustomerIdAndActiveStatus(customerId, 1))
                .thenReturn(Collections.emptyList());

        // Call the method under test
        List<Integer> supplierIds = subscriptionServiceImpl.getAllCustomerSubscriptions(customerId);

        // Verify the results
        assertEquals(0, supplierIds.size());
    }

    @Test
    public void testGetPendingSubscriptionWhenNoPendingSubscriptions() {
        int supplierId = 1;
        String status = "Pending";
        int activeStatus = 0;

        when(subscriptionRepository.findByActiveStatusAndStatusAndSupplierId(activeStatus, status, supplierId))
                .thenReturn(Collections.emptyList());

        List<Subscriptions> pendingSubscriptions = subscriptionServiceImpl.getPendingSubscription(1);

        verify(subscriptionRepository).findByActiveStatusAndStatusAndSupplierId(activeStatus, status, supplierId);
        assertTrue(pendingSubscriptions.isEmpty());
    }

    @Test
    public void testGetPendingSubscriptionWhenPendingSubscriptionsExist() {

        Subscriptions subscription = new Subscriptions();
        subscription.setSub_id(1);
        subscription.setCustomerId(2);
        subscription.setSupplierId(3);
        subscription.setActiveStatus(0);
        subscription.setStatus("Pending");

        int supplierId = 3;
        String status = "Pending";
        int activeStatus = 0;

        when(subscriptionRepository.findByActiveStatusAndStatusAndSupplierId(activeStatus, status, supplierId))
                .thenReturn(List.of(subscription));

        Customer customer = new Customer();
        customer.setCust_id(1);
        customer.setCust_fname("Alice");
        customer.setCust_email("alice@example.com");

        when(customerRepository.findById(2)).thenReturn(java.util.Optional.of(customer));

        List<Subscriptions> pendingSubscriptions = subscriptionServiceImpl.getPendingSubscription(3);

        verify(subscriptionRepository).findByActiveStatusAndStatusAndSupplierId(0, "Pending", 3);
        verify(customerRepository).findById(2);
        assertEquals(1, pendingSubscriptions.size());
        Subscriptions actualSubscription = pendingSubscriptions.get(0);
        assertEquals(subscription.getSub_id(), actualSubscription.getSub_id());
        assertEquals(subscription.getCustomerId(), actualSubscription.getCustomerId());
        assertEquals(subscription.getSupplierId(), actualSubscription.getSupplierId());
        assertEquals(subscription.getActiveStatus(), actualSubscription.getActiveStatus());
        assertEquals(subscription.getStatus(), actualSubscription.getStatus());
        Customer actualCustomer = actualSubscription.getCustomer();
        assertEquals(customer.getCust_id(), actualCustomer.getCust_id());
        assertEquals(customer.getCust_fname(), actualCustomer.getCust_fname());
        assertEquals(customer.getCust_email(), actualCustomer.getCust_email());
    }
}
