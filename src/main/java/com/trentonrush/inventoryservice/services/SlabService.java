package com.trentonrush.inventoryservice.services;

import com.trentonrush.inventoryservice.exceptions.InvalidInputException;
import com.trentonrush.inventoryservice.exceptions.ResourceNotFoundException;
import com.trentonrush.inventoryservice.models.Slab;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.models.enums.Status;
import com.trentonrush.inventoryservice.repositories.SlabRepository;
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
 * @see Slab
 * @see SlabDTO
 * @see SlabRepository
 */
@Service
public class SlabService {

    private static final Logger logger = LoggerFactory.getLogger(SlabService.class);

    private final SlabRepository slabRepository;

    public SlabService(SlabRepository slabRepository) {
        this.slabRepository = slabRepository;
    }

    /**
     * Add a new slab to the database
     * @param slabDTO the data needed to create a new slab
     */
    public Slab addSlab(SlabDTO slabDTO) {
        // Validate new slab details
        ValidationUtil.validateSlabDTO(slabDTO, true);
        logger.info("Request received to add new slab: Request -> {}", slabDTO);

        // Create new slab object
        Slab slab = Slab.build(slabDTO);
        slab.setStatus(Status.AVAILABLE);

        // Set creation times
        Instant now = Instant.now();
        slab.setCreationDate(now);
        slab.setModificationDate(now);

        // Save new slab
        Slab savedSlab = slabRepository.save(slab);
        logger.info("New Slab added to database: {}", savedSlab);
        return savedSlab;
    }

    /**
     * Update an existing slab in the database
     * @param id the id of the slab being updated
     * @param slabDTO the new fields being updated
     */
    public Slab updateSlab(String id, SlabDTO slabDTO) {
        logger.info("Request received to update slab: Slab ID -> {}, Request -> {}", id, slabDTO);

        // Get the existing slab from the database
        Slab slab = getSlab(id);
        logger.info("Current Slab being updated -> {}", slab);

        // Update only the fields that are provided and not empty
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getImage())) {
            slab.setImage(slabDTO.getImage());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getDescription())) {
            slab.setDescription(slabDTO.getDescription());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getColor())) {
            slab.setColor(slabDTO.getColor());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getType())) {
            slab.setType(slabDTO.getType());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getLocation())) {
            slab.setLocation(slabDTO.getLocation());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getSupplier())) {
            slab.setSupplier(slabDTO.getSupplier());
        }
        // catch if the dimensions contain any invalid values
        try {
            ValidationUtil.validateDimensions(slabDTO.getDimensions());
            slab.setDimensions(slabDTO.getDimensions());
        } catch (InvalidInputException ignored) {
            // do nothing if dimensions are empty
        }

        // Handle booleans directly since they can't be null
        slab.setRemnant(slabDTO.isRemnant());
        slab.setDamaged(slabDTO.isDamaged());

        // Update status accordingly
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getStatus())) {
            slab.setStatus(Status.fromString(slabDTO.getStatus()));
        }

        // Update modification date
        slab.setModificationDate(Instant.now());

        // Save the updated slab
        Slab savedSlab = slabRepository.save(slab);
        logger.info("Slab modified in database: {}", savedSlab);
        return savedSlab;
    }

    /**
     * Delete a slab from the db
     * @param id the id of the slab to be deleted
     */
    public void deleteSlab(String id) {
        slabRepository.delete(getSlab(id));
        logger.info("Slab deleted from database: {}", id);
    }

    /**
     * Get a slab from the db
     * @param id the id used to retrieve the slab
     * @return the slab associated with the provided id
     */
    public Slab getSlab(String id) {
        return slabRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Slab not found with id: {}", id);
                    return new ResourceNotFoundException("Slab with id " + id + " not found");
                });
    }

    /**
     * Does a search in the db for slabs matching the given criteria
     * @param type the type of slab being searched
     * @param color the color of slab being searched
     * @param status the color of the slab being searched
     * @return a list of slabs found during search
     */
    public List<Slab> listSlabs(String type, String color, String status) {
        List<Slab> slabs;
        if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color) && !ValidationUtil.isNullOrEmpty(status)) {
            slabs = slabRepository.findAllByTypeAndColorAndStatus(type, color, Status.fromString(status));
        } else if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            slabs = slabRepository.findAllByTypeAndColor(type, color);
        } else if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(status)) {
            slabs = slabRepository.findAllByTypeAndStatus(type, Status.fromString(status));
        } else if (!ValidationUtil.isNullOrEmpty(color) && !ValidationUtil.isNullOrEmpty(status)) {
            slabs = slabRepository.findAllByColorAndStatus(color, Status.fromString(status));
        } else if (!ValidationUtil.isNullOrEmpty(type)) {
            slabs = slabRepository.findAllByType(type);
        } else if (!ValidationUtil.isNullOrEmpty(color)) {
            slabs = slabRepository.findAllByColor(color);
        } else if (!ValidationUtil.isNullOrEmpty(status)) {
            slabs = slabRepository.findAllByStatus(Status.fromString(status));
        } else {
            logger.info("No specifiers for search. Listing all slabs.");
            return slabRepository.findAll();
        }
        logger.info("Slabs {} found with type: {}, color: {}, status: {}", slabs.stream().map(Slab::getId).toList(), type, color, status);
        return slabs;
    }

    /**
     * Changes the status of a specified slab to Reserved
     * @param id the id of the slab being reserved
     */
    public void reserveSlab(String id) {
        Slab slab = getSlab(id);
        logger.info("Current status of slab: Slab ID -> {}, Current Status -> {}", id, slab.getStatus());

        // Update slab status to RESERVED
        slab.setStatus(Status.RESERVED);
        slab.setModificationDate(Instant.now());
        slabRepository.save(slab);

        // Log the reservation action
        logger.info("Slab reserved in database: Slab ID -> {}", id);
    }

    /**
     * Check if any slabs has the "available" status
     * @param type the type of slab being checked
     * @param color the color of slab being checked
     * @return true if there is a slab available, false otherwise
     */
    public boolean checkAvailability(String type, String color) {
        logger.info("Checking availability with type: '{}' and color: '{}'", type, color);

        boolean isAvailable;

        if (!ValidationUtil.isNullOrEmpty(type) && ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by type: '{}'", type);
            isAvailable = slabRepository.existsByTypeAndStatus(type, Status.AVAILABLE);
        } else if (ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by color: '{}'", color);
            isAvailable = slabRepository.existsByColorAndStatus(color, Status.AVAILABLE);
        } else if (!ValidationUtil.isNullOrEmpty(type) && !ValidationUtil.isNullOrEmpty(color)) {
            logger.debug("Checking availability by type: '{}' and color: '{}'", type, color);
            isAvailable = slabRepository.existsByTypeAndColorAndStatus(type, color, Status.AVAILABLE);
        } else {
            logger.warn("Neither type nor color provided.");
            isAvailable = false;
        }

        logger.info("Availability check result for Slab: {}", isAvailable);
        return isAvailable;
    }
}
