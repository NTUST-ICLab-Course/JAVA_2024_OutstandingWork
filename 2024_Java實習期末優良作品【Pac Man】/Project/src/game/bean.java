package game;

import java.awt.*;

public class bean {
    public static final int XADD = 250; // Offset for X coordinate
    public static final int YADD = 150; // Offset for Y coordinate
    public static final int DUnits = 20; // Unit distance
    public int myX; // X coordinate of the bean
    public int myY; // Y coordinate of the bean
    public boolean bigbean; // Whether it is a big bean
    public boolean show; // Whether to show the bean

    // Default constructor
    public bean() {
        myX = 200;
        myY = 200;
    }
    
    // Constructor with parameters
    public bean(int x, int y, boolean big, boolean S) {
        myX = x;
        myY = y;
        bigbean = big;
        show = S;
    }
    
    // Access methods
    public int getX() { 
        return myX; // Get X coordinate
    }
    
    public int getY() { 
        return myY; // Get Y coordinate
    }
    
    public boolean getB() { 
        return bigbean; // Get whether it is a big bean
    }
    
    public boolean getS() { 
        return show; // Get whether to show the bean
    }
    
    // Modification methods
    public void setX(int x) {
        myX = x; // Set X coordinate
    } 
    
    public void setY(int y) {
        myY = y; // Set Y coordinate
    } 
    
    public void setB(boolean B) {
        bigbean = B; // Set whether it is a big bean
    }
    
    public void setS(boolean S) {
        show = S; // Set whether to show the bean
    }
    
    // Instance method
    public void draw(Graphics myBuffer) {
        myBuffer.setColor(Color.orange); // Set color to orange
        if (show) {
            if (!bigbean) {
                // Draw small bean
                myBuffer.fillOval(XADD + myX * DUnits - 2, YADD + myY * DUnits - 2, 5, 5);
            } else {
                // Draw big bean
                myBuffer.fillOval(XADD + myX * DUnits - 6, YADD + myY * DUnits - 6, 13, 13);
            }
        }
    }
}
