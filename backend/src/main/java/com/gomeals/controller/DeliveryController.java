package com.gomeals.controller;

import com.gomeals.model.Delivery;
import com.gomeals.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@CrossOrigin

/**
 * This controller contains all the methods related to CRUD operation of a
 * Particular delivery
 */
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * This method is used To retrieve details regarding a particular delivery using
     * the delivery ID
     * 
     * @param id
     * @return Retrieved Delivery object
     */
    @CrossOrigin
    @GetMapping("/get/{id}")
    public Delivery getDeliveryById(@PathVariable("id") int id) {
        return deliveryService.getDeliveryById(id);
    }

    /**
     * This method is used To retrieve details of a delivery related to a particular
     * customer using the customer ID
     * 
     * @param id
     * @return Retrieved List of Delivery object
     */
    @CrossOrigin
    @GetMapping("/get/customer/{id}")
    public List<Delivery> getByCustomerId(@PathVariable int id) {
        return deliveryService.getByCustId(id);
    }

    /**
     * This method is used To retrieve details of a delivery related to a particular
     * supplier using the customer ID
     * 
     * @param id
     * @return Retrieved List of Delivery object
     */
    @CrossOrigin
    @GetMapping("/get/supplier/{id}")
    public List<Delivery> getBySupplierId(@PathVariable int id) {
        return deliveryService.getBySupId(id);
    }

    /**
     * This method is used to add an entry to the delivery table initiate a delivery
     * 
     * @param delivery
     * @return String indicating the status of delivery creation
     */
    @PostMapping("/create")
    public ResponseEntity<String> createDelivery(@RequestBody Delivery delivery) {
        Boolean createDelivery = deliveryService.createDelivery(delivery);
        if (createDelivery) {
            return ResponseEntity.status(HttpStatus.OK).body("Delivery created successfully.\n");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while creating delivery.\n");
        }
    }

    /**
     * This method is used to update an entry in the delivery table hence updating
     * the initiated delivery
     * 
     * @param delivery
     * @return Updated delivery object
     */
    @PutMapping("/update")
    public Delivery updateDelivery(@RequestBody Delivery delivery) {
        return deliveryService.updateDelivery(delivery);
    }

    /**
     * This method is used to update the status of a delivery
     * 
     * @param deliveryId
     * @param orderStatus
     * @return Delivery object with updated status
     */
    @PutMapping("/update-status/")
    public ResponseEntity<Delivery> updateDeliveryStatus(@RequestParam int deliveryId, String orderStatus) {
        Delivery deliveryToUpdate = deliveryService.updateDeliveryStatus(deliveryId, orderStatus);
        if (deliveryToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(deliveryToUpdate, HttpStatus.OK);
        }
    }

    /**
     * This method is used to delete a particular entry in the delivery table hence
     * deleting the initiated delivery
     * 
     * @param id
     * @return String indicating the status of the deletion
     */
    @DeleteMapping("/delete/{id}")
    public String deleteDeliveryById(@PathVariable int id) {
        return deliveryService.deleteDeliveryById(id);
    }

}
