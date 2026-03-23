package org.example.Practice2;// Inventory.java
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Inventory {

    private Product[] products;
    private int       count;
    private int       capacity;
    private Scanner   sc = new Scanner(System.in); // Scanner nằm trong Inventory

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.products = new Product[capacity];
        this.count    = 0;
    }

    // ── Kiểm tra trùng mã ────────────────────────────────────
    private boolean isDuplicateCode(String code) {
        for (int i = 0; i < count; i++) {
            if (products[i].getProductCode().equalsIgnoreCase(code))
                return true;
        }
        return false;
    }

    // ── Thêm trực tiếp bằng object ───────────────────────────
    public boolean addProduct(Product p) {
        if (count >= capacity) {
            System.out.println("❌ Inventory is full!");
            return false;
        }
        if (isDuplicateCode(p.getProductCode())) {
            System.out.println("❌ Code '" + p.getProductCode()
                    + "' already exists!");
            return false;
        }
        products[count++] = p;
        System.out.println("✅ Added: " + p.getName());
        return true;
    }

    // ── Thêm bằng cách nhập từ bàn phím ──────────────────────
    public void addProductFromInput() {
        System.out.println("\n--- Choose product type ---");
        System.out.println("  1. Food");
        System.out.println("  2. Electronics");
        System.out.println("  3. Crockery");
        System.out.println("  0. Cancel");

        int type = readInt("Your choice: ");

        switch (type) {
            case 1 -> addFood();
            case 2 -> addElectronics();
            case 3 -> addCrockery();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("❌ Invalid type!");
        }
    }

    // ── Nhập Food ─────────────────────────────────────────────
    private void addFood() {
        System.out.println("\n--- Add Food ---");
        String code  = readString("Product code : ");
        String name  = readString("Name         : ");
        int    qty   = readNonNegativeInt("Quantity     : ");
        double price = readNonNegativeDouble("Unit price   : ");

        LocalDate mfgDate = readDate("Manufacture date (yyyy-MM-dd): ");
        LocalDate expDate;
        while (true) {
            expDate = readDate("Expiry date     (yyyy-MM-dd): ");
            if (expDate.isAfter(mfgDate)) break;
            System.out.println("❌ Expiry must be AFTER manufacture date!");
        }

        String supplier = readString("Supplier: ");

        addProduct(new Food(code, name, qty, price,
                mfgDate, expDate, supplier));
    }

    // ── Nhập Electronics ──────────────────────────────────────
    private void addElectronics() {
        System.out.println("\n--- Add Electronics ---");
        String code  = readString("Product code     : ");
        String name  = readString("Name             : ");
        int    qty   = readNonNegativeInt("Quantity         : ");
        double price = readNonNegativeDouble("Unit price       : ");
        int    war   = readNonNegativeInt("Warranty (months): ");
        double kw    = readNonNegativeDouble("Capacity (KW)    : ");

        addProduct(new Electronics(code, name, qty, price, war, kw));
    }

    // ── Nhập Crockery ─────────────────────────────────────────
    private void addCrockery() {
        System.out.println("\n--- Add Crockery ---");
        String    code    = readString("Product code : ");
        String    name    = readString("Name         : ");
        int       qty     = readNonNegativeInt("Quantity     : ");
        double    price   = readNonNegativeDouble("Unit price   : ");
        String    mfr     = readString("Manufacturer : ");
        LocalDate arrival = readDate("Arrival date (yyyy-MM-dd): ");

        addProduct(new Crockery(code, name, qty, price, mfr, arrival));
    }

    // ── Hiển thị danh sách ────────────────────────────────────
    public void displayAll() {
        if (count == 0) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("\n====== Inventory List ======");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + products[i].getInfo());
            System.out.println();
        }
    }

    // ── Thống kê ──────────────────────────────────────────────
    public void displayStatistics() {
        int foodCnt = 0, elecCnt = 0, crockCnt = 0;
        double foodVAT = 0, elecVAT = 0, crockVAT = 0;

        for (int i = 0; i < count; i++) {
            Product p = products[i];
            if (p instanceof Food) {
                foodCnt++;  foodVAT  += p.getVATAmount();
            } else if (p instanceof Electronics) {
                elecCnt++;  elecVAT  += p.getVATAmount();
            } else if (p instanceof Crockery) {
                crockCnt++; crockVAT += p.getVATAmount();
            }
        }

        System.out.println("\n====== Statistics ======");
        System.out.printf("Food        : %3d items | VAT: %10.2f%n",
                foodCnt,  foodVAT);
        System.out.printf("Electronics : %3d items | VAT: %10.2f%n",
                elecCnt,  elecVAT);
        System.out.printf("Crockery    : %3d items | VAT: %10.2f%n",
                crockCnt, crockVAT);
        System.out.printf("─────────────────────────────────%n");
        System.out.printf("TOTAL       : %3d items | VAT: %10.2f%n",
                count, foodVAT + elecVAT + crockVAT);
    }

    public void displayLowPerformingProducts() {
        System.out.println("\n====== Products Needing Attention ======");
        boolean found = false;

        for (int i = 0; i < count; i++) {
            String status = products[i].evaluateSales(); // gọi qua polymorphism
            if (!status.equals("Normal")) {
                System.out.println("⚠️  " + products[i].getInfo());
                System.out.println("    → Status: " + status);
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("✅ All products are performing normally.");
        }
    }

    // ── Helper methods (private — chỉ dùng trong Inventory) ──
    private String readString(String prompt) {
        String value = "";
        while (value.isBlank()) {
            System.out.print(prompt);
            value = sc.nextLine().trim();
            if (value.isBlank()) System.out.println("❌ Cannot be empty!");
        }
        return value;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    private int readNonNegativeInt(String prompt) {
        while (true) {
            int v = readInt(prompt);
            if (v >= 0) return v;
            System.out.println("❌ Must be >= 0!");
        }
    }

    private double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= 0) return v;
                System.out.println("❌ Must be >= 0!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("❌ Format: yyyy-MM-dd (e.g. 2025-03-15)");
            }
        }
    }

}