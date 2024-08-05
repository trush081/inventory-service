package com.trentonrush.inventoryservice.models.dtos;

import com.trentonrush.inventoryservice.models.Dimensions;

public class SlabDTO {

    private String image;
    private String description;
    private Dimensions dimensions;
    private boolean isRemnant;
    private boolean isDamaged;
    private int quantity;
    private String color;
    private String type;
    private String sqftPrice;
    private String location;
    private String supplier;
    private String status;

    public SlabDTO() {
        // empty constructor
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public boolean isRemnant() {
        return isRemnant;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getSqftPrice() {
        return sqftPrice;
    }

    public String getLocation() {
        return location;
    }

    public String getSupplier() {
        return supplier;
    }

    @Override
    public String toString() {
        return "SlabDTO{" +
                "image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", dimensions=" + dimensions +
                ", isRemnant=" + isRemnant +
                ", isDamaged=" + isDamaged +
                ", quantity=" + quantity +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", sqftPrice='" + sqftPrice + '\'' +
                ", location='" + location + '\'' +
                ", supplier='" + supplier + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
