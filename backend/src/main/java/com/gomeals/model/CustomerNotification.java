package com.gomeals.model;

import jakarta.persistence.*;


@Entity
@Table(name = "customer_notifications")
public class CustomerNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;
    private String message;
    @Column (name = "event_type")
    private String eventType;
    @Column (name = "customer_id")
    private int customerId;

    public CustomerNotification(){

    }

    public CustomerNotification(Integer notificationId, String message, String eventType, int customerId){
        this.notificationId = notificationId;
        this.message = message;
        this.eventType = eventType;
        this.customerId = customerId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
