package com.gomeals.model;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_addons")
@IdClass(DeliveryAddonsId.class)
public class DeliveryAddons {
    @Id
    @Column (name="cust_id")
    private Integer customerId;
    @Id
    @Column (name="addon_id")
    private Integer addonId;
    @Id
    @Column (name="delivery_id")
    private Integer deliveryId;
    private Integer quantity;

    public DeliveryAddons(){
        quantity = 0;
    }
    public DeliveryAddons(Integer cust_id, Integer addon_id, Integer delivery_id,Integer quantity){
        this.customerId = cust_id;
        this.addonId = addon_id;
        this.deliveryId=delivery_id;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

