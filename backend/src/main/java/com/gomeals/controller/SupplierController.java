package com.gomeals.controller;

import com.gomeals.model.Supplier;
import com.gomeals.service.SupplierService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
/**
 * This controller is used to Handle the operations related to the Supplier
 */
@CrossOrigin
public class SupplierController {
    @Autowired
    SupplierService supplierService;


    /**
     * This method is used to create the supplier profile
     * This is used in supplier registration
     * @param supplier
     * @return created supplier object
     */
    @CrossOrigin
    @PostMapping("/create")
    public Supplier registerSupplier(@RequestBody Supplier supplier) {
        return supplierService.registerSupplier(supplier);
    }

    /**
     * This method is used to get the supplier details
     * this is used while displaying the suppliers information to the customer
     * @param id
     * @return Retrieved supplier object
     */
    @GetMapping("/get/{id}")
    public Supplier getSupplierDetails(@PathVariable int id) {
        return supplierService.getSupplierDetails(id);
    }

    /**
     * This method is used to retrieve all suppliers info
     * This is useful in displaying various supplier profiles for customer to subscribe
     * @return List of supplier objects
     */
    @GetMapping("/get/all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return new ResponseEntity<>(supplierService.getAllSuppliers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public Supplier updateSupplier(@RequestBody Supplier supplier) {
        return supplierService.updateSupplier(supplier);
    }

    /**
     * This method is used to delete the supplier profoile
     * This is used to close supplier account
     * @param id
     * @return  String indicating the
     */
    @DeleteMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable int id) {
        return supplierService.deleteSupplier(id);
    }

    /**
     * This method is used to authenticate supplier and approve the login
     * This is useful for supplier login
     * @param supplier
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Supplier loginSupplier(@RequestBody Supplier supplier, HttpServletResponse response) {
        return supplierService.loginSupplier(supplier, response);
    }
}
