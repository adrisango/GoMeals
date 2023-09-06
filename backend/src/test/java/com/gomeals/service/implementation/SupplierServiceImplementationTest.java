
package com.gomeals.service.implementation;

import com.gomeals.model.Customer;
import com.gomeals.model.Subscriptions;
import com.gomeals.model.Supplier;
import com.gomeals.repository.CustomerRepository;
import com.gomeals.repository.SubscriptionRepository;
import com.gomeals.repository.SupplierRepository;
import com.gomeals.repository.SupplierReviewRepository;
import com.gomeals.utils.UserSecurity;

import jakarta.servlet.http.HttpServletResponse;

// import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SupplierServiceImplementationTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SupplierReviewRepository supplierReviewRepository;

    @Mock
    private UserSecurity userSecurity;

    @InjectMocks
    private SupplierServiceImplementation supplierService;

    private Supplier supplier;
    private List<Supplier> suppliers;
    private List<Customer> customers;
    private List<Subscriptions> subscriptions;
    private Customer customer;
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {

        supplier = new Supplier(1, "Halifax", "1234567890", "Jane Doe",
                "janedoe@gmail.com", "94738",
                "pass1234", 300.99, "paypal.me/jane.com");
        suppliers = new ArrayList<>(Arrays.asList(supplier));
        customer = new Customer(0, "John", "Bruce", "Wick", "jbw@gmail.com",
                "8264572839772", "8762264536", "pass123");
        customers = new ArrayList<>(Arrays.asList(customer));
        subscriptions = new ArrayList<>(Arrays.asList(new Subscriptions()));
        response = mock(HttpServletResponse.class);
    }

@Test
public void getSupplierDetails_ReturnsSupplierWithCustomersAndSubscriptions()
{
when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier));
when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
when(subscriptionRepository.findSubscriptionsBySupplierIdAndActiveStatus(1,
1)).thenReturn(subscriptions);
when(supplierReviewRepository.findSupplierAverage(1)).thenReturn(4.0);

Supplier result = supplierService.getSupplierDetails(1);

verify(supplierRepository, times(1)).findById(1);
verify(customerRepository, times(1)).findById(anyInt());
verify(subscriptionRepository,
times(1)).findSubscriptionsBySupplierIdAndActiveStatus(1, 1);
verify(supplierReviewRepository, times(1)).findSupplierAverage(1);

assertEquals(supplier, result);
assertEquals(subscriptions, result.getSubscriptions());
assertEquals(customers, result.getCustomers());
assertEquals(4.0, result.getSupplierRating(), 0.0);
}

@Test
public void getAllSuppliers_ReturnsSuppliersWithRatings() {
when(supplierRepository.findAll()).thenReturn(suppliers);
when(supplierReviewRepository.findSupplierAverage(1)).thenReturn(4.0);

List<Supplier> result = supplierService.getAllSuppliers();

verify(supplierRepository, times(1)).findAll();
verify(supplierReviewRepository, times(1)).findSupplierAverage(1);

assertEquals(suppliers, result);
assertEquals(4.0, result.get(0).getSupplierRating(), 0.0);
}

    @Test
    public void testGetAllSuppliers() {
        // mock data
        Supplier supplier1 = new Supplier(1, "Halifax", "9736372816", "Mayank Patel",
                "mpatel@gmail.com", null, null,
                null, null);
        Supplier supplier2 = new Supplier(2, "Dombivali", "7892725362", "Shreyas Nagaraja", "snagraja@gmail.com", null,
                null, null, null);
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(supplier1);
        supplierList.add(supplier2);

        // mock repository responses
        when(supplierRepository.findAll()).thenReturn(supplierList);
        when(supplierReviewRepository.findSupplierAverage(1)).thenReturn(4.5);
        when(supplierReviewRepository.findSupplierAverage(2)).thenReturn(3.8);

        // expected results
        List<Supplier> expectedSuppliers = new ArrayList<>();
        supplier1.setSupplierRating(4.5);
        supplier2.setSupplierRating(3.8);
        expectedSuppliers.add(supplier1);
        expectedSuppliers.add(supplier2);

        // call the service method
        List<Supplier> actualSuppliers = supplierService.getAllSuppliers();

        // assert the results
        assertEquals(expectedSuppliers, actualSuppliers);
    }

@Test
public void testGetAllSuppliersWithEmptyData() {
// mock repository responses
when(supplierRepository.findAll()).thenReturn(new ArrayList<>());

// expected results
List<Supplier> expectedSuppliers = new ArrayList<>();

// call the service method
List<Supplier> actualSuppliers = supplierService.getAllSuppliers();

// assert the results
assertEquals(expectedSuppliers, actualSuppliers);
}

