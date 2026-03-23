package org.example.Practice3;

public enum Manufacturer {
    HONDA, YAMAHA, TOYOTA, SUZUKI;

    // Parse từ String — không phân biệt hoa thường
    public static Manufacturer fromString(String s) {
        try {
            return valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Manufacturer must be: Honda, Yamaha, Toyota, Suzuki");
        }
    }
}