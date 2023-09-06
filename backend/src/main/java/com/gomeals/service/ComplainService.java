package com.gomeals.service;

import com.gomeals.model.Complain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ComplainService {

    // CREATE COMPLAIN
    Complain createComplain(Complain complain);

    // RETRIEVE COMPLAIN
    Complain getComplainById(Integer complainId);

    // RETRIEVE ALL COMPLAINS
    List<Complain> getAllComplains();

    // RETRIEVE ALL COMPLAINS MADE BY THAT USER ID
    List<Complain> getComplainsByCustomerId(Integer customerId);

    // RETRIEVE ALL COMPLAINS MADE TO THAT SUPPLIER ID
    List<Complain> getComplainsBySupplierId(Integer supplierId);

    // RETRIEVE ALL COMPLAINS MADE BY THE USER TO THAT SUPPLIER
    List<Complain> getComplainsByCustomerIdAndSupplierId(Integer customerId, Integer supplierId);

    // UPDATE COMPLAIN
    Complain updateComplain(Complain complain);

    // DELETE COMPLAIN
    void deleteComplain(Integer complainId);



}