@Test
public void testRegisterSupplier() {

// mock repository response
when(supplierRepository.findSupplierByEmail("janedoe@gmail.com")).thenReturn(null);

// mock user security response
when(userSecurity.encryptData("pass1234")).thenReturn("encrypted-password");

when(supplierRepository.save(supplier)).thenReturn(supplier);
// call the service method
Supplier actualSupplier = supplierService.registerSupplier(supplier);

// assert the results
assertNotNull(actualSupplier);
assertEquals("janedoe@gmail.com", actualSupplier.getSupEmail());
assertEquals("Jane Doe", actualSupplier.getSupName());
assertEquals("encrypted-password", actualSupplier.getPassword());
}

    @Test
    public void testRegisterSupplierWithEmailAlreadyExists() {
        // mock data
        Supplier supplier = new Supplier();
        supplier.setSupEmail("janedoe@gmail.com");
        supplier.setPassword("password");
        supplier.setSupName("Jane Doe");

        // mock repository response
        when(supplierRepository.findSupplierByEmail("janedoe@gmail.com")).thenReturn(supplier);

        // call the service method

        Assertions.assertThrows(RuntimeException.class, () -> {
            supplierService.registerSupplier(supplier);
        });
    }

    @Test
    public void testUpdateSupplierSuccess() {
        Supplier s = new Supplier();
        s.setSupId(1);
        s.setSupName("John Doe");
        s.setSupContactNumber("1234567890");
        s.setSupEmail("john.doe@example.com");
        s.setSupAddress("123 Main Street");
        s.setGovtIssuedId("ABC123");
        s.setPassword("password");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setSupId(s.getSupId());
        updatedSupplier.setSupName("Jane Doe");
        updatedSupplier.setSupContactNumber("0987654321");
        updatedSupplier.setSupEmail("jane.doe@example.com");
        updatedSupplier.setSupAddress("456 Oak Avenue");
        updatedSupplier.setGovtIssuedId("DEF456");
        updatedSupplier.setPassword("newpassword");

        when(supplierRepository.findById(s.getSupId())).thenReturn(Optional.of(s));
        when(supplierRepository.save(s)).thenReturn(updatedSupplier);

        Supplier result = supplierService.updateSupplier(updatedSupplier);

        verify(supplierRepository, times(1)).findById(s.getSupId());
        verify(supplierRepository, times(1)).save(s);

        Assertions.assertEquals(updatedSupplier, result);
    }

    @Test
    public void testUpdateSupplierNotFound() {
        Supplier s = new Supplier();
        s.setSupId(1);
        s.setSupName("John Doe");
        s.setSupContactNumber("1234567890");
        s.setSupEmail("john.doe@example.com");
        s.setSupAddress("123 Main Street");
        s.setGovtIssuedId("ABC123");
        s.setPassword("password");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setSupName("Jane Doe");
        updatedSupplier.setSupContactNumber("0987654321");
        updatedSupplier.setSupEmail("jane.doe@example.com");
        updatedSupplier.setSupAddress("456 Oak Avenue");
        updatedSupplier.setGovtIssuedId("DEF456");
        updatedSupplier.setPassword("newpassword");

        when(supplierRepository.findById(s.getSupId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            supplierService.updateSupplier(updatedSupplier);
        });

    }

    @Test
    public void testDeleteSupplier_success() {
        int id = 1;
        doNothing().when(supplierRepository).deleteById(id);

        String result = supplierService.deleteSupplier(id);

        assertEquals("Supplier deleted", result);
    }

    @Test
    public void testLoginSupplier_success() {
        Supplier supplier = new Supplier();
        supplier.setSupEmail("supplier@example.com");
        supplier.setPassword("password123");
        String encryptedPassword = "encrypted-password123";
        Supplier supplierData = new Supplier();
        supplierData.setSupId(1);
        supplierData.setSupEmail("supplier@example.com");
        supplierData.setPassword(encryptedPassword);

        when(userSecurity.encryptData("password123")).thenReturn("encrypted-password123");
        when(userSecurity.decryptData(encryptedPassword)).thenReturn("password123");
        when(supplierRepository.findSupplierByEmail(supplier.getSupEmail())).thenReturn(supplierData);
        when(supplierRepository.supplierPasswordMatch(supplier.getSupEmail())).thenReturn(encryptedPassword);

        Supplier result = supplierService.loginSupplier(supplier, response);

        assertNotNull(result);
        assertEquals(supplierData.getSupId(), result.getSupId());
        assertEquals(supplierData.getSupEmail(), result.getSupEmail());
        assertEquals(supplierData.getPassword(), result.getPassword());
    }

    @Test
    public void testLoginSupplier_incorrectPassword() {
        Supplier supplier = new Supplier();
        supplier.setSupEmail("supplier@example.com");
        supplier.setPassword("password123");

        String encryptedPassword = userSecurity.encryptData("wrongpassword");

        Supplier supplierData = new Supplier();
        supplierData.setSupId(1);
        supplierData.setSupEmail("supplier@example.com");
        supplierData.setPassword(encryptedPassword);

        when(supplierRepository.findSupplierByEmail(supplier.getSupEmail())).thenReturn(supplierData);
        when(supplierRepository.supplierPasswordMatch(supplier.getSupEmail())).thenReturn(encryptedPassword);

        Supplier result = supplierService.loginSupplier(supplier, response);

        assertNull(result);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_NOT_FOUND);
        verify(supplierRepository,
                times(1)).findSupplierByEmail(supplier.getSupEmail());
        verify(supplierRepository,
                times(1)).supplierPasswordMatch(supplier.getSupEmail());
    }

    @Test
    public void testLoginSupplier_supplierNotRegistered() {
        Supplier supplier = new Supplier();
        supplier.setSupEmail("supplier@example.com");
        supplier.setPassword("password123");

        when(supplierRepository.findSupplierByEmail(supplier.getSupEmail())).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () -> {
            supplierService.loginSupplier(supplier, response);
        });
    }

    @Test
    public void testLoginSupplier_emptyEmail() {
        Supplier supplier = new Supplier();
        supplier.setSupEmail("");
        supplier.setPassword("password123");

        Assertions.assertThrows(RuntimeException.class, () -> {
            supplierService.loginSupplier(supplier, response);
        });
    }

    @Test
    public void testLoginSupplier_emptyPassword() {
        Supplier supplier = new Supplier();
        supplier.setSupEmail("supplier@example.com");
        supplier.setPassword("");

        Assertions.assertThrows(RuntimeException.class, () -> {
            supplierService.loginSupplier(supplier, response);
        });
    }

}
