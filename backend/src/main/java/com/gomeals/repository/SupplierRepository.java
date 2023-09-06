package com.gomeals.repository;

import com.gomeals.model.Supplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
    @Query("SELECT s FROM Supplier s WHERE s.supEmail = :email")
    Supplier findSupplierByEmail(@Param("email") String email);

    @Query("SELECT s.password from Supplier s WHERE s.supEmail= :email")
    String supplierPasswordMatch(@Param("email") String email);

}
