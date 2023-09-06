package com.gomeals.service.implementation;

import com.gomeals.model.Complain;
import com.gomeals.repository.ComplainRepository;
import com.gomeals.service.ComplainService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class implements the ComplainService interface and provides the implementation
 * for the methods defined in the interface.
 */
@Service
public class ComplainServiceImplementation implements ComplainService {
    private final ComplainRepository complainRepository;

    public ComplainServiceImplementation(ComplainRepository complainRepository) {
        this.complainRepository = complainRepository;
    }
    /**
     * Creates a new complain.
     *
     * @param complain The complain object to be created.
     * @return The created complain object.
     */
    @Override
    public Complain createComplain(Complain complain) {
        return complainRepository.save(complain);
    }
    /**
     * Retrieves a complain by its ID.
     *
     * @param complainId The ID of the complain to be retrieved.
     * @return The complain object with the given ID, or null if not found.
     */
    @Override
    public Complain getComplainById(Integer complainId) {
        Complain complain = complainRepository.findById(complainId).orElse(null);
        return complain;
    }
    /**
     * Retrieves all complains.
     *
     * @return A list of all complain objects.
     */
    @Override
    public List<Complain> getAllComplains() {
        List<Complain> complains = new ArrayList<>();
        complainRepository.findAll().forEach(complain -> complains.add(complain));
        return complains;
    }
    /**
     * Retrieves complains by customer ID.
     *
     * @param customerId The ID of the customer for which complains are to be retrieved.
     * @return A list of complain objects associated with the given customer ID.
     */
    @Override
    public List<Complain> getComplainsByCustomerId(Integer customerId) {
        List<Complain> complains = new ArrayList<>();
        complainRepository.findComplainsByCustomerId(customerId).forEach(complain -> complains.add(complain));
        return complains;
    }
    /**
     * Retrieves complains by supplier ID.
     *
     * @param supplierId The ID of the supplier for which complains are to be retrieved.
     * @return A list of complain objects associated with the given supplier ID.
     */
    @Override
    public List<Complain> getComplainsBySupplierId(Integer supplierId) {
        List<Complain> complains = new ArrayList<>();
        complainRepository.findComplainsBySupplierId(supplierId).forEach(complain -> complains.add(complain));
        return complains;
    }
    /**
     * Retrieves complains by customer ID and supplier ID.
     *
     * @param customerId The ID of the customer for which complains are to be retrieved.
     * @param supplierId The ID of the supplier for which complains are to be retrieved.
     * @return A list of complain objects associated with the given customer ID and supplier ID.
     */
    @Override
    public List<Complain> getComplainsByCustomerIdAndSupplierId(Integer customerId, Integer supplierId) {
        List<Complain> complains;
        complains = complainRepository.findComplainsByCustomerIdAndSupplierId(customerId, supplierId);
        return complains;
    }
    /**
     * Updates an existing complain.
     *
     * @param complain The complain object to be updated.
     * @return The updated complain object.
     */
    @Override
    public Complain updateComplain(Complain complain) {
        return complainRepository.save(complain);
    }
    /**
     * Deletes a complain by its ID.
     *
     * @param complainId The ID of the complain to be deleted.
     */
    @Transactional
    @Override
    public void deleteComplain(Integer complainId) { // throws NoSuchElementException {
        if (complainRepository.findById(complainId).isPresent()) {
            complainRepository.deleteById(complainId);
        } else {

            // throw new NoSuchElementException("Complain not found with id: "+complainId );
        }
    }
}
