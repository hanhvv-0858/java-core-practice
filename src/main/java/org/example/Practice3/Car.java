package org.example.Practice3;

public class Car extends Vehicle {
    private int    numSeats;
    private String engineType;

    public Car(String vehicleNo, Manufacturer manufacturer,
               int yearMfg, String color, Owner owner,
               int numSeats, String engineType) {
        super(vehicleNo, manufacturer, yearMfg, color, owner);
        if (numSeats <= 0)
            throw new IllegalArgumentException("Seats must be > 0");
        this.numSeats   = numSeats;
        this.engineType = engineType;
    }

    public int    getNumSeats()   { return numSeats; }
    public String getEngineType() { return engineType; }

    @Override
    public String getType() { return "Car"; }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(
                " | Seats: %d | Engine: %s", numSeats, engineType);
    }
}
