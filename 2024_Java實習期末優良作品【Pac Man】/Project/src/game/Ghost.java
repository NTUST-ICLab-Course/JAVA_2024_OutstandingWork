package game;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Ghost {

    public static final int XADD = 250; // X coordinate offset
    public static final int YADD = 150; // Y coordinate offset
    public static final int DUnits = 20; // Unit distance
    public double myX; // X coordinate of the ghost
    public double myY; // Y coordinate of the ghost
    public int[] myD; // Direction array
    public int myDRun; // Current direction
    public boolean myR; // Whether in runaway state
    public boolean myC; // Whether in chase state
    public boolean myFM; // Whether in fear mode
    public boolean myDied; // Whether the ghost is dead
    public boolean myEnterRoom; // Whether entering the room
    public boolean myOutRoom; // Whether exiting the room
    public Image ghostImage; // Image of the ghost
    public Image fearedImage; // Image of the ghost in fear mode

    // Default constructor
    public Ghost() {
        myX = 12.5;
        myY = 11;
        myD = new int[6];
        myDRun = myD.length - 1; // Ensure initial value is within bounds
        loadImages(); // Load images
        ResetDRun();
    }

    // Constructor with parameters
    public Ghost(double x, double y) {
        myX = x;
        myY = y;
        myD = new int[6];
        myDRun = myD.length - 1; // Ensure initial value is within bounds
        loadImages(); // Load images
        ResetDRun();
    }

    // Method to load images
    public void loadImages() {
        URL ghostUrl = getClass().getResource("/ghost.gif");
        URL fearedUrl = getClass().getResource("/feared.gif");
        if (ghostUrl != null) {
            ghostImage = new ImageIcon(ghostUrl).getImage();
            System.out.println("Ghost GIF loaded successfully.");
        } else {
            System.out.println("Ghost GIF failed to load.");
        }
        if (fearedUrl != null) {
            fearedImage = new ImageIcon(fearedUrl).getImage();
            System.out.println("Feared GIF loaded successfully.");
        } else {
            System.out.println("Feared GIF failed to load.");
        }
    }

    // Method to chase a target
    public double Chase(double x, double y) {
        double rx = x, ry = y;
        if (rx < 0) rx = 0;
        if (rx > 27) rx = 27;
        if (ry < 0) ry = 0;
        if (ry > 30) ry = 30;
        return rx + ry * 56;
    }

    // Accessor methods
    public double getX() { return myX; }
    public double getY() { return myY; }
    public boolean getR() { return myR; }
    public boolean getC() { return myC; }
    public boolean getFM() { return myFM; }
    public boolean getDied() { return myDied; }
    public boolean getEnterRoom() { return myEnterRoom; }
    public boolean getOutRoom() { return myOutRoom; }
    public int[] getD() { return myD; }
    public int getDRun() { return myDRun; }
    public int getDir() {
        if (myDRun >= 0 && myDRun < myD.length) {
            return myD[myDRun];
        } else {
            return 0; // Avoid out-of-bounds
        }
    }
    public double getRunPoint() { return (1 + 1 * 56); }
    public double getStartPoint() { return (1 + 1 * 56); }

    // Modifier methods
    public void setX(double x) {
        myX = x;
        if (myX < 0) { myX = 27; myDRun = myD.length - 1; }
        if (myX > 27) { myX = 0; myDRun = myD.length - 1; }
    }

    public void setY(double y) {
        myY = y;
        if (myY < 0) { myY = 30; myDRun = myD.length - 1; }
        if (myY > 30) { myY = 0; myDRun = myD.length - 1; }
    }

    public void setR(boolean run) {
        myR = run;
        ResetDRun();
    }

    public void setC(boolean chase) { myC = chase; }
    public void setDied(boolean died) {
        myDied = died;
        if (died) myFM = false;
        ResetDRun();
    }

    public void setFM(boolean f) {
        myFM = f;
        ResetDRun();
    }

    public void setEnterRoom(boolean er) { myEnterRoom = er; }
    public void setOutRoom(boolean or) { myOutRoom = or; }
    public void setD(int[] dir) { myD = dir; }
    public void setDRun(int runs) { myDRun = runs; }

    // Method to reset direction counter
    public void ResetDRun() {
        myDRun = myD.length - 1; // Ensure reset value is within bounds
    }

    // Method to reset the ghost's position and state
    public void Reset() {
        setX(12.5);
        setY(11);
        setDRun(myD.length - 1);
        setFM(false);
        setDied(false);
    }

    // Method to draw the ghost
    public void draw(Graphics myBuffer) {
        Image imageToDraw = ghostImage; // Default to using ghostImage

        if ((myFM || myDied) && fearedImage != null) {
            imageToDraw = fearedImage;
        }

        if (imageToDraw != null) {
            myBuffer.drawImage(imageToDraw, (int)(XADD + myX * DUnits - 11), (int)(YADD + myY * DUnits - 11), 23, 23, null);
        } else {
            if (myDied) myBuffer.setColor(Color.white);
            else if (myFM) myBuffer.setColor(Color.blue);
            else myBuffer.setColor(Color.red);
            myBuffer.fillOval((int)(XADD + myX * DUnits - 11), (int)(YADD + myY * DUnits - 11), 23, 23);
        }
    }
}
