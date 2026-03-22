package org.example.Practice1;
// Square.java — hình vuông là trường hợp đặc biệt của Rectangle
public class Square extends Rectangle {
    public Square(double side) {
        super(side, side); // width = height = side
    }

    public double getSide() { return width; }

    @Override
    public String toString() {
        return "Square [side=" + getSide()
                + ", area=" + getArea()
                + ", perimeter=" + getPerimeter() + "]";
    }
}
