package com.trentonrush.inventoryservice.models;

public class Measurement {
    private double feet;
    private double inches;
    private double centimeters;

    // Favors feet/inches over cm
    public Measurement(double feet, double inches, double centimeters) {
        if (feet != 0.0 && inches != 0.0 && centimeters != 0.0) {
            centimeters = 0.0;
        }
        this.feet = feet;
        this.inches = inches;
        this.centimeters = centimeters;
    }

    public double getFeet() {
        return feet;
    }

    public void setFeet(double feet) {
        this.feet = feet;
    }

    public double getInches() {
        return inches;
    }

    public void setInches(double inches) {
        this.inches = inches;
    }

    public double getCentimeters() {
        return centimeters;
    }

    public void setCentimeters(double centimeters) {
        this.centimeters = centimeters;
    }

    public double getTotalInFeet() {
        if (feet != 0.0 && centimeters != 0.0) {
            return this.feet + this.inches / 12;
        }
        return this.centimeters / 30.48;
    }

    public double getTotalInInches() {
        if (feet != 0.0 && centimeters != 0.0) {
            return this.inches + this.feet * 12;
        }
        return this.centimeters / 2.54;
    }

    public double getTotalInCentimeters() {
        if (this.centimeters != 0.0) {
            return this.centimeters;
        }
        return (this.inches + this.feet * 12) * 2.54;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "feet=" + feet +
                ", inches=" + inches +
                ", centimeters=" + centimeters +
                '}';
    }
}
