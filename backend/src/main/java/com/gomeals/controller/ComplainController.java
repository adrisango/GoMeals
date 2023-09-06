package com.gomeals.controller;

import com.gomeals.model.Complain;
import com.gomeals.service.ComplainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complain")

@CrossOrigin
/**
 * This Controller contains methods which handle Complain functions like
 * creation, deletion, updating, retrieval of single addon based on add-on ID
 * and retrieving all addons of a particular supplier using the supplier ID
 */
public class ComplainController {

    private final ComplainService complainService;

    public ComplainController(ComplainService complainService) {
        this.complainService = complainService;
    }

    /**
     * This method accepts a complain object stores it in the database
     * and returns the object which is stored in the database
     * 
     * @param complain object
     * @return complain object
     */
    @PostMapping("/create")
    public ResponseEntity<Complain> createComplain(@RequestBody Complain complain) {
        return new ResponseEntity<>(complainService.createComplain(complain), HttpStatus.CREATED);
    }

    /**
     * This method returns a single complaint details based on its ID
     * 
     * @param complainId complian ID
     * @return complain object
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Complain> getComplainById(@PathVariable("id") Integer complainId) {
        return new ResponseEntity<>(complainService.getComplainById(complainId), HttpStatus.OK);
    }

    /**
     * This method returns a list of all complaints
     * 
     * @return complain object
     */
    @GetMapping("/get/all")
    public ResponseEntity<List<Complain>> getAllComplains() {
        return new ResponseEntity<>(complainService.getAllComplains(), HttpStatus.OK);
    }

    /**
     * This method returns all complaints related to a customer based on customer ID
     * 
     * @param customerId ID
     * @return List of complaint objects
     */
    @GetMapping("/get/all-customer/{id}")
    public ResponseEntity<List<Complain>> getAllCustomerComplains(@PathVariable("id") Integer customerId) {
        return new ResponseEntity<>(complainService.getComplainsByCustomerId(customerId), HttpStatus.OK);
    }

    /**
     * This method returns all complaints related to a supplier based on supplier ID
     * 
     * @param supplierId ID
     * @return List of complaint objects
     */
    @GetMapping("/get/all-supplier/{id}")
    public ResponseEntity<List<Complain>> getAllSupplierComplains(@PathVariable("id") Integer supplierId) {
        return new ResponseEntity<>(complainService.getComplainsBySupplierId(supplierId), HttpStatus.OK);
    }

    /**
     * This method returns all complaints related to a supplier and a customer based
     * on supplier ID and customer ID
     * 
     * @param supplierId
     * @param customerId
     * @return List of complaint objects
     */
    @GetMapping("/all-supplier-customer")
    public ResponseEntity<List<Complain>> getAllComplainsByCustomerAndSupplier(@RequestParam Integer customerId,
            Integer supplierId) {
        return new ResponseEntity<>(complainService.getComplainsByCustomerIdAndSupplierId(customerId, supplierId),
                HttpStatus.OK);
    }

    /**
     * This method accepts a complain object and updates its entry in the database
     * and returns the object which is updated
     * 
     * @param complain
     * @return complain object
     */
    @PutMapping("/update")
    public ResponseEntity<Complain> updateComplain(@RequestBody Complain complain) {
        return new ResponseEntity<>(complainService.updateComplain(complain), HttpStatus.OK);
    }

    /**
     * This method accepts a complain ID and deletes its entry in the database
     * and returns the status in astring
     * 
     * @param complainId
     * @return status string
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComplain(@PathVariable("id") Integer complainId) {
        complainService.deleteComplain(complainId);
        return ResponseEntity.status(HttpStatus.OK).body("Complain was successfully deleted.\n");
    }
}
