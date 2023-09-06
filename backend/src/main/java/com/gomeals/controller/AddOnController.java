package com.gomeals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gomeals.model.Addons;
import com.gomeals.service.AddonsService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/Addons")
/**
 * This Controller contains methods which handle Add-on functions like
 * creation, deletion, updating, retrieval of single addon based on add-on ID
 * and retrieving all addons of a particular supplier using the supplier ID
 */
public class AddOnController {

    @Autowired
    AddonsService addonsService;

    /**
     * This method returns a single add-on's details based on its ID
     * @param id Add-on ID
     * @return Addon object
     */
    @GetMapping("/get/{addonId}")
    public Addons getAddon(@PathVariable("addonId") int id) {
        return addonsService.getAddon(id);
    }

    /**
     * This method accepts an add-on object stores it in the database
     * and returns a string which updates its status
     * @param addon
     * @return Status String
     */
    @PostMapping("/create")
    public String createAddon(@RequestBody Addons addon) {
        return addonsService.createAddon(addon);
    }

    /**
     * This method accepts an add-on object and updates its entry in the database
     * and returns a string which updates its status
     * @param addon
     * @return status String
     */
    @PutMapping("/update")
    public String updateAddon(@RequestBody Addons addon) {

        return addonsService.updateAddon(addon);
    }

    /**
     * This method accepts an add-on ID and deletes its entry in the database
     * and returns a string which updates its status
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{addonId}")
    public String deleteAddon(@PathVariable("addonId") int id) {
        return addonsService.deleteAddon(id);
    }

    /**
     * This method returns all add-on's details of a supplier based on supplier ID
     * @param supplierId Add-on ID
     * @return Addon objects
     */
    @GetMapping("/get/all-supplier/{supplierId}")
    public ResponseEntity<List<Addons>> getAllSupplierAddons(@PathVariable("supplierId") int supplierId) {
        List<Addons> supplierAddons = addonsService.getAllSupplierAddons(supplierId);
        if (supplierAddons == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(supplierAddons, HttpStatus.OK);
    }

}
