package com.gomeals.repository;

import org.springframework.data.repository.CrudRepository;

import com.gomeals.model.Addons;

import java.util.List;

public interface AddonsRepository extends CrudRepository<Addons, Integer> {
    List<Addons> findAllBySupplierId(int supplierId);
}
