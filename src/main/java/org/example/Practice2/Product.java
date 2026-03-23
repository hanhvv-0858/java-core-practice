package org.example.Practice2;

public abstract class Product {
    // ── Attributes ────────────────────────────────────────────
    protected String productCode;
    protected String name;
    protected int    quantity;   // >= 0
    protected double unitPrice;

    // ── Constructor ───────────────────────────────────────────
    public Product(String productCode, String name, int quantity, double unitPrice) {
        // Validate ngay khi khởi tạo
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity must be >= 0");
        if (unitPrice < 0)
            throw new IllegalArgumentException("Unit price must be >= 0");

        this.productCode = productCode;
        this.name        = name;
        this.quantity    = quantity;
        this.unitPrice   = unitPrice;
    }

    // ── Getters / Setters ─────────────────────────────────────
    public String getProductCode() { return productCode; }
    public String getName()        { return name; }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity must be >= 0");
        this.quantity = quantity;
    }

    // ── Abstract methods — subclass BẮT BUỘC implement ────────
    public abstract double getVAT();            // VAT cho từng loại
    public abstract String evaluateSales();     // Đánh giá tình trạng bán hàng

    // ── Concrete method — dùng chung ──────────────────────────
    public double getVATAmount() {
        return unitPrice * quantity * getVAT();
    }

    public String getInfo() {
        return String.format(
                "Code: %-8s | Name: %-20s | Qty: %3d | Price: %8.2f | VAT: %.0f%%",
                productCode, name, quantity, unitPrice, getVAT() * 100
        );
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
