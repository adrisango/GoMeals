package com.gomeals.service.implementation;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gomeals.model.SupplierReview;
import com.gomeals.repository.SupplierReviewRepository;
import com.gomeals.service.SupplierReviewService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SupplierReviewServiceImplTest {

    @Mock
    private SupplierReviewRepository supplierReviewRepository;

    @InjectMocks
    private SupplierReviewServiceImplementation supplierReviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createSupplierReviewTest() {
        SupplierReview supplierReview = new SupplierReview();
        supplierReview.setCustomerId(1);
        supplierReview.setSupplierId(1);
        supplierReview.setSupplier_rating(5);
        supplierReview.setComment("Great food and service!");

        when(supplierReviewRepository.save(supplierReview)).thenReturn(supplierReview);

        SupplierReview createdReview = supplierReviewService.createSupplierReview(supplierReview);

        Assertions.assertEquals(createdReview.getCustomerId(), supplierReview.getCustomerId());
        Assertions.assertEquals(createdReview.getSupplierId(), supplierReview.getSupplierId());
        Assertions.assertEquals(createdReview.getSupplier_rating(), supplierReview.getSupplier_rating());
        Assertions.assertEquals(createdReview.getSupplier_reviewcol(), supplierReview.getSupplier_reviewcol());

        verify(supplierReviewRepository, times(1)).save(supplierReview);
    }

    @Test
    public void get4upStarSupplierTest() {
        List<Integer> supplierIds = new ArrayList<>();
        supplierIds.add(1);
        supplierIds.add(2);
        supplierIds.add(3);

        when(supplierReviewRepository.find4supId()).thenReturn(supplierIds);

        List<Integer> returnedSupplierIds = supplierReviewService.get4upStarSupplier();

        Assertions.assertEquals(returnedSupplierIds.size(), supplierIds.size());
        Assertions.assertEquals(returnedSupplierIds.get(0), supplierIds.get(0));
        Assertions.assertEquals(returnedSupplierIds.get(1), supplierIds.get(1));
        Assertions.assertEquals(returnedSupplierIds.get(2), supplierIds.get(2));

        verify(supplierReviewRepository, times(1)).find4supId();
    }
    @Test
    public void get3UpStarSupplierTest() {
        List<Integer> supplierIds = new ArrayList<>();
        supplierIds.add(1);
        supplierIds.add(2);
        supplierIds.add(3);

        when(supplierReviewRepository.find3supId()).thenReturn(supplierIds);

        List<Integer> returnedSupplierIds = supplierReviewService.get3UpStarSupplier();

        Assertions.assertEquals(returnedSupplierIds.size(), supplierIds.size());
        Assertions.assertEquals(returnedSupplierIds.get(0), supplierIds.get(0));
        Assertions.assertEquals(returnedSupplierIds.get(1), supplierIds.get(1));
        Assertions.assertEquals(returnedSupplierIds.get(2), supplierIds.get(2));

        verify(supplierReviewRepository, times(1)).find3supId();
    }

    @Test
    public void getSupplierReviewByCustomerIdAndSupplierIdTest() {
        int customerId = 1;
        int supplierId = 1;
        SupplierReview supplierReview = new SupplierReview();
        supplierReview.setSupplierId(1);
        supplierReview.setCustomerId(customerId);
        supplierReview.setSupplierId(supplierId);
        supplierReview.setSupplier_rating(4);
        supplierReview.setComment("Good food and service.");

        when(supplierReviewRepository.findByCustomerIdAndSupplierId(customerId, supplierId)).thenReturn(supplierReview);

        SupplierReview returnedReview = supplierReviewService.getSupplierReviewByCustomerIdAndSupplierId(customerId, supplierId);

        Assertions.assertEquals(returnedReview.getSupplierId(), supplierReview.getSupplierId());
        Assertions.assertEquals(returnedReview.getCustomerId(), supplierReview.getCustomerId());
        Assertions.assertEquals(returnedReview.getSupplierId(), supplierReview.getSupplierId());
        Assertions.assertEquals(returnedReview.getSupplier_rating(), supplierReview.getSupplier_rating());
        Assertions.assertEquals(returnedReview.getComment(), supplierReview.getComment());

        verify(supplierReviewRepository, times(1)).findByCustomerIdAndSupplierId(customerId, supplierId);
    }

}
