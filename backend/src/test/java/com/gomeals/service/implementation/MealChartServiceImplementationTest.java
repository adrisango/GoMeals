package com.gomeals.service.implementation;

import com.gomeals.model.MealChart;
import com.gomeals.model.MealChartID;
import com.gomeals.repository.MealChartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MealChartServiceImplementationTest {

    @Mock
    MealChartRepository mealChartRepository;

    @InjectMocks
    MealChartServiceImplementation mealChartServImpl;

    private MealChartID mealChartID;

    private MealChart mealChart;

    Date date;

    @BeforeEach
    public void setUp() {
        long millis = System.currentTimeMillis();
        date = new Date(millis);

        mealChartID = new MealChartID("Monday", 1);
        mealChart = new MealChart("meal1", "meal2", "meal3", "meal4", "meal5", date, mealChartID);
    }

    @Test
    public void testGetMealChart() {
        when(mealChartRepository.findById(mealChartID)).thenReturn(Optional.of(mealChart));

        MealChart getMealChart = mealChartServImpl.getMealChart(mealChartID);

        assertNotNull(getMealChart);
    }

    @Test
    public void testGetMealChartWhenIdNotFound() {
        when(mealChartRepository.findById(mealChartID)).thenReturn(Optional.empty());

        MealChart getMealChart = mealChartServImpl.getMealChart(mealChartID);

        assertNull(getMealChart);
    }

    @Test
    public void testAddMealChartSuccessfully() {
        List<MealChart> mealCharts = Arrays.asList(mealChart);

        when(mealChartRepository.saveAll(mealCharts)).thenReturn(mealCharts);

        String result = mealChartServImpl.addMealChart(mealCharts);

        assertEquals("Objects saved", result);
    }

    @Test
    public void testUpdateMealChart() {
        MealChart newMealChart = new MealChart("meal1", "meal2", "meal3", "meal4", "meal5", date, mealChartID);

        when(mealChartRepository.findById(mealChartID)).thenReturn(Optional.ofNullable(mealChart));

        String result = mealChartServImpl.updateMealChart(Arrays.asList(newMealChart));

        assertEquals("updated", result);
    }

    @Test
    public void testDeleteMealChartByIdSuccess() {
        Mockito.doNothing().when(mealChartRepository).deleteById(mealChartID);
        String result = mealChartServImpl.deleteMealChart(mealChartID);
        assertEquals("Meal Chart Deleted", result);
    }
}
