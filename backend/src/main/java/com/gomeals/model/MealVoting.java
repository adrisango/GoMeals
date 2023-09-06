package com.gomeals.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_voting")
@IdClass(MealVotingId.class)
public class MealVoting {
    @Id
    @Column(name="polling_id")
    private Integer pollingId;
    @Id
    @Column(name="customer_id")
    private Integer customerId;
    @Id
    @Column (name="supplier_id")
    private Integer supplierId;

    @Column (name="voted_meal")
    private String votedMeal;

    public MealVoting(){

    }
    public MealVoting(Integer pollingId, Integer customerId, Integer supplierId, String votedMeal){
        this.pollingId = pollingId;
        this.customerId = customerId;
        this.supplierId = supplierId;
        this.votedMeal = votedMeal;
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

    public String getVotedMeal() {
        return votedMeal;
    }

    public void setVotedMeal(String votedMeal) {
        this.votedMeal = votedMeal;
    }
}
