package game;

import java.awt.*;

public class Wall {
    public static final int XADD = 250; // X-coordinate offset
    public static final int YADD = 150; // Y-coordinate offset
    public static final int DUnits = 20; // Unit distance
    
    public double myX; // X-coordinate of the wall
    public double myY; // Y-coordinate of the wall
    public double myXW; // Width of the wall
    public double myYW; // Height of the wall

    // Default constructor
    public Wall() {
        myX = 0;
        myY = 0;
        myXW = 0;
        myYW = 0;
    }

    // Constructor with parameters
    public Wall(double x, double y, double xWidth, double yWidth) {
        myX = x;
        myY = y;
        myXW = xWidth;
        myYW = yWidth;
    }

    // Accessor methods (one for each field)
    public double getX() { 
        return myX;
    }
    public double getY() { 
        return myY;
    }
    public double getXWidth() { 
        return myXW;
    }
    public double getYWidth() { 
        return myYW;
    }

    // Modifier methods
    public void setX(double x) {
        myX = x;
    } 
    public void setY(double y) {
        myY = y;
    } 
    public void setXWidth(double x) {
        myXW = x;
    } 
    public void setYWidth(double y) {
        myYW = y;
    } 

    // Method to draw the wall
    public void draw(Graphics myBuffer) {
        myBuffer.setColor(Color.BLUE); // Set the color to blue
        myBuffer.fillRoundRect((int)(XADD + myX * DUnits - 2), (int)(YADD + myY * DUnits - 2), 
                               (int)(myXW * DUnits - DUnits + 5), (int)(myYW * DUnits - DUnits + 5), 
                               30, 30); // Draw a rounded rectangle
    }   
}
