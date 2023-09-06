package com.gomeals.model;

import jakarta.persistence.*;

@Entity
public class Addons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addon_id")
    private int addonId;

    private String item;

    private float price;

    @Column(name = "sup_id")
    private int supplierId;

    public int getAddonId() {
        return this.addonId;
    }

    public void setAddonId(int addonId) {
        this.addonId = addonId;
    }

    public String getItem() {
        return this.item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSupplierId() {
        return this.supplierId;
    }

    public void setSupplierId(int sup_id) {
        this.supplierId = sup_id;
    }

    public Addons(int addon_id, String item, float price, int supplierId) {
        this.addonId = addon_id;
        this.item = item;
        this.price = price;
        this.supplierId = supplierId;
    }

    public Addons() {
    }

}