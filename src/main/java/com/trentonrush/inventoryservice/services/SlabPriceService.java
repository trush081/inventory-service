package com.trentonrush.inventoryservice.services;

import com.trentonrush.inventoryservice.exceptions.InvalidInputException;
import com.trentonrush.inventoryservice.exceptions.ResourceNotFoundException;
import com.trentonrush.inventoryservice.models.SlabPrice;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.repositories.SlabPriceRepository;
import com.trentonrush.inventoryservice.utils.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static com.trentonrush.inventoryservice.utils.InventoryConstants.*;

/**
 * Service class for managing slab prices.
 * Provides methods to perform operations such as creating, updating, deleting, and retrieving prices.
 *
 * @author Trenton Rush
 * @since 2024-08-02
 * @see SlabPrice
 * @see SlabDTO
 */
@Service
public class SlabPriceService {

    private static final Logger logger = LoggerFactory.getLogger(SlabPriceService.class);

    private final SlabPriceRepository slabPriceRepository;

    public SlabPriceService(SlabPriceRepository slabPriceRepository) {
        this.slabPriceRepository = slabPriceRepository;
    }

    /**
     * Add a new price to the database
     * @param slabDTO the data needed to create a new price
     */
    public SlabPrice addPrice(SlabDTO slabDTO) {
        // Validate new price details
        ValidationUtil.validatePriceDetails(slabDTO);
        logger.info("Request received to add new slabPrice: Request -> {}", slabDTO);

        // Create new slabPrice object
        SlabPrice slabPrice = SlabPrice.buildSlabPrice(slabDTO);

        // Set creation times
        Instant now = Instant.now();
        slabPrice.setCreationDate(now);
        slabPrice.setModificationDate(now);

        // Save new slabPrice
        SlabPrice savedSlabPrice = slabPriceRepository.save(slabPrice);
        logger.info("New SlabPrice added to database: {}", savedSlabPrice);
        return slabPrice;
    }

    /**
     * Update an existing price in the database
     * @param id the id of the price being updated
     * @param slabDTO the new fields being updated
     */
    public SlabPrice updatePrice(String id, SlabDTO slabDTO) {
        logger.info("Request received to update price: SlabPrice ID -> {}, Request -> {}", id, slabDTO);

        // Get the existing slab from the database
        SlabPrice slabPrice = getPrice(id);
        logger.info("Current SlabPrice being updated -> {}", slabPrice);

        // Update only the fields that are provided and not empty

        if (!ValidationUtil.isNullOrEmpty(slabDTO.getColor())) {
            slabPrice.setColor(slabDTO.getColor());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getType())) {
            slabPrice.setType(slabDTO.getType());
        }
        if (!ValidationUtil.isNullOrEmpty(slabDTO.getSqftPrice())) {
            slabPrice.setAmountPerSqFtAndCurrency(new BigDecimal(slabDTO.getSqftPrice()), Currency.getInstance(USD_CURRENCY_CODE));
        }

        // Update modification date
        slabPrice.setModificationDate(Instant.now());

        // Save the updated slab
        SlabPrice savedSlabPrice = slabPriceRepository.save(slabPrice);
        logger.info("SlabPrice modified in database: {}", savedSlabPrice);
        return savedSlabPrice;
    }

    /**
     * Delete a price from the db
     * @param id the id of the price to be deleted
     */
    public void deletePrice(String id) {
        slabPriceRepository.delete(getPrice(id));
        logger.info("SlabPrice deleted from database: {}", id);
    }

    /**
     * Get a price from the db
     * @param id the id used to retrieve the price
     * @return the price associated with the provided id
     */
    public SlabPrice getPrice(String id) {
        return slabPriceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("SlabPrice not found with id: {}", id);
                    return new ResourceNotFoundException("SlabPrice with id " + id + " not found");
                });
    }

    /**
     * Does a search in the db for price matching the given criteria
     * @param type the type of slabPrice being searched
     * @param color the color of slabPrice being searched
     * @return the price that matches the slab type and color
     */
    public SlabPrice searchPrice(String type, String color) {
        if (ValidationUtil.isNullOrEmpty(type) || ValidationUtil.isNullOrEmpty(color)) {
            throw new InvalidInputException("Type and color must not be null or empty");
        }

        // get the specific slabPrice
        return slabPriceRepository.findByTypeAndColor(type, color)
                .orElseThrow(() -> new ResourceNotFoundException("SlabPrice with type " + type + " and color " + color + " not found"));
    }
}
