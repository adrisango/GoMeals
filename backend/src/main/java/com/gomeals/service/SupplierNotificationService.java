package com.gomeals.service;

import com.gomeals.model.SupplierNotification;

import java.util.List;

public interface SupplierNotificationService {
    // CREATE notification
    SupplierNotification createNotification(SupplierNotification supplierNotification);
    // RETRIEVE notification
    SupplierNotification getNotificationById(Integer notificationId);
    // RETRIEVE all Notifications
    List<SupplierNotification> getAllNotificationsBySupplierId(Integer supplierId);
    // UPDATE notification
    SupplierNotification updateNotification(SupplierNotification supplierNotification);
    // DELETE notification
    void deleteNotification(Integer notificationId);
}
