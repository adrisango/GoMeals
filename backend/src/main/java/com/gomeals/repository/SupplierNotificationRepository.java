package com.gomeals.repository;

import com.gomeals.model.SupplierNotification;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface SupplierNotificationRepository extends CrudRepository<SupplierNotification,Integer> {

    List<SupplierNotification> findAllBySupplierId(Integer supplierId);

}
