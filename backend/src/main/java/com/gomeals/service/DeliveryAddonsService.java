package com.gomeals.service;

import com.gomeals.model.DeliveryAddons;
import com.gomeals.model.DeliveryAddonsId;


import java.util.List;

public interface DeliveryAddonsService {

    // CREATE DELIVERY ADDONS
    DeliveryAddons createDeliveryAddons(DeliveryAddons deliveryAddons);

    // RETRIEVE DELIVERY ADDONS
    DeliveryAddons getDeliveryAddonsById(DeliveryAddonsId deliveryAddonsId);

    // RETRIEVE DELIVERY ADDONS BY DELIVERY ID
    List<DeliveryAddons> getDeliveryAddonsByDeliveryId(Integer deliveryId);

    // UPDATE DELIVERY ADDON
    DeliveryAddons updateDeliveryAddon(DeliveryAddons deliveryAddons);

    // DELETE DELIVERY ADDON
    void deleteDeliveryAddon(DeliveryAddonsId deliveryAddonId);

    // DELETE ALL DELIVERY ADDONS FROM THAT DELIVERY
    void deleteAllByDeliveryId(Integer deliveryId);


}
