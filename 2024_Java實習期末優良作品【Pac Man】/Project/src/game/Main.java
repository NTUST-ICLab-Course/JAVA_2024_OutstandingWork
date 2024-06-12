package game;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pac-Man"); // Create the main frame
        frame.setSize(800, 800); // Set frame size
        frame.setLocation(400, 0); // Set frame location
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        frame.setContentPane(new MenuPanel(frame)); // Pass frame reference to MenuPanel constructor
        frame.setVisible(true); // Set frame visibility
    }
}
