package org.example.Practice3;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

public class TransportManager {
    private int       oCount; // số owner hiện tại

    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Owner>   owners   = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    // ════════════════════════════════════════════════════════
    // TASK 1: Thêm phương tiện
    // ════════════════════════════════════════════════════════
    public void addVehicle(Vehicle v) {
        // Thêm owner nếu chưa có
        boolean duplicate = vehicles.stream()
                .anyMatch(x -> x.getVehicleNo()
                        .equalsIgnoreCase(v.getVehicleNo()));
        if (duplicate) {
            System.out.println("❌ Vehicle No '" + v.getVehicleNo()
                    + "' already exists!");
            return;
        }
        addOwnerIfNew(v.getOwner());
        vehicles.add(v);
        System.out.println("✅ Added: " + v.getVehicleNo());
    }

    private void addOwnerIfNew(Owner o) {
        boolean exists = owners.stream()
                .anyMatch(x -> x.getCmnd().equals(o.getCmnd()));
        if (!exists) owners.add(o);
    }

    // ════════════════════════════════════════════════════════
    // TASK 2: Tìm xe theo biển số
    // ════════════════════════════════════════════════════════
    public void searchByVehicleNo(String no) {
        vehicles.stream()
                .filter(v -> v.getVehicleNo().equalsIgnoreCase(no))
                .findFirst()
                .ifPresentOrElse(
                        v -> {
                            System.out.println("✅ Found:");
                            System.out.println("   " + v.getInfo());
                            System.out.println("   Owner: " + v.getOwner().getInfo());
                        },
                        () -> System.out.println("❌ Not found: " + no)
                );
    }

