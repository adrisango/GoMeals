package com.gomeals.controller;

import com.gomeals.model.DeliveryAddons;
import com.gomeals.model.DeliveryAddonsId;
import com.gomeals.service.DeliveryAddonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryAddons")
@CrossOrigin
/**
 * This controller has the functions handling the add-ons which will be added to
 * a delivery
 */
public class DeliveryAddonsController {
    @Autowired
    DeliveryAddonsService deliveryAddonsService;

    /**
     * This method is used to add an addon to an existing delivery
     * 
     * @param deliveryAddons
     * @return Created DeliveryAddons object
     */
    @PostMapping("/create")
    public ResponseEntity<DeliveryAddons> createDeliveryAddons(@RequestBody DeliveryAddons deliveryAddons) {
        return new ResponseEntity<>(deliveryAddonsService.createDeliveryAddons(deliveryAddons), HttpStatus.CREATED);
    }

    /**
     * This method is used to retrieve a particular Add-on object added to a
     * delivery using Delivery addon id
     * 
     * @param deliveryAddonsId
     * @return Retrived DeliveryAddons object
     */
    @PostMapping("/getDeliveryAddons")
    public ResponseEntity<DeliveryAddons> getDeliveryAddonsById(@RequestBody DeliveryAddonsId deliveryAddonsId) {
        return new ResponseEntity<>(deliveryAddonsService.getDeliveryAddonsById(deliveryAddonsId), HttpStatus.OK);
    }

    /**
     * This method is used to retrieve a particular Add-on object added to a
     * delivery using Delivery id
     * 
     * @param deliveryId
     * @return Retrived DeliveryAddon object
     */
    @CrossOrigin
    @GetMapping("/get/{id}")
    public ResponseEntity<List<DeliveryAddons>> getDeliveryAddonsByDeliveryId(@PathVariable("id") Integer deliveryId) {
        return new ResponseEntity<>(deliveryAddonsService.getDeliveryAddonsByDeliveryId(deliveryId), HttpStatus.OK);
    }

    /**
     * This method is used to update an Add-on which is already added to a delivery
     * 
     * @param deliveryAddon
     * @return Updated DeliiveryAddon object
     */
    @PutMapping("/update")
    public ResponseEntity<DeliveryAddons> updateDeliveryAddon(@RequestBody DeliveryAddons deliveryAddon) {
        return new ResponseEntity<>(deliveryAddonsService.updateDeliveryAddon(deliveryAddon), HttpStatus.OK);
    }

    /**
     * This method is used to delete an Add-on which is already added to a delivery
     * 
     * @param deliveryAddonId object
     * @return String indicating the status of the deletion
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDeliveryAddon(@RequestBody DeliveryAddonsId deliveryAddonId) {
        deliveryAddonsService.deleteDeliveryAddon(deliveryAddonId);
        return ResponseEntity.status(HttpStatus.OK).body("Delivery addon was successfully deleted.\n");
    }

    /**
     * This method is used to delete all the addons related to a delivery using the
     * delivery ID
     * 
     * @param deliveryId
     * @return String indicating the status of the deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAllByDeliveryId(@PathVariable("id") Integer deliveryId) {
        deliveryAddonsService.deleteAllByDeliveryId(deliveryId);
        return ResponseEntity.status(HttpStatus.OK).body("All delivery addons were successfully deleted.\n");
    }

}
