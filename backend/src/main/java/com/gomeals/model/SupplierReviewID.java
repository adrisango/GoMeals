package com.gomeals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class SupplierReviewID implements Serializable{
    @Column(name = "cust_id")
    private Integer customerId;

    @Column(name = "sup_id")
    private Integer supplierId;

    public SupplierReviewID(){

    }
    public SupplierReviewID(Integer customerId, Integer supplierId) {
        this.customerId = customerId;
        this.supplierId = supplierId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
