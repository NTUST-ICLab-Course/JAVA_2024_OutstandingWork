package game;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

public class Inky extends Ghost {

    public static final int XADD = 250; // X coordinate offset
    public static final int YADD = 150; // Y coordinate offset
    public static final int DUnits = 20; // Unit distance
    public ImageIcon leftGif; // Image for moving left
    public ImageIcon rightGif; // Image for moving right
    public ImageIcon fearedGif; // Image for fear mode
    public ImageIcon diedGif; // Image for death state
    public ImageIcon currentGif; // Currently displayed image

    // Default constructor
    public Inky() {
        setX(11.5);
        setY(14);
        loadGifs(); // Load images
    }

    // Constructor with parameters
    public Inky(double x, double y) {
        setX(x);
        setY(y);
        loadGifs(); // Load images
    }

    // Method to load images
    public void loadGifs() {
        leftGif = loadGif("/inky left.gif");
        rightGif = loadGif("/inky right.gif");
        fearedGif = loadGif("/fear.gif");
        diedGif = loadGif("/ghost.gif");
        currentGif = rightGif; // Default to right image
    }

    // Method to load image from specified path
    public ImageIcon loadGif(String path) {
        URL url = getClass().getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.out.println(path + " failed to load.");
            return null;
        }
    }

    // Method to chase a target
    public double Chase(double x, double y, int dir, double Bx, double By) {
        double rx = x, ry = y;
        if (dir == 1) rx -= 2; // Left
        if (dir == 2) ry += 2; // Down
        if (dir == 3) rx += 2; // Right
        if (dir == 4) ry -= 2; // Up
        rx = rx + (rx - Bx);
        ry = ry + (ry - By);

        if (rx < 0) rx = 0;
        if (rx > 27) rx = 27;
        if (ry < 0) ry = 0;
        if (ry > 30) ry = 30;
        return rx + ry * 56;
    }

    // Method to get the run point
    public double getRunPoint() {
        return (26 + 29 * 56);
    }

    // Method to get the start point
    public double getStartPoint() {
        return (11.5 + 14 * 56);
    }

    // Reset method
    public void Reset() {
        setX(11.5);
        setY(14);
        setC(false);
        setFM(false);
        setDied(false);
        setEnterRoom(false);
        setOutRoom(false);
        currentGif = rightGif; // Default to right image on reset
    }

    // Method to draw Inky
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
            myBuffer.setColor(Color.cyan);
            myBuffer.fillOval(x, y, 23, 23);
        }
    }
}
