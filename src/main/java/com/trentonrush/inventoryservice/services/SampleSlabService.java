package com.trentonrush.inventoryservice.services;

import com.trentonrush.inventoryservice.exceptions.ResourceAlreadyExistsException;
import com.trentonrush.inventoryservice.exceptions.ResourceNotFoundException;
import com.trentonrush.inventoryservice.models.SampleSlab;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.repositories.SampleSlabRepository;
import com.trentonrush.inventoryservice.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

/**
 * Service class for managing slabs.
 * Provides methods to perform operations such as creating, updating, deleting, and retrieving slabs.
 *
 * @author Trenton Rush
 * @since 2024-07-27
 * @see SampleSlab
 * @see SlabDTO
 * @see SampleSlabRepository
 */
@Service
public class SampleSlabService {

    private static final Logger logger = LoggerFactory.getLogger(SampleSlabService.class);

    private final SampleSlabRepository sampleSlabRepository;

    public SampleSlabService(SampleSlabRepository sampleSlabRepository) {
        this.sampleSlabRepository = sampleSlabRepository;
    }

    /**
     * Add a new sampleSlab to the database
     * @param slabDTO the data needed to create a new sampleSlab
     */
    public SampleSlab addSample(SlabDTO slabDTO) {
        // Validate new Sample Slab details
        ValidationUtil.validateSlabDTO(slabDTO, false);
        logger.info("Request received to add new sample slab: Request -> {}", slabDTO);

        // Prevent adding duplicate samples
        if(sampleSlabRepository.existsByTypeAndColor(slabDTO.getType(), slabDTO.getColor())) {
            throw new ResourceAlreadyExistsException("Sample Slab", "type & color", slabDTO.getType() + "," + slabDTO.getColor());
        }

        // Create new sampleSlab object
        SampleSlab sampleSlab = SampleSlab.build(slabDTO);

        // Set creation times
        Instant now = Instant.now();
        sampleSlab.setCreationDate(now);
        sampleSlab.setModificationDate(now);

        // Save new sample slab
        SampleSlab savedSampleSlab = sampleSlabRepository.save(sampleSlab);
        logger.info("New SampleSlab added to database: {}", savedSampleSlab);
        return savedSampleSlab;
    }

