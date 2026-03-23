package org.example.Practice3;

public class Truck extends Vehicle {
    private double tonnage; // tấn

    public Truck(String vehicleNo, Manufacturer manufacturer,
                 int yearMfg, String color, Owner owner,
                 double tonnage) {
        super(vehicleNo, manufacturer, yearMfg, color, owner);
        if (tonnage <= 0)
            throw new IllegalArgumentException("Tonnage must be > 0");
        this.tonnage = tonnage;
    }

    public double getTonnage() { return tonnage; }

    @Override
    public String getType() { return "Truck"; }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(" | Tonnage: %.1f tons", tonnage);
    }
}
