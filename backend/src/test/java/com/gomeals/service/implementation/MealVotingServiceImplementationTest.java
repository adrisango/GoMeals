package com.gomeals.service.implementation;

import com.gomeals.model.MealVoting;
import com.gomeals.model.Polling;
import com.gomeals.repository.MealVotingRepository;
import com.gomeals.repository.PollingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MealVotingServiceImplementationTest {

    @Mock
    private MealVotingRepository mealVotingRepository;

    @InjectMocks
    private MealVotingServiceImplementation mealVotingServiceImplementation;

    @Mock
    private PollingRepository pollingRepository;

    @Test
    void createMealVoting() {
        MealVoting mealVoting = new MealVoting(1,1,1,"Pizza");
        when(mealVotingRepository.save((mealVoting))).thenReturn(
                new MealVoting(1,1,1,"Pizza")
        );
        MealVoting newMealVoting = mealVotingServiceImplementation.createMealVoting(
                mealVoting);

        assertEquals("Pizza",newMealVoting.getVotedMeal());
    }

    @Test
    void getMealVotingByPollingId() {
        MealVoting mealVoting = new MealVoting(1,1,1,"Pizza");
        when(mealVotingRepository.findByPollingId(1)).thenReturn(mealVoting);

        MealVoting storedMealVoting = mealVotingServiceImplementation.getMealVotingByPollingId(1);

        assertEquals("Pizza", storedMealVoting.getVotedMeal());
    }

    @Test
    void getNonExistingPollingById(){
        MealVoting mealVoting = new MealVoting(1,1,1,"Pizza");
        when(mealVotingRepository.findByPollingId(1)).thenReturn(mealVoting);

        MealVoting nonExistingMealVoting = mealVotingServiceImplementation.getMealVotingByPollingId(2);

        assertNull(nonExistingMealVoting);
    }

    @Test
    void getMealVotingForCustomerByPollId() {
        MealVoting mealVoting = new MealVoting(1,1,1,"Pizza");
        when(mealVotingRepository.getMealVotingForCustomerByPollId(1, 1)).thenReturn(mealVoting);

        MealVoting storedMealVoting = mealVotingServiceImplementation.getMealVotingForCustomerByPollId(1, 1);

        assertEquals("Pizza", storedMealVoting.getVotedMeal());
        verify(mealVotingRepository).getMealVotingForCustomerByPollId(1, 1);
    }

    @Test
    void getNonExistingMealVotingForCustomerByPollId() {
        MealVoting mealVoting = new MealVoting(1,1,1,"Pizza");

        when(mealVotingRepository.getMealVotingForCustomerByPollId(1, 1)).thenReturn(mealVoting);

        MealVoting nonExistingMealVoting = mealVotingServiceImplementation.getMealVotingForCustomerByPollId(2, 1);
        assertNull(nonExistingMealVoting);
    }

    @Test
    void findMostVotedMeal() {
        int supplierId = 1;
        int pollingId = 1;

        String votedMeal = "Pizza";
        List<Object[]> mostVotedMeals = new ArrayList<>();
        Object[] mostVotedMeal = new Object[]{"Pizza", 5L};

        mostVotedMeals.add(mostVotedMeal);
        when(mealVotingRepository.countMealVotes(pollingId, supplierId)).thenReturn(mostVotedMeals);
        Polling polling = new Polling();

        when(pollingRepository.findById(pollingId)).thenReturn(Optional.of(polling));

        String finalMostVotedMeal = mealVotingServiceImplementation.findMostVotedMeal(pollingId, supplierId);

        assertEquals(votedMeal, finalMostVotedMeal);
        verify(pollingRepository).save(polling);
    }
}