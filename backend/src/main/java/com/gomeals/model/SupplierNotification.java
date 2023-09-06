package com.gomeals.model;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier_notifications")
public class SupplierNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;
    private String message;
    @Column (name = "event_type")
    private String eventType;
    @Column (name = "supplier_id")
    private int supplierId;

    public SupplierNotification(){

    }

    public SupplierNotification(Integer notificationId, String message, String eventType, int supplierId){
        this.notificationId = notificationId;
        this.message = message;
        this.eventType = eventType;
        this.supplierId = supplierId;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int customerId) {
        this.supplierId = customerId;
    }
}
