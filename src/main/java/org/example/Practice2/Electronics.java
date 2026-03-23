package org.example.Practice2;

public class Electronics  extends Product {
    private int    warrantyMonths; // >= 0
    private double capacityKW;    // >= 0

    public Electronics(String productCode, String name, int quantity,
                       double unitPrice, int warrantyMonths,
                       double capacityKW) {
        super(productCode, name, quantity, unitPrice);

        if (warrantyMonths < 0)
            throw new IllegalArgumentException("Warranty must be >= 0");
        if (capacityKW < 0)
            throw new IllegalArgumentException("Capacity must be >= 0");

        this.warrantyMonths = warrantyMonths;
        this.capacityKW     = capacityKW;
    }

    // ── Override abstract methods ─────────────────────────────
    @Override
    public double getVAT() { return 0.10; } // 10%

    @Override
    public String evaluateSales() {
        // Tồn kho < 3 → đã bán được (selling well)
        if (quantity < 3) {
            return "Selling well (low stock)";
        }
        return "Normal";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(
                "\n         Type: Electronics | Warranty: %d months | Capacity: %.1f KW | Status: %s",
                warrantyMonths, capacityKW, evaluateSales()
        );
    }
}
