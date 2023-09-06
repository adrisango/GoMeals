package com.gomeals.controller;

import com.gomeals.model.CustomerNotification;
import com.gomeals.service.CustomerNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-notification")
@CrossOrigin
/**
 * This controller contains all the functions required to manage the
 * notifications that will be sent to the customer
 */

public class CustomerNotificationController {

    private final CustomerNotificationService customerNotificationService;

    public CustomerNotificationController(CustomerNotificationService customerNotificationService) {
        this.customerNotificationService = customerNotificationService;
    }

    /**
     * This method is used to store a new notification that will be showed to the
     * customer
     * 
     * @param notification
     * @return notification object that is stored
     */
    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<CustomerNotification> createNotification(@RequestBody CustomerNotification notification) {
        return new ResponseEntity<>(customerNotificationService.createNotification(notification), HttpStatus.CREATED);
    }

    /**
     * This method is used to retrieve a particular notification using a
     * notification id
     * 
     * @param notificationId
     * @return notification object
     */
    @CrossOrigin
    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerNotification> getNotificationById(@PathVariable("id") Integer notificationId) {
        return new ResponseEntity<>(customerNotificationService.getNotificationById(notificationId), HttpStatus.OK);
    }

    /**
     * This method is used to retrieve all the notifications related to a particular
     * customer
     * 
     * @param customerId
     * @return List of notification objects
     */
    @CrossOrigin
    @GetMapping("/get/all-customer/{id}")
    public ResponseEntity<List<CustomerNotification>> getAllNotificationsByCustomerId(
            @PathVariable("id") Integer customerId) {
        return new ResponseEntity<>(customerNotificationService.getAllNotificationsByCustomerId(customerId),
                HttpStatus.OK);
    }

    /**
     * This method is used to update a particular notification using the
     * notification ID
     * 
     * @param notification
     * @return Updated notification object
     */
    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<CustomerNotification> updateNotification(@RequestBody CustomerNotification notification) {
        return new ResponseEntity<>(customerNotificationService.updateNotification(notification), HttpStatus.OK);
    }

    /**
     * This method is used to delete a particular notification entry using th
     * notification ID
     * 
     * @param notificationId
     * @return String about the status of the deletion
     */
    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") Integer notificationId) {
        customerNotificationService.deleteNotification(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("Notification was successfully deleted.\n");
    }

    /**
     * This method is used to notify all the customers subscribed to a particular
     * supplier
     * 
     * @param message
     * @param type
     * @param supplierId
     * @return String status of notification
     */
    @CrossOrigin
    @PostMapping("/create-all/")
    public ResponseEntity<String> notifyAllSupplierCustomers(@RequestParam String message, String type,
            int supplierId) {

        Boolean notificationsCreated = customerNotificationService.notifyAllSupplierCustomers(message, type,
                supplierId);
        if (notificationsCreated) {
            return ResponseEntity.status(HttpStatus.OK).body("Notifications created for all customers.\n");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating notifications.\n");
        }
    }
}
