package com.gomeals.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gomeals.model.Subscriptions;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscriptions, Integer> {
        List<Subscriptions> findSubscriptionsBySupplierIdAndActiveStatus(Integer supplierId, Integer activeStatus);

        List<Subscriptions> findSubscriptionsByCustomerIdAndActiveStatus(Integer customerId, Integer activeStatus);

        Subscriptions findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(Integer customerId, Integer supplierId,
                        Integer activeStatus);

        List<Subscriptions> findByActiveStatusAndStatusAndSupplierId(Integer activeStatus, String status,
                        Integer supplierId);

        @Query("SELECT s.customerId FROM Subscriptions s WHERE s.supplierId=:id AND s.activeStatus=1")
        List<Integer> getCustomersIdForSupplier(@Param("id") int supId);

}
