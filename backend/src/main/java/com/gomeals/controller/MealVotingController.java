package com.gomeals.controller;

import com.gomeals.model.MealVoting;
import com.gomeals.service.MealVotingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This controller contains the methods that handles the meal voting procedure
 */
@RestController
@RequestMapping("/meal-voting")
@CrossOrigin
public class MealVotingController {

    private final MealVotingService mealVotingService;

    public MealVotingController(MealVotingService mealVotingService) {
        this.mealVotingService = mealVotingService;
    }

    /**
     * This method is used to Create an entry in the meal voting table
     * Hence creating a poll with multiple meals
     * 
     * @param mealVoting
     * @return Created MealVoting object
     */
    @PostMapping("/create")
    public ResponseEntity<MealVoting> createMealVoting(@RequestBody MealVoting mealVoting) {
        return new ResponseEntity<>(mealVotingService.createMealVoting(mealVoting), HttpStatus.CREATED);
    }

    /**
     * This method is used to extract data from the meal voting table using poll id
     * Hence Extracting all the details of a particular meal poll
     * 
     * @param pollId
     * @return Retrieved MealVoting object
     */
    @GetMapping("/get/{poll-id}")
    public ResponseEntity<MealVoting> getMealVotingByPollId(@PathVariable("poll-id") int pollId) {
        MealVoting mealVoting = mealVotingService.getMealVotingByPollingId(pollId);
        if (mealVoting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mealVoting, HttpStatus.OK);
    }

    /**
     * This method is used to extract a particular meal voting related to a
     * particular customer
     * 
     * @param pollId
     * @param custId
     * @return Retrieved MealVoting object
     */
    @GetMapping("/get/{pollId}/{custId}")
    @ResponseBody
    public ResponseEntity<MealVoting> getMealVotingForCustomerByPollId(@PathVariable("pollId") int pollId,
            @PathVariable("custId") int custId) {
        MealVoting mealVoting = mealVotingService.getMealVotingForCustomerByPollId(pollId, custId);
        if (mealVoting == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            // return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(mealVoting, HttpStatus.OK);
    }

    /**
     * This method is used to extract the meal which is most voted in a poll
     * 
     * @param pollingId
     * @param supplierId
     * @return String which contains the name of the most voted meal
     */
    @GetMapping("/get/most-voted-meal/{pollingId}/{supplierId}")
    public ResponseEntity<String> getMostVotedMeal(@PathVariable("pollingId") int pollingId,
            @PathVariable("supplierId") int supplierId) {
        String mostVotedMeal = mealVotingService.findMostVotedMeal(pollingId, supplierId);
        if (mostVotedMeal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mostVotedMeal, HttpStatus.OK);
    }

}
