package com.gomeals.service;
import com.gomeals.model.MealChart;
import com.gomeals.model.MealChartID;
import java.util.List;


public interface MealChartService {
    public MealChart getMealChart(MealChartID id);

    public String addMealChart(List<MealChart> mealChart);

    public String updateMealChart(List<MealChart> mealChart);
    public String deleteMealChart(MealChartID id);
}