    // ════════════════════════════════════════════════════════
    // TASK 3: Tìm xe theo CMND chủ xe
    // ════════════════════════════════════════════════════════
    public void searchByCmnd(String cmnd) {
        List<Vehicle> result = vehicles.stream()
                .filter(v -> v.getOwner().getCmnd().equals(cmnd))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("❌ No vehicles for CMND: " + cmnd);
            return;
        }
        System.out.println("✅ " + result.size() + " vehicle(s):");
        result.forEach(v -> System.out.println("  → " + v.getInfo()));
    }

    // ════════════════════════════════════════════════════════
    // TASK 4: Xóa tất cả xe của 1 hãng
    // ════════════════════════════════════════════════════════
    public void deleteByManufacturer(Manufacturer mfr) {
        long before = vehicles.size();
        vehicles = vehicles.stream()
                .filter(v -> v.getManufacturer() != mfr)
                .collect(Collectors.toList());
        System.out.println("✅ Removed " + (before - vehicles.size())
                + " vehicle(s) of " + mfr);
    }

    // ════════════════════════════════════════════════════════
    // TASK 5: Hãng có nhiều xe nhất
    // ════════════════════════════════════════════════════════
    public void showTopManufacturer() {
        if (vehicles.isEmpty()) { System.out.println("No vehicles."); return; }

        Map<Manufacturer, Long> counts = vehicles.stream()
                .collect(Collectors.groupingBy(
                        Vehicle::getManufacturer, Collectors.counting()));

        Manufacturer top = counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);

        System.out.println("\n--- Manufacturer Count ---");
        counts.forEach((m, c) ->
                System.out.printf("  %-8s : %d%s%n",
                        m, c, m == top ? " ← MOST" : ""));
    }

    // ════════════════════════════════════════════════════════
    // TASK 6: Sắp xếp xe theo số lượng của hãng (DESC)
    // ════════════════════════════════════════════════════════
    public void sortByManufacturerCount() {
        Map<Manufacturer, Long> counts = vehicles.stream()
                .collect(Collectors.groupingBy(
                        Vehicle::getManufacturer, Collectors.counting()));

        vehicles.stream()
                .sorted(Comparator.comparingLong(
                        (Vehicle v) -> counts.getOrDefault(
                                v.getManufacturer(), 0L)).reversed())
                .forEach(v -> System.out.printf("  [%s|%d] %s%n",
                        v.getManufacturer(),
                        counts.get(v.getManufacturer()),
                        v.getInfo()));
    }
    // ════════════════════════════════════════════════════════
    // TASK 7: Thống kê từng loại xe
    // ════════════════════════════════════════════════════════
    public void displayStatistics() {
        Map<String, Long> byType = vehicles.stream()
                .collect(Collectors.groupingBy(
                        Vehicle::getType, Collectors.counting()));

        System.out.println("\n====== Statistics ======");
        byType.forEach((type, count) ->
                System.out.printf("  %-12s : %d%n", type, count));
        System.out.printf("  TOTAL        : %d%n", vehicles.size());
    }

    // ════════════════════════════════════════════════════════
    // Hiển thị danh sách
    // ════════════════════════════════════════════════════════
    public void displayAll() {
        if (vehicles.isEmpty()) { System.out.println("No vehicles."); return; }
        System.out.println("\n====== All Vehicles ======");
        vehicles.forEach(v -> {
            System.out.println("  " + v.getInfo());
            System.out.println("  Owner: " + v.getOwner().getInfo());
            System.out.println();
        });
    }

    // ════════════════════════════════════════════════════════
    // Nhập xe từ bàn phím
    // ════════════════════════════════════════════════════════
    public void addVehicleFromInput() {
        System.out.println("\n--- Choose vehicle type ---");
        System.out.println("  1. Car");
        System.out.println("  2. Motorbike");
        System.out.println("  3. Truck");
        System.out.println("  0. Cancel");

        int type = readInt("Choice: ");
        if (type == 0) return;

        String       no   = readVehicleNo("Vehicle number (5 chars): ");
        Manufacturer mfr  = readManufacturer(
                "Manufacturer (Honda/Yamaha/Toyota/Suzuki): ");
        int          year = readYear("Year of manufacture: ");
        String       col  = readString("Color: ");
        Owner        own  = readOwner();

        try {
            switch (type) {
                case 1 -> {
                    int    seats  = readPositiveInt("Number of seats: ");
                    String engine = readString("Engine type: ");
                    addVehicle(new Car(no, mfr, year, col, own,
                            seats, engine));
                }
                case 2 -> {
                    double cap = readPositiveDouble("Capacity (cc): ");
                    addVehicle(new Motorbike(no, mfr, year, col, own, cap));
                }
                case 3 -> {
                    double ton = readPositiveDouble("Tonnage (tons): ");
                    addVehicle(new Truck(no, mfr, year, col, own, ton));
                }
                default -> System.out.println("❌ Invalid type!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private Owner readOwner() {
        System.out.println("  -- Owner Info --");
        while (true) {
            try {
                String cmnd = readString("  CMND (12 digits): ");

                // Nếu CMND đã tồn tại → dùng lại owner cũ
                Optional<Owner> existing = owners.stream()
                        .filter(o -> o.getCmnd().equals(cmnd))
                        .findFirst();

                if (existing.isPresent()) {
                    System.out.println("  ℹ️  Using existing owner: "
                            + existing.get().getFullName());
                    return existing.get();
                }

                String name  = readString("  Full name: ");
                String email = readString("  Email: ");
                return new Owner(cmnd, name, email);

            } catch (IllegalArgumentException e) {
                System.out.println("  ❌ " + e.getMessage());
            }
        }
    }

    // ── Helper methods ─────────────────────────────────────

    private String readString(String prompt) {
        String v = "";
        while (v.isBlank()) {
            System.out.print(prompt);
            v = sc.nextLine().trim();
            if (v.isBlank()) System.out.println("❌ Cannot be empty!");
        }
        return v;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) {
                System.out.println("❌ Enter a valid number!"); }
        }
    }

    private int readPositiveInt(String prompt) {
        while (true) {
            int v = readInt(prompt);
            if (v > 0) return v;
            System.out.println("❌ Must be > 0!");
        }
    }

    private double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v > 0) return v;
                System.out.println("❌ Must be > 0!");
            } catch (NumberFormatException e) {
                System.out.println("❌ Enter a valid number!");
            }
        }
    }

    private String readVehicleNo(String prompt) {
        while (true) {
            String v = readString(prompt).toUpperCase();
            if (v.length() == 5) return v;
            System.out.println("❌ Must be exactly 5 characters!");
        }
    }

    private Manufacturer readManufacturer(String prompt) {
        while (true) {
            try {
                return Manufacturer.fromString(readString(prompt));
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private int readYear(String prompt) {
        int current = Year.now().getValue();
        while (true) {
            int y = readInt(prompt);
            if (y > 2000 && y <= current) return y;
            System.out.println("❌ Year must be > 2000 and <= " + current);
        }
    }
}
