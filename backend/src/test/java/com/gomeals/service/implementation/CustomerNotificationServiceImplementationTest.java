package com.gomeals.service.implementation;

import com.gomeals.model.CustomerNotification;
import com.gomeals.model.Subscriptions;
import com.gomeals.repository.CustomerNotificationRepository;
import com.gomeals.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class CustomerNotificationServiceImplementationTest {

        @Mock
        private CustomerNotificationRepository customerNotificationRepository;

        @Mock
        private SubscriptionRepository subscriptionRepository;

        @InjectMocks
        private CustomerNotificationServiceImplementation customerNotificationServiceImplementation;

        @Test
        void createNotification() {
                CustomerNotification customerNotification = new CustomerNotification(1,"a delivery was created",
                        "delivery",5);
                when(customerNotificationRepository.save((customerNotification))).thenReturn(
                        new CustomerNotification(1,"a delivery was created",
                                "delivery",5)
                );
                CustomerNotification newCustomerNotification = customerNotificationServiceImplementation.createNotification(
                        customerNotification);

                assertEquals("delivery",newCustomerNotification.getEventType());
        }

        @Test
        void getNotificationById() {
                when(customerNotificationRepository.findById(1)).thenReturn(
                        Optional.of(new CustomerNotification(1,"a delivery was created",
                                "delivery",5))
                );
                CustomerNotification customerNotification = customerNotificationServiceImplementation
                        .getNotificationById(1);

                assertEquals(5,customerNotification.getCustomerId());
        }

        @Test
        void getAllNotificationsByCustomerId() {

                when(customerNotificationRepository.findAllByCustomerId(5)).thenReturn(Arrays.asList(
                        new CustomerNotification(1,"a delivery was created",
                                "delivery",5),
                        new CustomerNotification(2,"Eren has approved your subscription request.",
                                "subscription",5),
                        new CustomerNotification(3,"Jane Doe has provided feedback to your complain",
                                "complain solved",5)
                ));
                List<CustomerNotification> result = customerNotificationServiceImplementation.getAllNotificationsByCustomerId(5);

                assertEquals("delivery",result.get(0).getEventType());
                assertEquals("Eren has approved your subscription request.",result.get(1).getMessage());
        }

        @Test
        void updateNotification() {
                CustomerNotification customerNotification = new CustomerNotification(1,
                        "Jane Doe has provided feedback to your complain", "subscription",5);
                CustomerNotification newCustomerNotification = new CustomerNotification(1,
                        "John Doe has provided feedback to your complain", "subscription",5);

                when(customerNotificationRepository.findById(1)).thenReturn(Optional.of(customerNotification));
                when(customerNotificationRepository.save(customerNotification)).thenReturn(newCustomerNotification);

                CustomerNotification updatedCustomerNotification = customerNotificationServiceImplementation
                        .updateNotification(customerNotification);

                assertEquals("John Doe has provided feedback to your complain",updatedCustomerNotification.getMessage());
        }

        @Test
        public void testDeleteNotification() {
                // Arrange
                Integer notificationId = 1;
                String message = "msg";
                String type = "type";
                int customerId = 1;

                CustomerNotification customerNotification = new CustomerNotification(notificationId,message,type,customerId);

                when(customerNotificationRepository.findById(notificationId)).thenReturn(Optional.of(customerNotification));
                customerNotificationServiceImplementation.deleteNotification(notificationId);

                verify(customerNotificationRepository, times(1)).deleteById(notificationId);
        }
        @Test
        public void testNotifyAllSupplierCustomersWithSubscribedCustomers() {
                // Mocking the behavior of the subscriptionRepository
                long millis=System.currentTimeMillis();

                int sub_id = 1;
                int meals_remaining = 20;
                Date sub_date = new Date(millis);
                int activeStatus = 1;
                int cust_id = 1;
                int supplierId = 1;

                int sub_id2 = 2;
                int meals_remaining2 = 20;
                int cust_id2 = 2;
                int supplierId2 = 1;

                Subscriptions subscription1 = new Subscriptions(sub_id, meals_remaining, sub_date, activeStatus,
                        cust_id, supplierId);
                Subscriptions subscription2 = new Subscriptions(sub_id2, meals_remaining2, sub_date, activeStatus,
                        cust_id2, supplierId2);

                List<Subscriptions> supplierSubscriptions = new ArrayList<>();
                supplierSubscriptions.add(subscription1);
                supplierSubscriptions.add(subscription2);
                when(subscriptionRepository.findSubscriptionsBySupplierIdAndActiveStatus(supplierId, activeStatus))
                        .thenReturn(supplierSubscriptions);

                String message = "Test Message";
                String type = "Test Type";

                boolean result = customerNotificationServiceImplementation.notifyAllSupplierCustomers(message, type, supplierId);

                // Verify that the method returned true
                assertTrue(result);
        }

        @Test
        public void testNotifyAllSupplierCustomersWithNoSubscribedCustomers() {
                long millis=System.currentTimeMillis();

                int supplierId = 3;
                int activeStatus = 1;

                String message = "Test Message";
                String type = "Test Type";
                when(subscriptionRepository.findSubscriptionsBySupplierIdAndActiveStatus(supplierId, activeStatus))
                        .thenReturn(new ArrayList<Subscriptions>());

                boolean result = customerNotificationServiceImplementation.notifyAllSupplierCustomers(message, type, supplierId);

                // Verify that the subscriptionRepository was called with the correct arguments
                verify(subscriptionRepository).findSubscriptionsBySupplierIdAndActiveStatus(supplierId, activeStatus);

                // Verify that the method returned false
                assertFalse(result);
        }



}