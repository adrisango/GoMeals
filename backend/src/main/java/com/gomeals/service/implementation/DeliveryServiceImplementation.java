package com.gomeals.service.implementation;

import com.gomeals.constants.Constants;
import com.gomeals.model.*;
import com.gomeals.repository.*;
import com.gomeals.service.DeliveryService;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.gomeals.constants.DeliveryStatus.*;
/**
 * Service implementation for handling delivery operations.
 * Implements the DeliveryService interface.
 */
@Service
public class DeliveryServiceImplementation implements DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    MealChartRepository mealChartRepository;
    @Autowired
    PollingRepository pollingRepository;
    @Autowired
    CustomerNotificationRepository custNotifiyRepo;

    /**
     * Creates a delivery for a customer.
     * @param delivery The Delivery object to create.
     * @return Boolean value indicating if the delivery was successfully created or not.
     */
    @Transactional
    @Override
    public Boolean createDelivery(Delivery delivery) {

        int supplierId = delivery.getSupId();
        int customerId = delivery.getCustId();

        LocalDateTime todayDate = LocalDateTime.now();
        LocalDateTime tomorrowDate = todayDate.plusDays(1);

        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        if (supplier == null) {
            System.out.println("That supplier does not exist.");
            return false;
        }

        // Verify that there is no active delivery for that date
        Delivery newDelivery;
        newDelivery = deliveryRepository.findBySupIdAndCustIdAndDeliveryDateAndOrderStatus(supplierId,
                customerId, tomorrowDate.toLocalDate(), IN_PROGRESS.getStatusName());
        if (newDelivery != null) {
            System.out.println("A delivery has already been created for that customer and date");
            return false;
        }
        newDelivery = delivery;

        // Verify if there are enough meals remaining in the user subscription before
        // creating a delivery
        Subscriptions subscriptions;
        subscriptions = subscriptionRepository.findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(
                customerId, supplierId, 1);

        if (!validateSubscription(subscriptions)) {
            return false;
        }

        // get the suppliers mealchart
        List<Object[]> mealChart = mealChartRepository.findMealChartBySupplierId(supplierId);
        if (mealChart.isEmpty()) {
            System.out.println("Meal chart not found");
            return false;
        }

        return addCustomerNotification(mealChart, tomorrowDate, supplierId, newDelivery, supplier, customerId);

        // return true;
    }

    public boolean addCustomerNotification(List<Object[]> mealChart, LocalDateTime tomorrowDate, int supplierId,
            Delivery newDelivery,
            Supplier supplier, int customerId) {
        StringBuilder deliveryMeal = new StringBuilder();
        String tomorrow = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(tomorrowDate);
        for (Object[] object : mealChart) {

            Date date = (Date) object[Constants.DATE_INDEX];
            String specialDate = date.toString();
            String day = (String) object[0];
            DayOfWeek tomorrowDay = tomorrowDate.getDayOfWeek();

            if (tomorrow.equals(specialDate)) { // special meal day, take most voted meal on the poll
                Polling polling = pollingRepository.findBySupIdAndStatus(supplierId, true);
                if (!validatePolling(polling)) {
                    return false;
                }
                deliveryMeal.append(polling.getVote());
                break;
            } else if (day.equals(tomorrowDay.toString().toLowerCase())) { // normal day, take the meal for that day
                for (int i = 1; i < Constants.MAX_MEAL_ITEM_SIZE; i++) {
                    String mealChartMeal = (String) object[i];
                    if (mealChartMeal == null || mealChartMeal.isEmpty()) {
                        continue;
                    }
                    deliveryMeal.append(mealChartMeal).append(",");
                }
                break;
            }

        }

        // Save the delivery
        newDelivery.setDeliveryMeal(deliveryMeal.toString());
        newDelivery.setDeliveryDate(tomorrowDate.toLocalDate());

        deliveryRepository.save(newDelivery);

        // Save notification to the user.
        CustomerNotification customerNotification;
        String notificationMessage;
        notificationMessage = "A delivery was created for: " + tomorrow + " by: " + supplier.getSupName();
        customerNotification = new CustomerNotification(null,
                notificationMessage, "delivery", customerId);

        custNotifiyRepo.save(customerNotification);
        return true;
    }

    public boolean validatePolling(Polling polling) {
        if (polling == null) {
            System.out.println("Poll not found.");
            return false;
        }
        if (polling.getVote() == null) {
            System.out.println("Voted meal not found.");
            return false;
        }
        return true;
    }

    public boolean validateSubscription(Subscriptions subscriptions) {
        if (subscriptions == null) {
            System.out.println("User has no active subscription with the supplier.");
            return false;
        }
        if (subscriptions.getMeals_remaining() == 0) {
            System.out.println("User has 0 meals remaining on the subscription.");
            return false;
        }
        return true;
    }
    /**
     * Retrieves a delivery by its ID.
     * @param id The ID of the delivery to retrieve.
     * @return The Delivery object if found, otherwise null.
     */
    @Override
    public Delivery getDeliveryById(int id) {
        return deliveryRepository.findById(id).orElse(null);
    }
    /**
     * Updates a delivery.
     * @param delivery The Delivery object to update.
     * @return The updated Delivery object if successful, otherwise null.
     */

    @Override
    public Delivery updateDelivery(@RequestBody Delivery delivery) {
        return deliveryRepository.findById(delivery.getDeliveryId()).map(
                currentDelivery -> {
                    currentDelivery.setDeliveryDate(delivery.getDeliveryDate());
                    currentDelivery.setDeliveryMeal(delivery.getDeliveryMeal());
                    currentDelivery.setOrderStatus(delivery.getOrderStatus());
                    currentDelivery.setSupId(delivery.getSupId());
                    currentDelivery.setCustId(delivery.getCustId());
                    return deliveryRepository.save(currentDelivery);
                }).orElse(null);
    }
    /**
     * Deletes a delivery by its ID.
     * @param id The ID of the delivery to delete.
     * @return A string indicating that the delivery was deleted.
     */
    @Override
    public String deleteDeliveryById(int id) {
        deliveryRepository.deleteById(id);
        return "Delivery deleted!";
    }
    /**
     * Retrieves a list of deliveries for a given customer ID.
     * @param id The ID of the customer.
     * @return A list of Delivery objects for the given customer ID.
     */
    @Override
    public List<Delivery> getByCustId(int id) {
        return deliveryRepository.findByCustId(id);
    }
    /**
     * Updates the existing delivery.
     * @param id The ID of the customer.
     * @param status status of the delivery
     * @return Updated delivery object.
     */

    @Transactional
    @Override
    public Delivery updateDeliveryStatus(int id, String status) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        Subscriptions subscription;
        subscription = subscriptionRepository.findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(
                delivery.getCustId(), delivery.getSupId(), 1);

        String newStatus = validateDeliveryAndSubscription(delivery, subscription, status);
        if (newStatus == null)
            return null;
        if (CANCELLED.getStatusName().equals(newStatus)) {
            delivery.setOrderStatus(CANCELLED.getStatusName());
        } else {
            // Update the remaining meals on the subscription table
            if (subscription.getMeals_remaining() == 0) {
                subscription.setActiveStatus(0);
            }
            if (subscription.getMeals_remaining() > 0) {
                subscription.setMeals_remaining(subscription.getMeals_remaining() - 1);
            }
            delivery.setOrderStatus(COMPLETED.getStatusName());
        }
        // Saving changes to the delivery and the new sub meal count
        deliveryRepository.save(delivery);
        subscriptionRepository.save(subscription);
        System.out.println("Order status successfully updated.");

        return delivery;
    }

    /**
     * Retrieves all deliveries that the supplier has initiated
     * @param delivery
     * @return List of delivery objects
     */
    public String validateDeliveryAndSubscription(Delivery delivery, Subscriptions subscription, String status) {
        if (delivery == null) {
            return null;
        }
        if (status == null || status.isEmpty()) {
            return null;
        }

        String newStatus = status.toLowerCase();

        if (!CANCELLED.getStatusName().equals(newStatus) &&
                !COMPLETED.getStatusName().equals(newStatus)) {
            System.out.println("Invalid status type.");
            return null;
        }

        if (!IN_PROGRESS.getStatusName().equals(delivery.getOrderStatus())) {
            System.out.println("Can't update the status of an order that it's not in progress.");
            return null;
        }

        if (subscription == null) {
            System.out.println("The user doesn't have an active subscription.");
            return null;
        }
        return newStatus;
    }

    @Override
    public List<Delivery> getBySupId(int id) {
        return deliveryRepository.findBySupId(id);
    }
}
