package com.gomeals.controller;

import com.gomeals.model.MealChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.gomeals.service.MealChartService;
import com.gomeals.model.MealChartID;

import java.util.List;

/**
 * This controller contains the methods that is used to handle the meal chart of
 * a
 * particular supplier
 */
@RestController
@RequestMapping("/meal_chart")
@CrossOrigin
public class MealChartController {
    @Autowired
    MealChartService mealChartService;

    /**
     * This method is used to extract the details of a meal chart using its ID
     * 
     * @param id
     * @return Meal chart object
     */
    @PostMapping("/get")
    public MealChart getMealChart(@RequestBody MealChartID id) {
        return mealChartService.getMealChart(id);
    }

    /**
     * This method is used to create a new meal chart for a particular supplier
     * 
     * @param mealChart
     * @return String indicating the status of the meal chart creation
     */
    @PostMapping("/create")
    public String addMealChart(@RequestBody List<MealChart> mealChart) {
        return mealChartService.addMealChart(mealChart);
    }

    /**
     * This method is used to update the existing meal chart of a particular
     * supplier
     * 
     * @param mealChart
     * @return String indicating the status of the updation
     */
    @PutMapping("/update")
    public String updateMealChartList(@RequestBody List<MealChart> mealChart) {
        return mealChartService.updateMealChart(mealChart);
    }

    /**
     * This method is used to delete the particular entries in meal chart table
     * hence deleting the meal chart
     * 
     * @param id
     * @return String indicating the status of the deletion
     */
    @DeleteMapping("/delete")
    public String deleteMealChart(@RequestBody MealChartID id) {
        return mealChartService.deleteMealChart(id);
    }

}
