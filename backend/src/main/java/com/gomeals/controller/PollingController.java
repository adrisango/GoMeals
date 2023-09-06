package com.gomeals.controller;

import com.gomeals.model.Polling;
import com.gomeals.service.PollingService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This controller contains the methods which is required to handle a poll
 * created by the supplier
 */
@RestController
@RequestMapping("/polling")
@CrossOrigin
public class PollingController {

    @Autowired
    PollingService pollingService;

    /**
     * This method is used to retrieve details of a poll from the polling table
     * using the poll id
     * 
     * @param id polling id
     * @return Retrieved Polling object
     */
    @GetMapping("/get/{id}")
    public Polling getPollById(@PathVariable int id) {
        return pollingService.getPollById(id);
    }

    /**
     * This method is used to extract the details of all the polls that is initiated
     * by the supplier
     * from the polling table
     * 
     * @param supId supplier id
     * @return List of Polling objects which are active
     */
    @GetMapping("/get/activePolls/{supId}")
    public List<Polling> getActivePollForSuppliers(@PathVariable int[] supId) {
        return pollingService.getActivePollForSupplier(supId);
    }

    /**
     * This method used to create an entries in the polling table
     * Hence creating a poll
     * 
     * @param polling polling object
     * @return Created polling object
     */
    @PostMapping("/create")
    public Polling createPoll(@RequestBody Polling polling) {
        return pollingService.createPoll(polling);
    }

    /**
     * This method used to update the entries in the polling table
     * Hence updating a poll
     * 
     * @param polling polling object
     * @return updated polling object
     */
    @PutMapping("/update")
    public Polling updatePoll(@RequestBody Polling polling) {
        return pollingService.updatePoll(polling);
    }

    /**
     * This method used to delete the entries in the polling table
     * Hence deleting a poll
     * 
     * @param id polling id
     * @return String indicating the status of the deletion
     */
    @DeleteMapping("/delete/{id}")
    public String deletePollById(@PathVariable int id) {
        return pollingService.deletePollById(id);
    }

    /**
     * This method is used to extract all the polls created by the supplier from the
     * polling table
     * 
     * @param supId
     * @return List containing the retrieved polling objects
     */
    @GetMapping("/get/allPolls/{supId}")
    public List<Polling> getAllPolls(@PathVariable int supId) {
        return pollingService.getAllPollsForSupplier(supId);
    }
}
