package com.gomeals.repository;

import com.gomeals.model.DeliveryAddons;
import com.gomeals.model.DeliveryAddonsId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeliveryAddonsRepository extends CrudRepository<DeliveryAddons, DeliveryAddonsId> {
    List<DeliveryAddons> findDeliveryAddonsByDeliveryId(Integer deliveryId);
    void deleteAllByDeliveryId(Integer deliveryId);
}
