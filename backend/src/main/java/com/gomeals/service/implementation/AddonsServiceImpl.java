package com.gomeals.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gomeals.model.Addons;
import com.gomeals.repository.AddonsRepository;
import com.gomeals.service.AddonsService;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
/**
 * Implementation of the AddonsService interface that provides CRUD operations for managing Addons.
 */
public class AddonsServiceImpl implements AddonsService {

    @Autowired
    AddonsRepository addonsRepository;
    /**
     * Retrieves an Addon by addonId.
     *
     * @param addonId The ID of the Addon to retrieve.
     * @return The retrieved Addon, or null if not found.
     */
    @Override
    @Transactional
    public Addons getAddon(int addonId) {
        return addonsRepository.findById(addonId).orElse(null);
    }
    /**
     * Creates a new Addon.
     *
     * @param addon The Addon object to create.
     * @return A string indicating the success of the operation.
     */
    @Override
    @Transactional
    public String createAddon(Addons addon) {
        addonsRepository.save(addon);
        return "Addon added successfully";
    }
    /**
     * Updates an existing Addon.
     *
     * @param addon The Addon object to update.
     * @return A string indicating the success of the operation.
     */
    @Override
    @Transactional
    public String updateAddon(Addons addon) {
        Addons latestAddon = addonsRepository.findById(addon.getAddonId()).orElse(null);
        latestAddon.setItem(addon.getItem());
        latestAddon.setPrice(addon.getPrice());
        addonsRepository.save(latestAddon);
        return "Addon Item updated successfully.";
    }
    /**
     * Deletes an Addon by addonId.
     *
     * @param addonId The ID of the Addon to delete.
     * @return A string indicating the success of the operation.
     */
    @Override
    @Transactional
    public String deleteAddon(int addonId) {
        addonsRepository.deleteById(addonId);
        return "Addon deleted successully";
    }
    /**
     * Retrieves all Addons associated with a supplier by supplierId.
     *
     * @param supplierId The ID of the supplier.
     * @return A list of Addons associated with the supplier.
     */
    @Override
    public List<Addons> getAllSupplierAddons(int supplierId) {
        List<Addons> supplierAddons = addonsRepository.findAllBySupplierId(supplierId);
        return supplierAddons;
    }

}
