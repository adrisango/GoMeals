package com.gomeals.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private int deliveryId;
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    @Column(name = "delivery_meal")
    private String deliveryMeal;
    @Column(name = "order_status")
    private String orderStatus;
    @Column(name = "sup_id")
    private int supId;
    @Column(name = "cust_id")
    private int custId;

    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "sup_id")
    // private Supplier supplier;
    //
    // @ManyToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "cust_id")
    // private Customer customer;

    public Delivery() {
    }

    public Delivery(int deliveryId, LocalDate deliveryDate, String deliveryMeal, String orderStatus, int supId,
            int custId) {
        this.deliveryId = deliveryId;
        this.deliveryDate = deliveryDate;
        this.deliveryMeal = deliveryMeal;
        this.orderStatus = orderStatus;
        this.supId = supId;
        this.custId = custId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryMeal() {
        return deliveryMeal;
    }

    public void setDeliveryMeal(String deliveryMeal) {
        this.deliveryMeal = deliveryMeal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getSupId() {
        return supId;
    }

    public void setSupId(int supId) {
        this.supId = supId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

}
