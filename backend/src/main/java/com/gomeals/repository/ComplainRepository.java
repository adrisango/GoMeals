package com.gomeals.repository;

import com.gomeals.model.Complain;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComplainRepository extends CrudRepository<Complain, Integer> {
    List<Complain> findComplainsByCustomerId(Integer customerId);
    List<Complain> findComplainsBySupplierId(Integer supplierId);

    List<Complain> findComplainsByCustomerIdAndSupplierId(Integer customerId, Integer supplierId);


}
