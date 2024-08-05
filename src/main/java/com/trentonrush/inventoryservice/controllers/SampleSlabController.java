package com.trentonrush.inventoryservice.controllers;

import com.trentonrush.inventoryservice.models.SampleSlab;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.services.SampleSlabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing sample-slab-related operations.
 * Provides endpoints to add, update, delete, retrieve, search, reserve, and check the availability of sample-slabs.
 *
 * @author Trenton Rush
 * @since 2024-07-27
 * @see SampleSlabService
 * @see SampleSlab
 * @see SlabDTO
 */
@RestController
@RequestMapping("/v1/samples")
public class SampleSlabController {

    private final SampleSlabService sampleSlabService;

    public SampleSlabController(SampleSlabService sampleSlabService) {
        this.sampleSlabService = sampleSlabService;
    }

    /**
     * Add a new sample to the database.
     * @param slabDTO the details of the sample to be added
     * @return ResponseEntity containing the added sample
     */
    @PostMapping
    public ResponseEntity<SampleSlab> add(@RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(sampleSlabService.addSample(slabDTO));
    }

    /**
     * Update existing samples in the database.
     * @param id the id of the samples to be updated
     * @param slabDTO the new details of the samples
     * @return ResponseEntity containing the updated samples
     */
    @PutMapping("/{id}")
    public ResponseEntity<SampleSlab> update(@PathVariable String id, @RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(sampleSlabService.updateSample(id, slabDTO));
    }

    /**
     * Delete a sample from the database.
     * @param id the id of the sample to be deleted
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        sampleSlabService.deleteSample(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve a sample by its id.
     * @param id the id of the sample to be retrieved
     * @return ResponseEntity containing the sample
     */
    @GetMapping("/{id}")
    public ResponseEntity<SampleSlab> get(@PathVariable String id) {
        return ResponseEntity.ok(sampleSlabService.getSample(id));
    }

    /**
     * Search for samples based on type, color, and status.
     * @param type the type of the samples (optional)
     * @param color the color of the samples (optional)
     * @param onlyAvailable the boolean value to check for available samples only (optional)
     * @return ResponseEntity containing the list of samples
     * TODO: Add support for more generic searching by color
     */
    @GetMapping("/search")
    public ResponseEntity<List<SampleSlab>> searchSlab(@RequestParam(required = false) String type,
                                                 @RequestParam(required = false) String color,
                                                 @RequestParam(name = "only_available", defaultValue = "false") Boolean onlyAvailable) {
        return ResponseEntity.ok(sampleSlabService.listSamples(type, color, onlyAvailable));
    }

    /**
     * Increment the quantity of a sample by one.
     * @param id the id of the sample
     * @return ResponseEntity with no content
     */
    @PatchMapping("/{id}/increment-quantity")
    public ResponseEntity<Void> incrementQuantity(@PathVariable String id) {
        sampleSlabService.incrementQuantity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Increment the quantity of a sample by one.
     * @param id the id of the sample
     * @return ResponseEntity with no content
     */
    @PatchMapping("/{id}/decrement-quantity")
    public ResponseEntity<Void> decrementQuantity(@PathVariable String id) {
        sampleSlabService.decrementQuantity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check availability of samples based on type and color.
     * @param type the type of sample
     * @param color the color of sample
     * @return ResponseEntity with a boolean indicating availability
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam(required = false) String type, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(sampleSlabService.checkAvailability(type, color));
    }
}
