package org.example.Practice1;

public class Rectangle extends Shape {

    // Constructor — gọi super() để khởi tạo width, height từ Shape
    public Rectangle(double width, double height) {
        super(width, height); // BẮT BUỘC gọi constructor cha. ← Giao cho Shape tự khởi tạo
        // Từ đây width và height đã có giá trị
    }

    // Diện tích = width * height
    public double getArea() {
        return width * height;
    }

    // Chu vi = 2 * (width + height)
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "Rectangle [width=" + width
                + ", height=" + height
                + ", area=" + getArea()
                + ", perimeter=" + getPerimeter() + "]";
    }
}
