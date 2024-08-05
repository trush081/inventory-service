package com.trentonrush.inventoryservice.models;

import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import com.trentonrush.inventoryservice.models.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "slabs")
public class Slab {

    @Id
    private String id;
    private String image;
    private String description;
    private Dimensions dimensions;
    private boolean isRemnant;
    private boolean isDamaged;
    private String color;
    private String type;
    private String location;
    private String supplier;
    private Instant creationDate;
    private Instant modificationDate;
    private Status status;

    public Slab() {
        // empty constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public boolean isRemnant() {
        return isRemnant;
    }

    public void setRemnant(boolean remnant) {
        isRemnant = remnant;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Build Slab from DTO
     * @param slabDTO object containing slab details
     * @return new Slab
     */
    public static Slab build(SlabDTO slabDTO) {
        Slab slab = new Slab();
        slab.image = slabDTO.getImage();
        slab.description = slabDTO.getDescription();
        slab.dimensions = slabDTO.getDimensions();
        slab.isRemnant = slabDTO.isRemnant();
        slab.isDamaged = slabDTO.isDamaged();
        slab.color = slabDTO.getColor();
        slab.type = slabDTO.getType();
        slab.location = slabDTO.getLocation();
        slab.supplier = slabDTO.getSupplier();
        if (null != slabDTO.getStatus()) {
            slab.status = Status.fromString(slabDTO.getStatus());
        }
        return slab;
    }

    @Override
    public String toString() {
        return "Slab{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", dimensions=" + dimensions +
                ", isRemnant=" + isRemnant +
                ", isDamaged=" + isDamaged +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", supplier='" + supplier + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", status=" + status +
                '}';
    }
}
