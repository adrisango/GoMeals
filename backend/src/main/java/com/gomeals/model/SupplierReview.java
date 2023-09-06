package com.gomeals.model;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier_review")
@IdClass(SupplierReviewID.class)
public class SupplierReview {

    @Id
    @Column(name = "cust_id")
    private Integer customerId;

    @Id
    @Column(name = "sup_id")
    private Integer supplierId;
    private String comment;

    private int supplier_rating;
    private String supplier_reviewcol;

    public SupplierReview() {

    }

    public SupplierReview(Integer customerId, Integer supplierId, String comment, int supplier_rating,
            String supplier_reviewcol) {
        this.customerId = customerId;
        this.supplierId = supplierId;
        this.comment = comment;
        this.supplier_rating = supplier_rating;
        this.supplier_reviewcol = supplier_reviewcol;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getSupplier_rating() {
        return supplier_rating;
    }

    public void setSupplier_rating(int supplier_rating) {
        this.supplier_rating = supplier_rating;
    }

    public String getSupplier_reviewcol() {
        return supplier_reviewcol;
    }

    public void setSupplier_reviewcol(String supplier_reviewcol) {
        this.supplier_reviewcol = supplier_reviewcol;
    }
}
