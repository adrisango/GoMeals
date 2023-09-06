package com.gomeals.service.implementation;

import com.gomeals.constants.DeliveryStatus;
import com.gomeals.model.*;
import com.gomeals.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.gomeals.constants.DeliveryStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeliveryServiceImplementationTest {

    @Mock
    DeliveryRepository deliveryRepository;

    @Mock
    SubscriptionRepository subscriptionRepository;

    @Mock
    SupplierRepository supplierRepository;

    @Mock
    MealChartRepository mealChartRepository;

    @Mock
    PollingRepository pollingRepository;

    @Mock
    CustomerNotificationRepository customerNotificationRepository;

    @InjectMocks
    DeliveryServiceImplementation deliveryServiceImplementation;
    long millis=System.currentTimeMillis();
    Date date = new Date(millis);

    @Test
    public void testCreateDelivery() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 12);
        List<Object[]> mockMealChart = new ArrayList<>();
        Object[] meal1 = new Object[]{"Monday", 1, "meal1", "meal2", "meal3", "meal4", "meal5", date};
        Object[] meal2 = new Object[]{"Tuesday", 2, "meal1", "meal2", "meal3", "meal4", "meal5", date};
        mockMealChart.add(meal1);
        mockMealChart.add(meal2);
        Subscriptions subscription = new Subscriptions();
        subscription.setCustomerId(1);
        subscription.setSupplierId(1);
        subscription.setActiveStatus(1);
        subscription.setMeals_remaining(5);
        CustomerNotification customerNotification = new CustomerNotification(null, "", "", 1);
