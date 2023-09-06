package com.gomeals.service;

import com.gomeals.model.SupplierReview;
import java.util.List;

public interface SupplierReviewService {

    SupplierReview createSupplierReview(SupplierReview supplierReview);

    List<Integer> get4upStarSupplier();

    List<Integer> get3UpStarSupplier();

    SupplierReview getSupplierReviewByCustomerIdAndSupplierId(int customerId, int supplierId);

}
