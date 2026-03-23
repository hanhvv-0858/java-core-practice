package org.example.Practice2;

import java.time.LocalDate;

public class Food  extends Product {
    private LocalDate mfgDate;  // ngày sản xuất
    private LocalDate expDate;  // ngày hết hạn
    private String    supplier; // nhà cung cấp

    public Food(String productCode, String name, int quantity,
                double unitPrice, LocalDate mfgDate,
                LocalDate expDate, String supplier) {
        super(productCode, name, quantity, unitPrice);

        // Validate: ngày hết hạn phải SAU ngày sản xuất
        if (!expDate.isAfter(mfgDate))
            throw new IllegalArgumentException(
                    "Expiry date must be after manufacture date!");

        this.mfgDate  = mfgDate;
        this.expDate  = expDate;
        this.supplier = supplier;
    }

    // Kiểm tra còn hạn không
    public boolean isExpired() {
        return LocalDate.now().isAfter(expDate);
    }

    // ── Override abstract methods ─────────────────────────────
    @Override
    public double getVAT() { return 0.05; } // 5%

    @Override
    public String evaluateSales() {
        // Còn hàng VÀ đã hết hạn → khó bán
        if (quantity > 0 && isExpired()) {
            return "Hard to sell (expired)";
        }
        return "Normal";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + String.format(
                "\n         Type: Food | MfgDate: %s | ExpDate: %s | Supplier: %s | Status: %s",
                mfgDate, expDate, supplier, evaluateSales()
        );
    }
}
