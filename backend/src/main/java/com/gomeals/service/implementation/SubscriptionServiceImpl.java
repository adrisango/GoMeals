package com.gomeals.service.implementation;

import com.gomeals.model.Customer;
import com.gomeals.repository.CustomerRepository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomeals.model.Subscriptions;
import com.gomeals.repository.SubscriptionRepository;
import com.gomeals.service.SubscriptionService;

import jakarta.transaction.Transactional;
import java.util.Collections;
/**
 * Implementation of subscription service interface
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	@Autowired
	SubscriptionRepository subscriptionRepository;

	@Autowired
	CustomerRepository customerRepository;
	/**
	 * Adds a subscription to the subscription table.
	 *
	 * @param subscription The subscription to be added.
	 * @return A message indicating the result of the operation.
	 */

	@Transactional
	public String addSubscription(Subscriptions subscription) {
		subscriptionRepository.save(subscription);
		return "Subscription added to the subscription table";
	}
	/**
	 * Retrieves a subscription by subscription ID.
	 *
	 * @param subId The ID of the subscription to retrieve.
	 * @return The retrieved subscription, or null if not found.
	 */

	@Transactional
	public Subscriptions getSubscription(int subId) {
		return subscriptionRepository.findById(subId).orElse(null);

	}

	/**
	 * Updates a subscription in the subscription table.
	 *
	 * @param subscription The subscription to be updated.
	 * @return A message indicating the result of the operation.
	 */

	@Transactional
	public String updateSubscription(Subscriptions subscription) {
		Subscriptions latestSubscription = subscriptionRepository.findById(subscription.getSub_id()).orElse(null);
		latestSubscription.setActiveStatus(subscription.getActiveStatus());
		latestSubscription.setStatus(subscription.getStatus());
		latestSubscription.setMeals_remaining(subscription.getMeals_remaining());
		latestSubscription.setSub_date(subscription.getSub_date());
		subscriptionRepository.save(latestSubscription);
		return "Subscription updated successfully.";
	}

	/**
	 * Deletes a subscription by subscription ID.
	 *
	 * @param subId The ID of the subscription to be deleted.
	 * @return A message indicating the result of the operation.
	 */

	@Transactional
	public String deleteSubscription(int subId) {
		subscriptionRepository.deleteById(subId);

		return "Subscription deleted successfully.";

	}

	/**
	 * Retrieves a list of customer IDs for a given supplier ID.
	 *
	 * @param supId The ID of the supplier.
	 * @return A list of customer IDs.
	 */

	@Override
	public List<Integer> getCustomersIdForSupplier(int supId) {
		return subscriptionRepository.getCustomersIdForSupplier(supId);

	}

	/**
	 * Retrieves a list of all customer subscriptions for a given customer ID.
	 *
	 * @param custId The ID of the customer.
	 * @return A list of supplier IDs for the customer's subscriptions.
	 */
	@Override
	public List<Integer> getAllCustomerSubscriptions(int custId) {
		List<Integer> listOfSuppliersForCustomers = new ArrayList<>();
		List<Subscriptions> litstOfSubscriptions;
		litstOfSubscriptions = subscriptionRepository.findSubscriptionsByCustomerIdAndActiveStatus(custId, 1);

		litstOfSubscriptions.forEach(subscription -> listOfSuppliersForCustomers.add(subscription.getSupplierId()));

		return listOfSuppliersForCustomers;
	}

	/**
	 * Retrieves a list of pending subscriptions for a given supplier ID.
	 *
	 * @param supplierId The ID of the supplier.
	 * @return A list of pending subscriptions.
	 */

	@Override
	public List<Subscriptions> getPendingSubscription(int supplierId) {
		List<Subscriptions> pendingSubscriptions;
		pendingSubscriptions = subscriptionRepository.findByActiveStatusAndStatusAndSupplierId(0,
				"Pending", supplierId);
		if (pendingSubscriptions.isEmpty()) {
			return Collections.emptyList();
		}
		pendingSubscriptions.forEach(pendingSubscription -> {
			Customer currentCustomer;

			currentCustomer = customerRepository.findById(pendingSubscription.getCustomerId()).orElse(null);
			pendingSubscription.setCustomer(currentCustomer);
		});
		return pendingSubscriptions;
	}

}
