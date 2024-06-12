package game;

import java.awt.Color;
import java.awt.Graphics;

public class Door extends Wall {

    public static final int XADD = 250; // Offset for X coordinate
    public static final int YADD = 150; // Offset for Y coordinate
    public static final int DUnits = 20; // Unit distance

    public double myX; // X coordinate of the door
    public double myY; // Y coordinate of the door
    public double myXW; // Width of the door
    public double myYW; // Height of the door

    // Default constructor
    public Door() {
        super(12.5, 12, 3, 1);
        myX = 12.5;
        myY = 12;
        myXW = 3;
        myYW = 1;
    }

    // Constructor with parameters
    public Door(int x, int y, int xWidth, int yWidth) {
        super(x, y, xWidth, yWidth);
        myX = x;
        myY = y;
        myXW = xWidth;
        myYW = yWidth;
    }

    // Method to draw the door
    public void draw(Graphics myBuffer) {
        myBuffer.setColor(Color.pink); // Set color to pink
        myBuffer.fillRect(
            (int)(XADD + myX * DUnits - 2),
            (int)(YADD + myY * DUnits - 2),
            (int)(myXW * DUnits - DUnits + 5),
            (int)(myYW * DUnits - DUnits + 5)
        );
    }
}
