package com.gomeals.service;

import com.gomeals.model.MealVoting;

public interface MealVotingService {
    MealVoting createMealVoting(MealVoting mealVoting);

    MealVoting getMealVotingByPollingId(int pollingId);

    MealVoting getMealVotingForCustomerByPollId(int pollId, int custId);

    String findMostVotedMeal(int pollingId, int supplierId);
}
