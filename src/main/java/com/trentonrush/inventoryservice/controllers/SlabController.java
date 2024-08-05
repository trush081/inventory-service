package com.trentonrush.inventoryservice.controllers;

import com.trentonrush.inventoryservice.models.Slab;
import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.services.SlabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing slab-related operations.
 * Provides endpoints to add, update, delete, retrieve, search, reserve, and check the availability of slabs.
 *
 * @author Trenton Rush
 * @since 2024-07-27
 * @see SlabService
 * @see Slab
 * @see SlabDTO
 */
@RestController
@RequestMapping("/v1/slabs")
public class SlabController {

    private final SlabService slabService;

    public SlabController(SlabService slabService) {
        this.slabService = slabService;
    }

    /**
     * Add a new slab to the database.
     * @param slabDTO the details of the slab to be added
     * @return ResponseEntity containing the added slab
     */
    @PostMapping
    public ResponseEntity<Slab> add(@RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(slabService.addSlab(slabDTO));
    }

    /**
     * Update an existing slab in the database.
     * @param id the id of the slab to be updated
     * @param slabDTO the new details of the slab
     * @return ResponseEntity containing the updated slab
     */
    @PutMapping("/{id}")
    public ResponseEntity<Slab> update(@PathVariable String id, @RequestBody SlabDTO slabDTO) {
        return ResponseEntity.ok(slabService.updateSlab(id, slabDTO));
    }

    /**
     * Delete a slab from the database.
     * @param id the id of the slab to be deleted
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        slabService.deleteSlab(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve a slab by its id.
     * @param id the id of the slab to be retrieved
     * @return ResponseEntity containing the slab
     */
    @GetMapping("/{id}")
    public ResponseEntity<Slab> get(@PathVariable String id) {
        return ResponseEntity.ok(slabService.getSlab(id));
    }

    /**
     * Search for slabs based on type, color, and status.
     * @param type the type of the slabs (optional)
     * @param color the color of the slabs (optional)
     * @param status the status of the slabs (optional)
     * @return ResponseEntity containing the list of slabs
     * TODO: Add support for more generic searching by color
     */
    @GetMapping("/search")
    public ResponseEntity<List<Slab>> searchSlab(@RequestParam(required = false) String type,
                                                @RequestParam(required = false) String color,
                                                @RequestParam(required = false) String status) {
        return ResponseEntity.ok(slabService.listSlabs(type, color, status));
    }

    /**
     * Reserve a slab by updating its status.
     * @param id the id of the slab to be reserved
     * @return ResponseEntity with no content
     */
    @PatchMapping("/{id}/reserve")
    public ResponseEntity<Void> reserveSlab(@PathVariable String id) {
        slabService.reserveSlab(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check availability of slabs based on type and color.
     * @param type the type of slabs
     * @param color the color of slabs
     * @return ResponseEntity with a boolean indicating availability
     */
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAvailability(@RequestParam(required = false) String type, @RequestParam(required = false) String color) {
        return ResponseEntity.ok(slabService.checkAvailability(type, color));
    }
}
