package com.gomeals.service.implementation;

import com.gomeals.model.Polling;
import com.gomeals.repository.PollingRepository;
import com.gomeals.service.PollingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Implementation of PollingService interface that provides methods for managing polling operations.
 */
@Service
public class PollingServiceImplementation implements PollingService {

    @Autowired
    PollingRepository pollingRepository;
    /**
     * Creates a new polling entity.
     * @param polling The polling entity to be created.
     * @return The created polling entity.
     */
    @Override
    public Polling createPoll(Polling polling) {
        return pollingRepository.save(polling);
    }
    /**
     * Retrieves the polling entity by pollId.
     * @param id The pollId to search for.
     * @return The polling entity with the specified pollId.
     */
    @Override
    public Polling getPollById(int id) {
        return pollingRepository.findById(id).orElse(null);
    }
    /**
     * Retrieves active polls for a list of supplierIds.
     * @param supId The supplierIds to search for.
     * @return The list of active polls for the specified supplierIds.
     */
    public List<Polling> getActivePollForSupplier(int[] supId) {
        return pollingRepository.getActivePollForSupplier(supId);
    }
    /**
     * Updates an existing polling entity.
     * @param polling The polling entity to be updated.
     * @return The updated polling entity.
     */
    @Override
    public Polling updatePoll(Polling polling) {
        Polling currentPolling = pollingRepository.findById(polling.getPollId()).orElse(null);
        if(currentPolling != null) {
            currentPolling.setPollDate(polling.getPollDate());
            currentPolling.setItem1(polling.getItem1());
            currentPolling.setItem2(polling.getItem2());
            currentPolling.setItem3(polling.getItem3());
            currentPolling.setItem4(polling.getItem4());
            currentPolling.setItem5(polling.getItem5());
            currentPolling.setStatus(polling.getStatus());
            pollingRepository.save(currentPolling);
        }
        return currentPolling;
    }
    /**
     * Deletes a polling entity by pollId.
     * @param id The pollId of the polling entity to be deleted.
     * @return A string indicating the result of the deletion operation.
     */
    @Override
    public String deletePollById(int id) {
        pollingRepository.deleteById(id);
        return "Poll deleted!";
    }
    /**
     * Retrieves all polls for a supplier by supId.
     * @param supId The supId to search for.
     * @return The list of polls for the specified supId.
     */
    @Override
    public List<Polling> getAllPollsForSupplier(int supId) {
        return pollingRepository.getAllPollsForSupplier(supId);
    }
}
