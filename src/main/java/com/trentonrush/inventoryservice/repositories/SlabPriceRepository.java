package com.trentonrush.inventoryservice.repositories;

import com.trentonrush.inventoryservice.models.SlabPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Slab Price Repository
 *
 * @author Trenton Rush
 * @since 2024-07-28
 */
@Repository
public interface SlabPriceRepository extends MongoRepository<SlabPrice, String> {
    Optional<SlabPrice> findByTypeAndColor(String type, String color);
}
