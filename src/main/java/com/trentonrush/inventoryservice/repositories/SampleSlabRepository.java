package com.trentonrush.inventoryservice.repositories;

import com.trentonrush.inventoryservice.models.SampleSlab;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Sample Slab Repository
 *
 * @author Trenton Rush
 * @since 2024-07-28
 */
@Repository
public interface SampleSlabRepository extends MongoRepository<SampleSlab, String> {
    List<SampleSlab> findAllByType(String type);
    List<SampleSlab> findAllByColor(String color);
    List<SampleSlab> findAllByQuantityGreaterThan(int quantity);
    List<SampleSlab> findAllByTypeAndColor(String type, String color);
    boolean existsByTypeAndColor(String type, String color);
    List<SampleSlab> findAllByColorAndQuantityGreaterThan(String color, int quantity);
    boolean existsByColorAndQuantityGreaterThan(String color, int quantity);
    List<SampleSlab> findAllByTypeAndQuantityGreaterThan(String type, int quantity);
    boolean existsByTypeAndQuantityGreaterThan(String type, int quantity);
    List <SampleSlab> findAllByTypeAndColorAndQuantityGreaterThan(String type, String color, int quantity);
    boolean existsByTypeAndColorAndQuantityGreaterThan(String type, String color, int quantity);
}
