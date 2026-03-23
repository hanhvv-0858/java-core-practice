package org.example.Practice3;

public class Motorbike extends Vehicle {
    private double capacity; // cc

    public Motorbike(String vehicleNo, Manufacturer manufacturer,
                     int yearMfg, String color, Owner owner,
                     double capacity) {
        super(vehicleNo, manufacturer, yearMfg, color, owner);
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
    }
    // ── Getters / Setters ─────────────────────────────────────
    public double getCapacity() { return capacity; }

    @Override
    public String getType() { return "Motorbike"; }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Capacity: %.0fcc", capacity);
    }
}
