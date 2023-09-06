package com.gomeals.service.implementation;

import com.gomeals.model.MealChart;
import com.gomeals.service.MealChartService;
import com.gomeals.repository.MealChartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gomeals.model.MealChartID;
import java.util.List;
/**
 * Service implementation for handling MealChart operations.
 * Implements the MealChartService interface.
 */
@Service
public class MealChartServiceImplementation implements MealChartService {
    @Autowired
    MealChartRepository mealchartRepository;

    /**
     * Retrieves a MealChart by its ID.
     * @param id The ID of the MealChart to retrieve.
     * @return The MealChart object if found, otherwise null.
     */
    @Override
    public MealChart getMealChart(MealChartID id) {
        return mealchartRepository.findById(id).orElse(null);

    }

    /**
     * Adds a list of MealChart objects to the database.
     * @param mealChart The list of MealChart objects to add.
     * @return A string indicating that the objects were saved.
     */
    @Override
    public String addMealChart(List<MealChart> mealChart) {
        mealchartRepository.saveAll(mealChart);
        return "Objects saved";
    }
    /**
     * Updates a list of MealChart objects in the database.
     * @param mealCharts The list of MealChart objects to update.
     * @return A string indicating that the objects were updated.
     */
    @Override
    public String updateMealChart(List<MealChart> mealCharts) {
        for (MealChart mealChart : mealCharts) {
            MealChart m = mealchartRepository.findById(mealChart.getId()).orElse(null);
            saveMeals(mealChart, m);
        }
        return "updated";
    }


    public void saveMeals(MealChart mealChart, MealChart m) {
        if (m != null) {
            if (mealChart.getItem1() != "") {
                m.setItem1(mealChart.getItem1());
            }
            if (mealChart.getItem2() != "") {
                m.setItem2(mealChart.getItem2());
            }
            if (mealChart.getItem3() != "") {
                m.setItem3(mealChart.getItem3());
            }
            if (mealChart.getItem4() != "") {
                m.setItem4(mealChart.getItem4());
            }
            if (mealChart.getItem5() != "") {
                m.setItem5(mealChart.getItem5());
            }
            mealchartRepository.save(m);
        }
    }

    /**
     * Deletes a MealChart by its ID.
     * @param id The ID of the MealChart to delete.
     * @return A string indicating that the MealChart was deleted.
     */
    @Override
    public String deleteMealChart(MealChartID id) {
        mealchartRepository.deleteById(id);
        return "Meal Chart Deleted";
    }
}
