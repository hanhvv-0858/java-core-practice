package org.example.Practice2;

import java.time.LocalDate;

public class Practice2 {
    static Inventory inv = new Inventory(100);    // tối đa 100 sản phẩm

    public static void main(String[] args) {
        // Thêm sẵn vài sản phẩm mẫu
        addSampleData();

        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = readChoice();

            switch (choice) {
                case 1 -> inv.addProductFromInput(); // ← gọi method của Inventory
                case 2 -> inv.displayAll();
                case 3 -> inv.displayStatistics();
                case 4 -> inv.displayLowPerformingProducts();
                case 0 -> System.out.println("Goodbye! see you next time!");
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }
    // ── In menu ra màn hình ───────────────────────────────────
    static void printMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║   SUPERMARKET INVENTORY      ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Add new product          ║");
        System.out.println("║  2. View all products        ║");
        System.out.println("║  3. View statistics          ║");
        System.out.println("║  4. View problem products    ║");
        System.out.println("║  0. Exit                     ║");
        System.out.println("╚══════════════════════════════╝");
    }

    static int readChoice() {
        System.out.print("Your choice: ");
        try {
            return Integer.parseInt(
                    new java.util.Scanner(System.in).nextLine().trim()
            );
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    // ── Dữ liệu mẫu ──────────────────────────────────────────
    static void addSampleData() {
        inv.addProduct(new Food(
                "F001", "Fresh Milk", 20, 15000,
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 15),
                "Vinamilk"
        ));

        inv.addProduct(new Food(
                "F002", "Expired Bread", 5, 8000,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 5),  // đã hết hạn
                "ABC Bakery"
        ));

        inv.addProduct(new Electronics(
                "E001", "Samsung TV 55\"", 2, 15000000, 24, 0.1
        )); // qty=2 → selling well

        inv.addProduct(new Electronics(
                "E002", "LG Washing Machine", 5, 8000000, 12, 0.5
        ));

        inv.addProduct(new Crockery(
                "C001", "Ceramic Bowl Set", 60, 250000,
                "Minh Long",
                LocalDate.now().minusDays(15)  // nhập 15 ngày trước
        )); // qty=60 > 50, storage=15 > 10 → slow sale

    }
}
