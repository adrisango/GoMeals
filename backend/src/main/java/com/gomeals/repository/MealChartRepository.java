package com.gomeals.repository;
import com.gomeals.model.MealChart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.gomeals.model.MealChartID;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealChartRepository extends CrudRepository<MealChart, MealChartID> {

    @Query(value = "SELECT * FROM meal_chart WHERE sup_id = :supplierId", nativeQuery = true)
    List<Object[]> findMealChartBySupplierId(@Param("supplierId") int supplierId);

}
