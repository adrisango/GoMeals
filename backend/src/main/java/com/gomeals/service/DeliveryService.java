package com.gomeals.service;

import com.gomeals.model.Delivery;

import java.util.List;

public interface DeliveryService {

    Boolean createDelivery(Delivery delivery);

    Delivery getDeliveryById(int id);

    Delivery updateDelivery(Delivery delivery);

    String deleteDeliveryById(int id);

    List<Delivery> getByCustId(int id);

    List<Delivery> getBySupId(int id);

    Delivery updateDeliveryStatus(int id, String status);

}
