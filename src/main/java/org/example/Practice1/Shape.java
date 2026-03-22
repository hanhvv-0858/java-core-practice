package org.example.Practice1;

// Shape.java
public class Shape {

    // Properties — dùng protected để subclass truy cập được trực tiếp
    protected double width;
    protected double height;

    // Constructor
    public Shape(double width, double height) {
        this.width  = width;
        this.height = height;
    }

    // Getters
    public double getWidth()  { return width; }
    public double getHeight() { return height; }

    // toString — in thông tin cơ bản
    @Override
    public String toString() {
        return "Shape [width=" + width + ", height=" + height + "]";
    }
}