    /**
     * Update an existing sampleSlab in the database
     * @param id the id of the sampleSlab being updated
     * @param slabDTO the new fields being updated
     */
    public SampleSlab updateSample(String id, SlabDTO slabDTO) {
        logger.info("Request received to update sample slab: SampleSlab ID -> {}, Request -> {}", id, slabDTO);

        // Get the existing slab from the database
        SampleSlab sampleSlab = getSample(id);
        logger.info("Current SampleSlab being updated -> {}", sampleSlab);

        // Update only the fields that are provided and not empty
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getImage())) {
            sampleSlab.setImage(slabDTO.getImage());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getColor())) {
            sampleSlab.setColor(slabDTO.getColor());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getType())) {
            sampleSlab.setType(slabDTO.getType());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getSupplier())) {
            sampleSlab.setSupplier(slabDTO.getSupplier());
        }
        if (slabDTO.getQuantity() >= 0) {
            sampleSlab.setQuantity(slabDTO.getQuantity());
        }

        // Update modification date
        sampleSlab.setModificationDate(Instant.now());

        SampleSlab savedSampleSlab = sampleSlabRepository.save(sampleSlab);
        logger.info("SampleSlab modified in database: {}", savedSampleSlab);
        return savedSampleSlab;
    }

    /**
     * Delete a sampleSlab from the db
     * @param id the id of the sampleSlab to be deleted
     */
    public void deleteSample(String id) {
        sampleSlabRepository.delete(getSample(id));
        logger.info("SampleSlab deleted from database: {}", id);
    }

    /**
     * Get a sampleSlab from the db
     * @param id the id used to retrieve the sampleSlab
     * @return the sampleSlab associated with the provided id
     */
    public SampleSlab getSample(String id) {
        return sampleSlabRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("SampleSlab not found with id: {}", id);
                    return new ResourceNotFoundException("Sample Slab with id " + id + " not found");
                });
    }

    /**
     * Search for samples based on type, color, and status.
     *
     * @param type the type of the samples (optional)
     * @param color the color of the samples (optional)
     * @param onlyAvailable the boolean value to check for available samples only (optional)
     * @return a list of SampleSlabs found
     */
    public List<SampleSlab> listSamples(String type, String color, boolean onlyAvailable) {
        List<SampleSlab> sampleSlabs;
        if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color) && onlyAvailable) {
            sampleSlabs = sampleSlabRepository.findAllByTypeAndColorAndQuantityGreaterThan(type, color, 0);
        } else if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            sampleSlabs = sampleSlabRepository.findAllByTypeAndColor(type, color);
        } else if (!ValidationUtil.isNullOrEmpty(type) && onlyAvailable) {
            sampleSlabs = sampleSlabRepository.findAllByTypeAndQuantityGreaterThan(type, 0);
        } else if (!ValidationUtil.isNullOrEmpty(color) && onlyAvailable) {
            sampleSlabs = sampleSlabRepository.findAllByColorAndQuantityGreaterThan(color, 0);
        } else if (!ValidationUtil.isNullOrEmpty(type)) {
            sampleSlabs = sampleSlabRepository.findAllByType(type);
        } else if (!ValidationUtil.isNullOrEmpty(color)) {
            sampleSlabs = sampleSlabRepository.findAllByColor(color);
        } else if (onlyAvailable) {
            sampleSlabs = sampleSlabRepository.findAllByQuantityGreaterThan(0);
        } else {
            logger.info("No specifiers for search. Listing all SampleSlabs.");
            return sampleSlabRepository.findAll();
        }
        logger.info("SampleSlabs {} found with type: {}, color: {}, onlyAvailable: {}", sampleSlabs.stream().map(SampleSlab::getId).toList(), type, color, onlyAvailable);
        return sampleSlabs;
    }

    /**
     * Increase the quantity of a specific Sample Slab
     *
     * @param id the id of the slab to increment
     */
    public void incrementQuantity(String id) {
        SampleSlab sampleSlab = getSample(id);
        sampleSlab.setQuantity(sampleSlab.getQuantity() + 1);
        sampleSlab.setModificationDate(Instant.now());
        sampleSlabRepository.save(sampleSlab);
        logger.info("SampleSlab {} increased quantity to {}", sampleSlab.getId(), sampleSlab.getQuantity());
    }

    /**
     * Decrement the quantity of a specific Sample Slab
     *
     * @param id the id of the slab to decrement
     */
    public void decrementQuantity(String id) {
        SampleSlab sampleSlab = getSample(id);
        sampleSlab.setQuantity(sampleSlab.getQuantity() - 1);
        sampleSlab.setModificationDate(Instant.now());
        sampleSlabRepository.save(sampleSlab);
        logger.info("SampleSlab {} decreased quantity to {}", sampleSlab.getId(), sampleSlab.getQuantity());
    }

    /**
     * Check if any SampleSlabs have a quantity greater than 0
     * @param type the type of sampleSlab being checked
     * @param color the color of sampleSlab being checked
     * @return true if there is a SampleSlab is available, false otherwise
     */
    public boolean checkAvailability(String type, String color) {
        logger.info("Checking availability with type: '{}' and color: '{}'", type, color);

        boolean isAvailable;

        if (!ValidationUtil.isNullOrEmpty(type) && ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by type: '{}'", type);
            isAvailable = sampleSlabRepository.existsByTypeAndQuantityGreaterThan(type, 0);
        } else if (ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by color: '{}'", color);
            isAvailable = sampleSlabRepository.existsByColorAndQuantityGreaterThan(color, 0);
        } else if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by type: '{}' and color: '{}'", type, color);
            isAvailable = sampleSlabRepository.existsByTypeAndColorAndQuantityGreaterThan(type, color, 0);
        } else {
            logger.warn("Neither type nor color provided.");
            isAvailable = false;
        }

        logger.info("Availability check result for SampleSlab: {}", isAvailable);
        return isAvailable;
    }
}
