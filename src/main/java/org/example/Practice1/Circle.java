package org.example.Practice1;
// Circle dùng radius — ta truyền radius vào cả width lẫn height
// (vì Shape yêu cầu 2 tham số, nhưng Circle chỉ cần 1)
public class Circle  extends Shape {

    // Constructor — radius truyền vào cả 2 tham số của Shape
    public Circle(double radius) {
        super(radius, radius); // width = height = radius
    }

    // Getter — lấy radius từ width
    public double getRadius() { return width; }

    // Diện tích = π * r^2
    public double getArea() {
        return Math.PI * width * width; // width chính là radius
        // hoặc: return 3.14 * width * width; nếu đề yêu cầu dùng 3.14
    }

    // Đường kính = 2 * radius
    public double getDiameter() { return 2 * width; }

    // Chu vi = diameter * 3.14
    public double getCircumference() {
        return getDiameter() * 3.14;
    }

    @Override
    public String toString() {
        return "Circle [radius=" + getRadius()
                + ", area=" + String.format("%.2f", getArea())
                + ", circumference=" + String.format("%.2f", getCircumference()) + "]";
    }
}
