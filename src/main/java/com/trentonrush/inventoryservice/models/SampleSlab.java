package com.trentonrush.inventoryservice.models;

import com.trentonrush.inventoryservice.models.dtos.SlabDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "sample_slabs")
public class SampleSlab {

    @Id
    private String id;
    private String image;
    private String color;
    private String type;
    private int quantity;
    private String supplier;
    private Instant creationDate;
    private Instant modificationDate;

    public SampleSlab() {
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Build SampleSlab from DTO
     * @param slabDTO object containing sample details
     * @return new SampleSlab
     */
    public static SampleSlab build(SlabDTO slabDTO) {
        SampleSlab sampleSlab = new SampleSlab();
        sampleSlab.image = slabDTO.getImage();
        sampleSlab.color = slabDTO.getColor();
        sampleSlab.type = slabDTO.getType();
        sampleSlab.quantity = Math.max(slabDTO.getQuantity(), 0);
        sampleSlab.supplier = slabDTO.getSupplier();
        return sampleSlab;
    }

    @Override
    public String toString() {
        return "SampleSlab{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", supplier='" + supplier + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
