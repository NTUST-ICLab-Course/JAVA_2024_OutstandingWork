package game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class GameOverPanel extends JPanel {
    public JFrame frame; // Main frame
    public int finalScore; // Final score
    public BufferedImage backgroundImage; // Background image
    public Clip gameOverSoundClip; // Game over sound effect
    public boolean isSoundPlayed = false; // Flag variable to prevent sound from playing repeatedly

    // Constructor that receives the main frame and final score
    public GameOverPanel(JFrame frame, int finalScore) {
        this.frame = frame;
        this.finalScore = finalScore;
        setLayout(new BorderLayout()); // Set layout manager
        loadBackgroundImage("/game succeed.jpg"); // Load background image

        JLabel scoreLabel = new JLabel("Score: " + this.finalScore, JLabel.CENTER);
        scoreLabel.setForeground(Color.MAGENTA); // Set font color
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 64)); // Set font
        add(scoreLabel, BorderLayout.CENTER); // Add score label to the center of the panel

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Set panel transparency
        buttonPanel.setLayout(new FlowLayout()); // Set layout manager
        JButton restartButton = createButton("Restart");
        restartButton.addActionListener(e -> {
            frame.setContentPane(new GamePanel(frame)); // Restart the game
            frame.validate();
            frame.getContentPane().requestFocusInWindow();
        });
        buttonPanel.add(restartButton);

        JButton exitButton = createButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Exit the game
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the south of the panel

        playGameOverSound(); // Play game over sound effect
    }

    // Method to load background image
    public void loadBackgroundImage(String path) {
        try {
            URL imageURL = getClass().getResource(path); // Load image using the given path
            backgroundImage = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading background image.");
        }
    }

    // Method to play game over sound effect
    public void playGameOverSound() {
        if (!isSoundPlayed) { // Check if the sound has already been played
            try {
                URL url = getClass().getResource("/game fail.wav"); // Load sound file
                if (url == null) {
                    throw new IOException("Audio file not found");
                }

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                gameOverSoundClip = AudioSystem.getClip();
                gameOverSoundClip.open(audioIn);
                gameOverSoundClip.start();
                isSoundPlayed = true; // Set flag variable to ensure the sound plays only once
            } catch (UnsupportedAudioFileException e) {
                System.err.println("Unsupported audio file format: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("I/O error with audio file: " + e.getMessage());
            } catch (LineUnavailableException e) {
                System.err.println("Audio line unavailable: " + e.getMessage());
            }
        }
    }

    // Override paintComponent method to draw background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    // Method to create a button
    public JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        return button;
    }
}
