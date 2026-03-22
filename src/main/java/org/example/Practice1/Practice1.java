package org.example.Practice1;

import java.util.ArrayList;
import java.util.List;

public class Practice1 {
    public static void main(String[] args) {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Shape(5, 3));
        shapes.add(new Rectangle(6, 4));
        shapes.add(new Circle(7));
        shapes.add(new Triangle(4, 3, 3, 4, 5));
        shapes.add(new Square(5));

        System.out.println("====== All Shapes ======");
        for (Shape s : shapes) {
            System.out.println(s); // gọi đúng toString() của từng class
        }
    }
}

