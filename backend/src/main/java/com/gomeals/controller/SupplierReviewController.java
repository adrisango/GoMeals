package com.gomeals.controller;

import com.gomeals.model.SupplierReview;
import com.gomeals.service.SupplierReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplierReview")
@CrossOrigin
public class SupplierReviewController {
    @Autowired
    SupplierReviewService supplierReviewService;

    /**
     * This method is used to create a supplier review
     * This is used when customer raises a complaint
     * @param supplierReview
     * @return created supplier review object
     */
    @PostMapping("/create")
    public SupplierReview createSupplierReview(@RequestBody SupplierReview supplierReview) {
        return supplierReviewService.createSupplierReview(supplierReview);
    }

    /**
     * This is used to extract a particular complaint using supplier id and customer id
     * This is used when supplier sees all the current complaints
     * @param customerId
     * @param supplierId
     * @return Retrieved supplier review object
     */
    @GetMapping("/getById")
    public SupplierReview getByCustomerIdAndSupplierId(@RequestParam int customerId, int supplierId) {
        return supplierReviewService.getSupplierReviewByCustomerIdAndSupplierId(customerId, supplierId);
    }

    /**
     * This is used to extract all the supplier details whose average review is greate than or equals to 4
     * This is used when the customer filters out the suppliers
     * @return Retrieved lis of suppliers
     */
    @GetMapping("/get/4us")
    public List<Integer> get4upStarSupplier() {
        return supplierReviewService.get4upStarSupplier();
    }
    /**
     * This is used to extract all the supplier details whose average review is greate than or equals to 3
     * This is used when the customer filters out the suppliers
     * @return Retrieved lis of suppliers
     */
    @GetMapping("/get/3us")
    public List<Integer> get3UpStarSupplier() {
        return supplierReviewService.get3UpStarSupplier();
    }
}
