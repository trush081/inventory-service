package com.trentonrush.inventoryservice.controllers;

import com.trentonrush.inventoryservice.models.SlabPrice;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.services.SlabPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for managing price-related operations.
 * Provides endpoints to add, update, delete, retrieve slab prices.
 *
 * @author Trenton Rush
 * @since 2024-08-02
 * @see SlabPriceService
 * @see SlabPrice
 * @see SlabDTO
 */
@RestController
@RequestMapping("/v1/prices")
public class SlabPriceController {

    private final SlabPriceService slabPriceService;

    public SlabPriceController(SlabPriceService slabPriceService) {
        this.slabPriceService = slabPriceService;
    }

    /**
     * Add a new price to the database.
     * @param slabDTO the details of the price to be added
     * @return ResponseEntity containing the added price
     */
    @PostMapping
    public ResponseEntity<SlabPrice> add(@RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(slabPriceService.addPrice(slabDTO));
    }

    /**
     * Update an existing price in the database.
     * @param id the id of the price to be updated
     * @param slabDTO the new details of the price
     * @return ResponseEntity containing the updated price
     */
    @PutMapping("/{id}")
    public ResponseEntity<SlabPrice> update(@PathVariable String id, @RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(slabPriceService.updatePrice(id, slabDTO));
    }

    /**
     * Delete a price from the database.
     * @param id the id of the price to be deleted
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        slabPriceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve a price by its id.
     * @param id the id of the price to be retrieved
     * @return ResponseEntity containing the price
     */
    @GetMapping("/{id}")
    public ResponseEntity<SlabPrice> get(@PathVariable String id) {
        return ResponseEntity.ok(slabPriceService.getPrice(id));
    }

    /**
     * Search for price based on slab type, color.
     * @param type the type of the slabPrice
     * @param color the color of the slabPrice
     * @return ResponseEntity containing the slab price
     */
    @GetMapping("/search")
    public ResponseEntity<SlabPrice> searchPrice(@RequestParam(required = false) String type, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(slabPriceService.searchPrice(type, color));
    }

}