//        mealChartList.add( new MealChart("meal1", "meal2", "meal3", "meal4", "meal5", date, new MealChartID("Monday", 29)));

        when(deliveryRepository.save(delivery)).thenReturn(delivery);
        when(supplierRepository.findById(29)).thenReturn(Optional.of(new Supplier(29, "123 Main St", "555-555-5555", "ABC Catering", "abc@example.com", "12345", "password", 10.00, "https://www.paypal.com/abc")));
        when(deliveryRepository.findBySupIdAndCustIdAndDeliveryDateAndOrderStatus(29, 12, date.toLocalDate().plusDays(1), IN_PROGRESS.getStatusName())).thenReturn(null);
        when(subscriptionRepository.findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(12, 29, 1)).thenReturn(subscription);
        when(mealChartRepository.findMealChartBySupplierId(29)).thenReturn(mockMealChart);
        when(pollingRepository.findBySupIdAndStatus(1, true)).thenReturn(new Polling(1, date, "1", "meal1", "meal2", "meal3", "meal4", "meal5", false, 29));
        when(customerNotificationRepository.save(customerNotification)).thenReturn(customerNotification);

        Boolean result = deliveryServiceImplementation.createDelivery(delivery);

        assertTrue(result);

    }

    @Test
    void testGetDeliveryById() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 12);

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));

        Delivery testDelivery = deliveryServiceImplementation.getDeliveryById(1);

        assertNotNull(testDelivery);
    }

    @Test
    void testGetDeliveryByIdNotFound() {
        when(deliveryRepository.findById(1)).thenReturn(Optional.empty());

        Delivery testDelivery = deliveryServiceImplementation.getDeliveryById(1);

        assertNull(testDelivery);
    }

    @Test
    public void testUpdateDeliveryWhenDeliveryDoesNotExist() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 12);

        when(deliveryRepository.findById(2)).thenReturn(null);

        Delivery updateddelivery = deliveryServiceImplementation.updateDelivery(delivery);

        assertNull(updateddelivery);
    }

    @Test
    public void testUpdateDeliveryWhenDeliveryExists() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 12);
        Delivery newDelivery = new Delivery(1, date.toLocalDate().plusDays(1), "Bread", "Inprogress", 29, 12);

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(newDelivery));
        when(deliveryRepository.save(newDelivery)).thenReturn(newDelivery);

        Delivery updatedDelivery = deliveryServiceImplementation.updateDelivery(delivery);

        assertNotNull(updatedDelivery);
        assertEquals(updatedDelivery.getDeliveryId(), delivery.getDeliveryId());
    }

    @Test
    public void testUpdateDeliveryWhenDeliveryIdsDoNotMatch() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 12);
        Delivery newDelivery = new Delivery(2, date.toLocalDate().plusDays(1), "Bread", "Inprogress", 29, 12);

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));

        Delivery updatedDelivery = deliveryServiceImplementation.updateDelivery(newDelivery);

        assertNull(updatedDelivery);
    }

    @Test
    public void testDeleteDeliveryByIdSuccess() {
        Mockito.doNothing().when(subscriptionRepository).deleteById(1);
        String result = deliveryServiceImplementation.deleteDeliveryById(1);
        assertEquals("Delivery deleted!", result);
    }

    @Test
    public void testGetByCustId() {
        int customerId = 1;
        Delivery delivery1 = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", "Inprogress", 29, 1);
        Delivery delivery2 = new Delivery(1, date.toLocalDate().plusDays(1), "Bread", "Inprogress", 29, 1);
        List<Delivery> expectedDeliveries = Arrays.asList(delivery1, delivery2);

        when(deliveryRepository.findByCustId(1)).thenReturn(expectedDeliveries);

        List<Delivery> customerDeliveries = deliveryServiceImplementation.getByCustId(1);

        assertEquals(customerDeliveries, expectedDeliveries);
    }

    @Test
    public void testGetByCustIdWithNoDeliveries() {
        int customerId = 123;

        when(deliveryRepository.findByCustId(customerId)).thenReturn(Collections.emptyList());

        List<Delivery> actualDeliveries = deliveryServiceImplementation.getByCustId(customerId);

        assertTrue(actualDeliveries.isEmpty());
    }

    @Test
    public void testUpdateDeliveryStatusToCancelled() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", IN_PROGRESS.getStatusName(), 29, 12);
        Subscriptions subscriptions = new Subscriptions(1, 10, date, 1, 1 ,1);

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
        when(subscriptionRepository.findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(12, 29, 1)).thenReturn(subscriptions);
        when(subscriptionRepository.save(subscriptions)).thenReturn(subscriptions);

        Delivery updatedDelivery = deliveryServiceImplementation.updateDeliveryStatus(1, "cancelled");

        assertEquals(CANCELLED.getStatusName(), updatedDelivery.getOrderStatus());
    }

    @Test
    public void testUpdateDeliveryStatusToCompleted() {
        Delivery delivery = new Delivery(1, date.toLocalDate().plusDays(1), "Rice", IN_PROGRESS.getStatusName(), 1, 1);
        Subscriptions subscriptions = new Subscriptions(1, 10, date, 1, 1 ,1);

        when(deliveryRepository.findById(1)).thenReturn(Optional.of(delivery));
        when(subscriptionRepository.findSubscriptionsByCustomerIdAndSupplierIdAndActiveStatus(1, 1, 1)).thenReturn(subscriptions);
        when(subscriptionRepository.save(subscriptions)).thenReturn(subscriptions);

        Delivery updatedDelivery = deliveryServiceImplementation.updateDeliveryStatus(1, "completed");

        assertEquals(COMPLETED.getStatusName(), updatedDelivery.getOrderStatus());

        assertEquals(9, subscriptions.getMeals_remaining());
    }

    @Test
    public void testGetBySupId() {
        // Given
        int id = 123;
        List<Delivery> expectedDeliveries = new ArrayList<>();
        Delivery delivery1 = new Delivery();
        delivery1.setDeliveryId(1);
        delivery1.setSupId(id);
        Delivery delivery2 = new Delivery();
        delivery2.setDeliveryId(2);
        delivery2.setSupId(id);
        expectedDeliveries.add(delivery1);
        expectedDeliveries.add(delivery2);
        when(deliveryRepository.findBySupId(id)).thenReturn(expectedDeliveries);

        List<Delivery> actualDeliveries = deliveryServiceImplementation.getBySupId(id);

        assertEquals(expectedDeliveries.size(), actualDeliveries.size());
    }



}
