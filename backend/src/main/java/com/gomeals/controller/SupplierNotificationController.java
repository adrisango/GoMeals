package com.gomeals.controller;

import com.gomeals.model.SupplierNotification;
import com.gomeals.service.SupplierNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/supplier-notification")
/**
 * This controller contains all the functions required to manage the notifications that will be sent to the supplier
 */
@CrossOrigin
public class SupplierNotificationController {

    private final SupplierNotificationService supplierNotificationService;

    public SupplierNotificationController(SupplierNotificationService supplierNotificationService) {
        this.supplierNotificationService = supplierNotificationService;
    }

    /**
     * This method is used to store a new notification that will be showed to the supplier
     * This is useful to display notification to the supplier
     * @param notification
     * @return notification object that is stored
     */
    @PostMapping("/create")
    public ResponseEntity<SupplierNotification> createNotification(@RequestBody SupplierNotification notification) {
        return new ResponseEntity<>(supplierNotificationService.createNotification(notification), HttpStatus.CREATED);
    }
    /**
     * This method is used to retrieve a particular notification using a notification id
     * This is useful for showing a particular notification to the supplier
     * @param notificationId
     * @return notification object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<SupplierNotification> getNotificationById(@PathVariable("id") Integer notificationId) {
        return new ResponseEntity<>(supplierNotificationService.getNotificationById(notificationId), HttpStatus.OK);
    }
    /**
     * This method is used to retrieve all the notifications related to a particular supplier
     * This is useful to segregate which supplier has sent what notification
     * @param supplierId
     * @return List of notification objects
     */
    @CrossOrigin
    @GetMapping("/get/all-supplier/{id}")
    public ResponseEntity<List<SupplierNotification>> getAllNotificationsBySupplierId(
            @PathVariable("id") Integer supplierId) {
        return new ResponseEntity<>(supplierNotificationService.getAllNotificationsBySupplierId(supplierId),
                HttpStatus.OK);
    }
    /**
     * This method is used to update a particular notification using the notification ID
     * @param notification
     * @return Updated notification object
     */
    @PutMapping("/update")
    public ResponseEntity<SupplierNotification> updateNotification(@RequestBody SupplierNotification notification) {
        return new ResponseEntity<>(supplierNotificationService.updateNotification(notification), HttpStatus.OK);
    }

    /**
     * This method is used to notify all the customers subscribed to a particular supplier
     * @param notificationId
     * @return String regarding status of deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") Integer notificationId) {
        supplierNotificationService.deleteNotification(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body("Notification was successfully deleted.\n");
    }

}
