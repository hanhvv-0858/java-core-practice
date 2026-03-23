package org.example.Practice2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Crockery extends Product {
    private String    manufacturer; // nhà sản xuất
    private LocalDate arrivalDate;  // ngày nhập kho

    public Crockery(String productCode, String name, int quantity,
                    double unitPrice, String manufacturer,
                    LocalDate arrivalDate) {
        super(productCode, name, quantity, unitPrice);
        this.manufacturer = manufacturer;
        this.arrivalDate  = arrivalDate;
    }

    // Số ngày đã lưu kho
    public long getDaysInStorage() {
        return ChronoUnit.DAYS.between(arrivalDate, LocalDate.now());
    }

    // ── Override abstract methods ─────────────────────────────
    @Override
    public double getVAT() { return 0.10; } // 10%

    @Override
    public String evaluateSales() {
        // Tồn kho > 50 VÀ lưu kho > 10 ngày → bán chậm
        if (quantity > 50 && getDaysInStorage() > 10) {
            return "Slow sale (overstocked)";
        }
        return "Normal";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(
                "\n         Type: Crockery | Manufacturer: %s | Arrival: %s | Days in storage: %d | Status: %s",
                manufacturer, arrivalDate, getDaysInStorage(), evaluateSales()
        );
    }
}
