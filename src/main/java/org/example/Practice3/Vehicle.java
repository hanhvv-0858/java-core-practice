package org.example.Practice3;

import java.time.Year;

public abstract class Vehicle {
    protected String       vehicleNo;    // 5 ký tự, unique
    protected Manufacturer manufacturer;
    protected int          yearMfg;      // > 2000, <= năm hiện tại
    protected String       color;
    protected Owner        owner;

    public Vehicle(String vehicleNo, Manufacturer manufacturer,
                   int yearMfg, String color, Owner owner) {
        // Validate vehicleNo: đúng 5 ký tự
        if (vehicleNo == null || vehicleNo.length() != 5)
            throw new IllegalArgumentException(
                    "Vehicle number must be exactly 5 characters!");

        // Validate năm sản xuất
        int currentYear = Year.now().getValue();
        if (yearMfg <= 2000 || yearMfg > currentYear)
            throw new IllegalArgumentException(
                    "Year must be > 2000 and <= " + currentYear);

        this.vehicleNo    = vehicleNo.toUpperCase();
        this.manufacturer = manufacturer;
        this.yearMfg      = yearMfg;
        this.color        = color;
        this.owner        = owner;
    }

    // Getters
    public String       getVehicleNo()    { return vehicleNo; }
    public Manufacturer getManufacturer() { return manufacturer; }
    public int          getYearMfg()      { return yearMfg; }
    public String       getColor()        { return color; }
    public Owner        getOwner()        { return owner; }

    // Tên loại xe — subclass override
    public abstract String getType();

    public String getInfo() {
        return String.format(
                "No: %-6s | Type: %-10s | Brand: %-8s | Year: %d | Color: %-10s",
                vehicleNo, getType(), manufacturer, yearMfg, color
        );
    }

    @Override
    public String toString() { return getInfo(); }
}
