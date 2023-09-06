package com.gomeals.service.implementation;

import com.gomeals.model.DeliveryAddons;
import com.gomeals.model.DeliveryAddonsId;
import com.gomeals.repository.DeliveryAddonsRepository;
import com.gomeals.service.DeliveryAddonsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of the DeliveryAddonsService interface that provides
 * methods to perform CRUD (Create, Read, Update, Delete) operations on
 * DeliveryAddons objects.
 */
@Service
public class DeliveryAddonsServiceImplementation implements DeliveryAddonsService {

    private final DeliveryAddonsRepository deliveryAddonsRepository;

    public DeliveryAddonsServiceImplementation(DeliveryAddonsRepository deliveryAddonsRepository) {
        this.deliveryAddonsRepository = deliveryAddonsRepository;
    }
    /**
     * Create a new DeliveryAddons object.
     *
     * @param deliveryAddons The DeliveryAddons object to be created.
     * @return The created DeliveryAddons object.
     */
    @Override
    public DeliveryAddons createDeliveryAddons(DeliveryAddons deliveryAddons) {
        return deliveryAddonsRepository.save(deliveryAddons);
    }
    /**
     * Get a DeliveryAddons object by its ID.
     *
     * @param deliveryAddonsId The ID of the DeliveryAddons object.
     * @return The retrieved DeliveryAddons object or null if not found.
     */
    @Override
    public DeliveryAddons getDeliveryAddonsById(DeliveryAddonsId deliveryAddonsId) {
        return deliveryAddonsRepository.findById(deliveryAddonsId).orElse(null);
    }
    /**
     * Get a list of DeliveryAddons objects by delivery ID.
     *
     * @param deliveryId The ID of the delivery.
     * @return The list of DeliveryAddons objects associated with the delivery.
     */
    @Override
    public List<DeliveryAddons> getDeliveryAddonsByDeliveryId(Integer deliveryId) {
        List<DeliveryAddons> deliveryAddons;
        deliveryAddons = deliveryAddonsRepository.findDeliveryAddonsByDeliveryId(deliveryId);
        return deliveryAddons;
    }
    /**
     * Update a DeliveryAddons object.
     *
     * @param deliveryAddons The DeliveryAddons object with updated information.
     * @return The updated DeliveryAddons object.
     */
    @Override
    @Transactional
    public DeliveryAddons updateDeliveryAddon(DeliveryAddons deliveryAddons) {
        return deliveryAddonsRepository.save(deliveryAddons);
    }
    /**
     * Delete a DeliveryAddons object by its ID.
     *
     * @param deliveryAddonId The ID of the DeliveryAddons object to be deleted.
     */
    @Override
    @Transactional
    public void deleteDeliveryAddon(DeliveryAddonsId deliveryAddonId) {
        deliveryAddonsRepository.deleteById(deliveryAddonId);
    }
    /**
     * Delete all DeliveryAddons objects associated with a delivery by delivery ID.
     *
     * @param deliveryId The ID of the delivery.
     */
    @Override
    @Transactional
    public void deleteAllByDeliveryId(Integer deliveryId) {
        deliveryAddonsRepository.deleteAllByDeliveryId(deliveryId);
    }
}
