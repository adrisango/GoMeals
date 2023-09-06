package com.gomeals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class DeliveryAddonsId implements Serializable {
    @Column (name="cust_id")
    private Integer customerId;


    @Column (name="addon_id")
    private Integer addonId;


    @Column (name="delivery_id")
    private Integer deliveryId;


    public DeliveryAddonsId(){

    }
    public DeliveryAddonsId(Integer customerId,Integer addonId,Integer deliveryId){
        this.customerId=customerId;
        this.addonId=addonId;
        this.deliveryId=deliveryId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAddonId() {
        return addonId;
    }

    public void setAddonId(Integer addonId) {
        this.addonId = addonId;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }
}
