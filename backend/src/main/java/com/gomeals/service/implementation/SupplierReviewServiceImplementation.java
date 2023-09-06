package com.gomeals.service.implementation;

import com.gomeals.model.SupplierReview;
import com.gomeals.repository.SupplierReviewRepository;
import com.gomeals.service.SupplierReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of the SupplierReviewService interface.
 */
@Service
public class SupplierReviewServiceImplementation implements SupplierReviewService {

    @Autowired
    SupplierReviewRepository supplierReviewRepository;

    /**
     * Creates a new SupplierReview.
     *
     * @param supplierReview The SupplierReview object to be created.
     * @return The created SupplierReview object.
     */
    @Override
    public SupplierReview createSupplierReview(SupplierReview supplierReview) {return supplierReviewRepository.save(supplierReview);}

    /**
     * Retrieves a list of Supplier IDs for Suppliers with 4 or more stars in their reviews.
     *
     * @return A list of Supplier IDs.
     */
    @Override
    public List<Integer> get4upStarSupplier(){return supplierReviewRepository.find4supId();}

    /**
     * Retrieves a list of Supplier IDs for Suppliers with 3 or more stars in their reviews.
     *
     * @return A list of Supplier IDs.
     */
    @Override
    public List<Integer> get3UpStarSupplier() {
        return supplierReviewRepository.find3supId();
    }

    /**
     * Retrieves a SupplierReview by Customer ID and Supplier ID.
     *
     * @param customerId The ID of the Customer.
     * @param supplierId The ID of the Supplier.
     * @return The SupplierReview object.
     */
    @Override
    public SupplierReview getSupplierReviewByCustomerIdAndSupplierId(int customerId, int supplierId) {
        return supplierReviewRepository.findByCustomerIdAndSupplierId(customerId, supplierId);
    }

}
