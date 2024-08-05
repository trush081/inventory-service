package com.trentonrush.inventoryservice.repositories;

import com.trentonrush.inventoryservice.models.Slab;
import com.trentonrush.inventoryservice.models.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Slab Repository
 *
 * @author Trenton Rush
 * @since 2024-07-28
 */
@Repository
public interface SlabRepository extends MongoRepository<Slab, String> {
    List<Slab> findAllByType(String type);
    List<Slab> findAllByColor(String color);
    List<Slab> findAllByStatus(Status status);
    List<Slab> findAllByTypeAndColor(String type, String color);
    List<Slab> findAllByColorAndStatus(String color, Status status);
    boolean existsByColorAndStatus(String color, Status status);
    List<Slab> findAllByTypeAndStatus(String type,Status status);
    boolean existsByTypeAndStatus(String type, Status status);
    List <Slab> findAllByTypeAndColorAndStatus(String type, String color, Status status);
    boolean existsByTypeAndColorAndStatus(String type, String color, Status status);
}
