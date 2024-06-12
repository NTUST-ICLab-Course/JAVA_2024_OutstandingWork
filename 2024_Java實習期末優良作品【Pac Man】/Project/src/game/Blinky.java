package game;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

public class Blinky extends Ghost {
    public static final int XADD = 250; // Offset for X coordinate
    public static final int YADD = 150; // Offset for Y coordinate
    public static final int DUnits = 20; // Unit distance
    public ImageIcon leftGif; // Image for moving left
    public ImageIcon rightGif; // Image for moving right
    public ImageIcon fearedGif; // Image for fear state
    public ImageIcon diedGif; // Image for death state
    public ImageIcon currentGif; // Currently displayed image

    // Default constructor
    public Blinky() {
        setX(13.5);
        setY(11);
        loadGifs(); // Load images
    }

    // Constructor with parameters
    public Blinky(double x, double y) {
        setX(x);
        setY(y);
        loadGifs(); // Load images
    }

    // Method to load images
    public void loadGifs() {
        leftGif = loadGif("/blinky left.gif");
        rightGif = loadGif("/blinky right.gif");
        fearedGif = loadGif("/fear.gif");
        diedGif = loadGif("/ghost.gif");
        currentGif = rightGif; // Default to right image
    }

    // Load image from specified path
    public ImageIcon loadGif(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println(path + " failed to load.");
            return null;
        }
    }

    // Method to chase the target
    public double Chase(double x, double y) {
        double rx = x, ry = y;
        if (rx < 0) rx = 0;
        if (rx > 27) rx = 27;
        if (ry < 0) ry = 0;
        if (ry > 30) ry = 30;
        return rx + ry * 56;
    }

    // Method to get the run point
    public double getRunPoint() {
        return (26 + 1 * 56);
    }

    // Method to get the start point
    public double getStartPoint() {
        return (13.5 + 14 * 56);
    }

    // Reset method
    public void Reset() {
        setX(13.5);
        setY(11);
        setC(false);
        setFM(false);
        setDied(false);
        setEnterRoom(false);
        setOutRoom(false);
        currentGif = rightGif; // Default to right image on reset
    }

    // Method to draw Blinky
    public void draw(Graphics myBuffer) {
        int x = (int) (XADD + getX() * DUnits - 11);
        int y = (int) (YADD + getY() * DUnits - 11);

        if (getDied()) {
            currentGif = diedGif;
        } else if (getFM()) {
            currentGif = fearedGif;
        } else {
            if (getDir() == 1) {
                currentGif = leftGif;
            } else if (getDir() == 3) {
                currentGif = rightGif;
            }
        }

        if (currentGif != null) {
            currentGif.paintIcon(null, myBuffer, x, y);
        } else {
            myBuffer.setColor(Color.red);
            myBuffer.fillOval(x, y, 23, 23);
        }
    }
}
