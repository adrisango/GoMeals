package com.gomeals.service.implementation;

import com.gomeals.model.CustomerNotification;
import com.gomeals.model.Subscriptions;
import com.gomeals.repository.CustomerNotificationRepository;
import com.gomeals.repository.SubscriptionRepository;
import com.gomeals.service.CustomerNotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Implementation of the CustomerNotificationService interface that provides
 * CRUD operations for managing customer notifications.
 */

@Service
public class CustomerNotificationServiceImplementation implements CustomerNotificationService {

    private final CustomerNotificationRepository custNotifRepo;
    private final SubscriptionRepository subscriptionRepository;

    public CustomerNotificationServiceImplementation(CustomerNotificationRepository custNotifRepo,
            SubscriptionRepository subscriptionRepository) {
        this.custNotifRepo = custNotifRepo;
        this.subscriptionRepository = subscriptionRepository;

    }
    /**
     * Creates a new customer notification.
     *
     * @param customerNotification The customer notification object to be created.
     * @return The created customer notification object.
     */
    @Override
    public CustomerNotification createNotification(CustomerNotification customerNotification) {
        return custNotifRepo.save(customerNotification);
    }

    /**
     * Retrieves a customer notification by its ID.
     *
     * @param notificationId The ID of the customer notification to be retrieved.
     * @return The customer notification object if found, or null if not found.
     */
    @Override
    public CustomerNotification getNotificationById(Integer notificationId) {
        return custNotifRepo.findById(notificationId).orElse(null);
    }
    /**
     * Retrieves all customer notifications by customer ID.
     *
     * @param customerId The ID of the customer for whom the notifications are to be retrieved.
     * @return A list of customer notification objects associated with the given customer ID.
     */
    @Override
    public List<CustomerNotification> getAllNotificationsByCustomerId(Integer customerId) {
        List<CustomerNotification> notifications = new ArrayList<>();
        custNotifRepo.findAllByCustomerId(customerId)
                .forEach(notification -> notifications.add(notification));
        return notifications;
    }
/**
 * Updates an existing customer notification.
 *
 * @param customerNotification The customer notification object to be updated.
 * @return The updated customer notification object if found, or null if not found.
 */
    @Override
    public CustomerNotification updateNotification(CustomerNotification customerNotification) {
        return custNotifRepo.findById(customerNotification.getNotificationId()).map(
                currentNotification -> {
                    currentNotification.setMessage(customerNotification.getMessage());
                    currentNotification.setEventType(customerNotification.getEventType());
                    currentNotification.setCustomerId(customerNotification.getCustomerId());
                    return custNotifRepo.save(currentNotification);
                }).orElse(null);
    }
    /**
     * Deletes a customer notification by its ID.
     *
     * @param notificationId The ID of the customer notification to be deleted.
     * @throws NoSuchElementException if the customer notification with the given ID is not found.
     */
    @Override
    public void deleteNotification(Integer notificationId) {
        if (custNotifRepo.findById(notificationId).isEmpty()) {
            throw new NoSuchElementException("Notification not found with id: " + notificationId);
        } else {
            custNotifRepo.deleteById(notificationId);
        }
    }

/**
 * Notifies all customers subscribed to a supplier.
 *
 * @param message The notification message
 * @param type The notification type
 * @param supplierId The ID of the supplier
 * @return True if notifications are successfully sent, false ifnot
 */
    public Boolean notifyAllSupplierCustomers(String message, String type, int supplierId){


        List<Subscriptions> supplierSubscriptions;

        supplierSubscriptions = subscriptionRepository
                .findSubscriptionsBySupplierIdAndActiveStatus(supplierId, 1);
        if (supplierSubscriptions.isEmpty()) {
            System.out.println("This supplier doesn't have any subscribed customers.");
            return false;
        }

        for (Subscriptions subscription : supplierSubscriptions) {
            CustomerNotification customerNotification = new CustomerNotification();
            customerNotification.setMessage(message);
            customerNotification.setEventType(type);
            customerNotification.setCustomerId(subscription.getCustomerId());
            custNotifRepo.save(customerNotification);
        }
        System.out.println("Successfully saved notifications for all the customers.");

        return true;
    }
}
