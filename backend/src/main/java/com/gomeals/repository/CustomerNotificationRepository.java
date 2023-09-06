package com.gomeals.repository;

import com.gomeals.model.CustomerNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerNotificationRepository extends CrudRepository<CustomerNotification, Integer> {

    List<CustomerNotification> findAllByCustomerId(Integer customerId);

}
