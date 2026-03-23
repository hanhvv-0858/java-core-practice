package org.example.Practice3;
import java.util.*;
import java.util.stream.*;

public class Practice3 {
    static TransportManager mgr = new TransportManager();
    static Scanner sc = new Scanner(System.in);
    // Map menu options → action
    static final Map<Integer, Runnable> ACTIONS = new LinkedHashMap<>();

    public static void main(String[] args) {
        addSampleData(); // Thêm 1 số sample data để test
        setupActions(); // khởi tạo map các action

        int choice = -1;
        while (choice != 0) {
            printMenu();
            choice = readInt("Your choice: ");
            final int finalChoice = choice;
            if (finalChoice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            // Stream — tìm action tương ứng
            Optional.ofNullable(ACTIONS.get(finalChoice))
                    .ifPresentOrElse(
                            Runnable::run,  // nếu có → chạy action
                            () -> System.out.println("❌ Invalid choice!")
                    );
        }
    }

    // ── Setup các actions vào Map ─────────────────────────────
    static void setupActions() {
        ACTIONS.put(1, mgr::addVehicleFromInput);
        ACTIONS.put(2, () -> {
            System.out.print("Enter vehicle number: ");
            mgr.searchByVehicleNo(sc.nextLine().trim());
        });
        ACTIONS.put(3, () -> {
            System.out.print("Enter CMND: ");
            mgr.searchByCmnd(sc.nextLine().trim());
        });
        ACTIONS.put(4, () -> {
            System.out.print("Manufacturer (Honda/Yamaha/Toyota/Suzuki): ");
            try {
                mgr.deleteByManufacturer(
                        Manufacturer.fromString(sc.nextLine().trim()));
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        });
        ACTIONS.put(5, mgr::showTopManufacturer);
        ACTIONS.put(6, mgr::sortByManufacturerCount);
        ACTIONS.put(7, mgr::displayStatistics);
        ACTIONS.put(8, mgr::displayAll);
    }

    // ── In menu tự động từ Map ────────────────────────────────
    static final Map<Integer, String> MENU_ITEMS = new LinkedHashMap<>();
    static {
        MENU_ITEMS.put(1, "Add vehicle");
        MENU_ITEMS.put(2, "Search by vehicle number");
        MENU_ITEMS.put(3, "Search by owner CMND");
        MENU_ITEMS.put(4, "Delete by manufacturer (all vehicles)");
        MENU_ITEMS.put(5, "Top manufacturer");
        MENU_ITEMS.put(6, "Sort by manufacturer count");
        MENU_ITEMS.put(7, "Statistics by vehicle type");
        MENU_ITEMS.put(8, "View all vehicles");
        MENU_ITEMS.put(0, "Exit");
    }

    static void printMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║     TRANSPORT MANAGEMENT         ║");
        System.out.println("╠══════════════════════════════════╣");

        // Stream duyệt và in menu items
        MENU_ITEMS.entrySet().stream()
                .map(e -> String.format("║  %d. %-30s║", e.getKey(), e.getValue()))
                .forEach(System.out::println);

        System.out.println("╚══════════════════════════════════╝");
    }

    // ── Sample data ───────────────────────────────────────────
    static void addSampleData() {
        // Dùng Stream để tạo và add nhiều owner + vehicle cùng lúc
        System.out.println("==== Sample data start adding ===");
        List<Owner> owners = List.of(
                new Owner("123456789012", "Nguyen Van An",  "an@gmail.com"),
                new Owner("987654321098", "Tran Thi Bich",  "bich@gmail.com"),
                new Owner("111222333444", "Le Van Cuong",   "cuong@gmail.com")
        );

        List<Vehicle> samples = List.of(
                new Car     ("AB123", Manufacturer.TOYOTA, 2022, "White",  owners.get(0), 4, "Petrol"),
                new Car     ("XY456", Manufacturer.HONDA,  2021, "Black",  owners.get(1), 7, "Diesel"),
                new Motorbike("MK789", Manufacturer.YAMAHA, 2023, "Red",   owners.get(0), 150),
                new Motorbike("HN001", Manufacturer.HONDA,  2020, "Blue",  owners.get(2), 110),
                new Truck   ("TK999", Manufacturer.SUZUKI, 2019, "Gray",  owners.get(1), 5.0),
                new Car     ("TC321", Manufacturer.HONDA,  2022, "Silver", owners.get(2), 5, "Hybrid")
        );

        // Stream add tất cả vào manager
        samples.forEach(mgr::addVehicle);

        System.out.println("✅ Sample data loaded.\n");
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println("❌ Enter a number!"); }
        }
    }
}
