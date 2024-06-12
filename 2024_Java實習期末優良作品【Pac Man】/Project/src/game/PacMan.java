package game;

import java.awt.*;
import javax.swing.ImageIcon;

public class PacMan {
    public static final int XADD = 250; // X-coordinate offset
    public static final int YADD = 150; // Y-coordinate offset
    public static final int DUnits = 20; // Unit distance
    public double myX; // PacMan's X-coordinate
    public double myY; // PacMan's Y-coordinate
    public int direction; // Current direction
    public int desiredDirection; // Desired direction
    public ImageIcon image; // Current displayed image
    public boolean isDead = false; // Whether PacMan is dead

    // PacMan images
    public ImageIcon leftImage = new ImageIcon(getClass().getResource("/pacman_left.gif"));
    public ImageIcon rightImage = new ImageIcon(getClass().getResource("/pacman_right.gif"));
    public ImageIcon upImage = new ImageIcon(getClass().getResource("/pacman_up.gif"));
    public ImageIcon downImage = new ImageIcon(getClass().getResource("/pacman_down.gif"));
    public ImageIcon deathGif = new ImageIcon(getClass().getResource("/pacman_death.gif"));

    // Default constructor
    public PacMan() {
        myX = 14;
        myY = 23;
        direction = 3; // Default direction is right
        desiredDirection = 3; // Default desired direction is also right
        image = rightImage; // Default image is right
    }

    // Constructor with parameters
    public PacMan(int x, int y) {
        myX = x;
        myY = y;
        direction = 3; // Default direction is right
        desiredDirection = 3; // Default desired direction is also right
        image = rightImage; // Default image is right
    }

    // Get X-coordinate
    public double getX() { 
        return myX;
    }

    // Get Y-coordinate
    public double getY() { 
        return myY;
    }

    // Set X-coordinate
    public void setX(double x) {
        myX = x;
        if (myX < 0) myX = 27; // When X-coordinate is less than 0, reappear from the right
        if (myX > 27) myX = 0; // When X-coordinate is greater than 27, reappear from the left
    } 

    // Set Y-coordinate
    public void setY(double y) {
        myY = y;
        if (myY < 0) myY = 30; // When Y-coordinate is less than 0, reappear from the bottom
        if (myY > 30) myY = 0; // When Y-coordinate is greater than 30, reappear from the top
    } 

    // Set current direction and update image
    public void setD(int dir) {
        direction = dir;
        switch (dir) {
            case 1:
                image = leftImage; // Left
                break;
            case 2:
                image = downImage; // Down
                break;
            case 3:
                image = rightImage; // Right
                break;
            case 4:
                image = upImage; // Up
                break;
        }
    }

    // Get current direction
    public int getD() {
        return direction;
    }

    // Set desired direction
    public void setDB(int dir) {
        desiredDirection = dir;
    }

    // Get desired direction
    public int getDB() {
        return desiredDirection;
    }

    // Method to move PacMan
    public void move() {
        if (!isDead) {
            switch (direction) {
                case 1:
                    setX(myX - 0.5); // Move left
                    break;
                case 2:
                    setY(myY + 0.5); // Move down
                    break;
                case 3:
                    setX(myX + 0.5); // Move right
                    break;
                case 4:
                    setY(myY - 0.5); // Move up
                    break;
            }
        }
    }

    // Method to draw PacMan
    public void draw(Graphics myBuffer) {
        if (isDead) {
            deathGif.paintIcon(null, myBuffer, (int) (XADD + myX * DUnits - 9), (int) (YADD + myY * DUnits - 9));
        } else {
            image.paintIcon(null, myBuffer, (int) (XADD + myX * DUnits - 9), (int) (YADD + myY * DUnits - 9));
        }
    }

    // Get death status
    public boolean isDead() {
        return isDead;
    }

    // Set death status
    public void setDead(boolean dead) {
        isDead = dead;
    }
}
