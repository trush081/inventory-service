package com.trentonrush.inventoryservice.utils;

import com.trentonrush.inventoryservice.exceptions.InvalidInputException;
import com.trentonrush.inventoryservice.models.Dimensions;
import com.trentonrush.inventoryservice.models.Measurement;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;

import java.util.Objects;

/**
 * Utility Class for validation methods
 *
 * @author Trenton Rush
 * @since 2024-07-29
 * @see SlabDTO
 * @see Dimensions
 * @see Measurement
 */
public class ValidationUtil {

    private ValidationUtil() {
        throw new AssertionError();
    }

    /**
     * Validate a slabDTO has non-null fields
     * @param slabDTO object being validated
     * @param isSlab specifies if Dimensions object needs to be checked
     */
    public static void validateSlabDTO(SlabDTO slabDTO, boolean isSlab) {
        if (Objects.isNull(slabDTO)) {
            throw new InvalidInputException("Slab is null");
        }

        if (isNullOrEmpty(slabDTO.getImage())) {
            throw new InvalidInputException("Image is empty");
        }

        if (isNullOrEmpty(slabDTO.getColor())) {
            throw new InvalidInputException("Color is empty");
        }

        if (isNullOrEmpty(slabDTO.getType())) {
            throw new InvalidInputException("Type is empty");
        }

        if (isNullOrEmpty(slabDTO.getSupplier())) {
            throw new InvalidInputException("Supplier is empty");
        }

        if (isSlab) {
            if (isNullOrEmpty(slabDTO.getLocation())) {
                throw new InvalidInputException("Location is empty");
            }
            ValidationUtil.validateDimensions(slabDTO.getDimensions());
        }
    }

    /**
     * Validate a slabDTO pricing details
     * @param slabDTO object being validated
     */
    public static void validatePriceDetails(SlabDTO slabDTO) {
        if (Objects.isNull(slabDTO)) {
            throw new InvalidInputException("Slab is null");
        }

        if (isNullOrEmpty(slabDTO.getColor())) {
            throw new InvalidInputException("Color is empty");
        }

        if (isNullOrEmpty(slabDTO.getType())) {
            throw new InvalidInputException("Type is empty");
        }

        if (isNullOrEmpty(slabDTO.getSqftPrice())) {
            throw new InvalidInputException("Sqft price is empty");
        }
    }

    /**
     * Check if string is null or empty
     * @param str string being checked
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.trim().isEmpty();
    }

    /**
     * Validate if dimensions data
     * @param dimensions object being validated
     */
    public static void validateDimensions(Dimensions dimensions) {
        if (Objects.isNull(dimensions)) {
            throw new InvalidInputException("Dimensions is null");
        }
        validateMeasurement(dimensions.getLength());
        validateMeasurement(dimensions.getWidth());
        validateMeasurement(dimensions.getThickness());
    }

    /**
     * Validate if measurement data is greater than 0
     * @param measurement values being validated
     */
    private static void validateMeasurement(Measurement measurement) {
        if (Objects.isNull(measurement)) {
            throw new InvalidInputException("Dimensions contains an empty measurement");
        }
        if (measurement.getCentimeters() <= 0.0 && measurement.getFeet() <= 0.0 && measurement.getInches() <= 0.0) {
            throw new InvalidInputException("All measurements must contain a value greater than zero");
        }
    }
}
