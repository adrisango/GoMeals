package com.gomeals.service;
import com.gomeals.model.Supplier;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


public interface SupplierService {

    public  Supplier loginSupplier(Supplier supplier, HttpServletResponse response);
    Supplier getSupplierDetails(int id);
    List<Supplier> getAllSuppliers();
    Supplier registerSupplier(Supplier supplier);
    Supplier updateSupplier(Supplier supplier);
    String deleteSupplier(int id);

}
