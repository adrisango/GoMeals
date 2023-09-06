package com.gomeals.service.implementation;

import com.gomeals.model.MealVoting;
import com.gomeals.model.Polling;
import com.gomeals.repository.MealVotingRepository;
import com.gomeals.repository.PollingRepository;
import com.gomeals.service.MealVotingService;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Implementation of MealVotingService interface that provides methods for managing meal voting operations.
 */
@Service
public class MealVotingServiceImplementation implements MealVotingService {

    private final MealVotingRepository mealVotingRepository;
    private final PollingRepository pollingRepository;
    /**
     * Constructor to inject dependencies of {@link MealVotingServiceImplementation}.
     *
     * @param mealVotingRepository The repository for {@link MealVoting}.
     * @param pollingRepository    The repository for {@link Polling}.
     */
    public MealVotingServiceImplementation(MealVotingRepository mealVotingRepository,
            PollingRepository pollingRepository) {
        this.mealVotingRepository = mealVotingRepository;
        this.pollingRepository = pollingRepository;
    }
    /**
     * Creates a new meal voting entity.
     * @param mealVoting The meal voting entity to be created.
     * @return The created meal voting entity.
     */
    @Override
    public MealVoting createMealVoting(MealVoting mealVoting) {
        return mealVotingRepository.save(mealVoting);
    }
    /**
     * Retrieves the meal voting entity by pollingId.
     * @param pollingId The pollingId to search for.
     * @return The meal voting entity with the specified pollingId.
     */
    @Override
    public MealVoting getMealVotingByPollingId(int pollingId) {
        return mealVotingRepository.findByPollingId(pollingId);
    }
    /**
     * Retrieves the meal voting entity for a customer by pollId and custId.
     * @param pollId The pollId to search for.
     * @param custId The custId to search for.
     * @return The meal voting entity for the specified pollId and custId.
     */
    @Override
    public MealVoting getMealVotingForCustomerByPollId(int pollId, int custId) {
        return mealVotingRepository.getMealVotingForCustomerByPollId(pollId, custId);
    }
    /**
     * Finds the most voted meal for a polling and supplier, updates the polling entity and returns the most voted meal.
     * @param pollingId The pollingId to search for.
     * @param supplierId The supplierId to search for.
     * @return The most voted meal for the specified pollingId and supplierId.
     */
    public String findMostVotedMeal(int pollingId, int supplierId) {
        List<Object[]> mostVotedMeals = mealVotingRepository.countMealVotes(pollingId, supplierId);
        if (mostVotedMeals.isEmpty()) {
            System.out.println("No voted meals were found.");
            return null;
        }
        String mostVotedMeal = null;
        for (Object[] object : mostVotedMeals) {
            mostVotedMeal = (String) object[0];
            Long voteCount = (Long) object[1];
        }
        Polling polling = pollingRepository.findById(pollingId).orElse(null);
        if (polling == null) {
            System.out.println("Poll does not exist, can't update the special meal.");
            return null;
        }
        polling.setVote(mostVotedMeal);
        polling.setStatus(false);
        pollingRepository.save(polling);

        return mostVotedMeal;
    }
}
