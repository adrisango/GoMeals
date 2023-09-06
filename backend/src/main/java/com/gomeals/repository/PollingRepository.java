package com.gomeals.repository;

import com.gomeals.model.Polling;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PollingRepository extends CrudRepository<Polling, Integer> {

    @Query("SELECT p FROM Polling p WHERE p.supId IN (:supId) and p.status=1")
    List<Polling> getActivePollForSupplier(@Param("supId") int[] supId);

    @Query("SELECT p FROM Polling p WHERE p.supId =:supId ORDER BY p.pollDate desc")
    List<Polling> getAllPollsForSupplier(@Param("supId") int supId);

    Polling findBySupIdAndStatus(int supplierId, boolean status);
}
