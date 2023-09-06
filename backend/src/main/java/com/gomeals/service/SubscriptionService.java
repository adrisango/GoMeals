package com.gomeals.service;

import java.util.List;

import com.gomeals.model.Subscriptions;

public interface SubscriptionService {

	public String addSubscription(Subscriptions subscription);

	public Subscriptions getSubscription(int sub_id);

	public List<Integer> getCustomersIdForSupplier(int id);

	public String updateSubscription(Subscriptions subscription);

	public String deleteSubscription(int sub_id);

	public List<Subscriptions> getPendingSubscription(int supplierId);

	public List<Integer> getAllCustomerSubscriptions(int cust_id);

}
