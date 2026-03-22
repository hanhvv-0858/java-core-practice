package org.example.Practice1;

public class Triangle extends Shape {
    private double base;
    private double sideA;
    private double sideB;
    private double sideC; // 3 cạnh để tính chu vi

    public Triangle(double base, double height,
                    double sideA, double sideB, double sideC) {
        super(base, height);
        this.base  = base;
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    // Diện tích = 1/2 * base * height
    public double getArea() {
        return 0.5 * base * height;
    }

    // Chu vi = tổng 3 cạnh
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    public String toString() {
        return "Triangle [base=" + base + ", height=" + height
                + ", area=" + getArea()
                + ", perimeter=" + getPerimeter() + "]";
    }
}
