package com.trentonrush.inventoryservice.models;

public class Dimensions {

    private Measurement length; // feet & inches
    private Measurement width; // feet & inches
    private Measurement thickness; // cm

    public Dimensions() {
        // empty constructor
    }

    public Dimensions(Measurement length, Measurement thickness, Measurement width) {
        this.length = length;
        this.thickness = thickness;
        this.width = width;
    }

    public Measurement getLength() {
        return length;
    }

    public void setLength(Measurement length) {
        this.length = length;
    }

    public Measurement getThickness() {
        return thickness;
    }

    public void setThickness(Measurement thickness) {
        this.thickness = thickness;
    }

    public Measurement getWidth() {
        return width;
    }

    public void setWidth(Measurement width) {
        this.width = width;
    }
}
