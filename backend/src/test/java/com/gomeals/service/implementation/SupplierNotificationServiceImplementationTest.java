package com.gomeals.service.implementation;

import com.gomeals.model.SupplierNotification;
import com.gomeals.repository.SupplierNotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SupplierNotificationServiceImplementationTest {

        @Mock
        private SupplierNotificationRepository supNotifyRepo;

        @InjectMocks
        private SupplierNotificationServiceImplementation supNotifyServiceImpl;

        @Test
        void createNotification() {
                SupplierNotification supplierNotification = new SupplierNotification(1, "new user subscribed.",
                                "subscription", 5);

                SupplierNotification savedSupNotification = new SupplierNotification(1, "new user subscribed.",
                                "subscription", 5);

                when(supNotifyRepo.save((supplierNotification))).thenReturn(savedSupNotification);
                SupplierNotification newSupplierNotification = supNotifyServiceImpl
                                .createNotification(
                                                supplierNotification);

                assertEquals("subscription", newSupplierNotification.getEventType());
        }

        @Test
        void getNotificationById() {

                SupplierNotification supNotification = new SupplierNotification(1, "new user subscribed.",
                                "subscription", 5);
                when(supNotifyRepo.findById(1)).thenReturn(
                                Optional.of(supNotification));
                SupplierNotification supplierNotification = supNotifyServiceImpl
                                .getNotificationById(1);

                assertEquals(5, supplierNotification.getSupplierId());
        }

        @Test
        void getAllNotificationsBySupplierId() {

                SupplierNotification notification1 = new SupplierNotification(1, "new user subscribed.",
                                "subscription", 5);

                SupplierNotification notification2 = new SupplierNotification(2, "new complain raised on the portal.",
                                "complain", 5);

                SupplierNotification notification3 = new SupplierNotification(3, "new review added.",
                                "review", 5);
                when(supNotifyRepo.findAllBySupplierId(5)).thenReturn(Arrays.asList(
                                notification1,
                                notification2,
                                notification3));
                List<SupplierNotification> result = supNotifyServiceImpl.getAllNotificationsBySupplierId(5);

                assertEquals("review", result.get(2).getEventType());
                assertEquals("new user subscribed.", result.get(0).getMessage());
        }

        @Test
        void updateNotification() {

                SupplierNotification supplierNotification = new SupplierNotification(1,
                                "new user subscribed.", "subscription", 5);
                SupplierNotification newSupplierNotification;

                newSupplierNotification = new SupplierNotification(1,
                                "new user requested subscription", "subscription", 5);

                when(supNotifyRepo.findById(1)).thenReturn(Optional.of(supplierNotification));
                when(supNotifyRepo.save(supplierNotification)).thenReturn(newSupplierNotification);

                SupplierNotification updatedSupplierNotification = supNotifyServiceImpl
                                .updateNotification(supplierNotification);

                assertEquals("new user requested subscription", updatedSupplierNotification.getMessage());
        }

        @Test
        void deleteNotification() {
                SupplierNotification supplierNotification = new SupplierNotification(1,
                                "new user subscribed.", "subscription", 5);

                when(supNotifyRepo.findById(1)).thenReturn(Optional.of(supplierNotification));
                supNotifyServiceImpl.deleteNotification(1);
                verify(supNotifyRepo).deleteById(1);
        }
}