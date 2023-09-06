package com.gomeals.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MealVotingId implements Serializable {

    @Column (name="polling_id")
    private Integer pollingId;

    @Column(name="customer_id")
    private Integer customerId;

    @Column (name="supplier_id")
    private Integer supplierId;

    public MealVotingId(){

    }
    public MealVotingId(Integer pollingId, Integer customerId, Integer supplierId){
        this.pollingId = pollingId;
        this.customerId = customerId;
        this.supplierId = supplierId;
    }

    public Integer getPollingId() {
        return pollingId;
    }

    public void setPollingId(Integer pollingId) {
        this.pollingId = pollingId;
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
