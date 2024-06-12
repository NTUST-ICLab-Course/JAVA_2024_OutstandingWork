package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MenuPanel extends JPanel {
    public JButton startButton, exitButton; // Start and Exit buttons
    public JLabel backgroundImage; // Background image label
    public Clip backgroundMusicClip; // Background music

    // Constructor
    public MenuPanel(JFrame frame) {
        setLayout(new BorderLayout()); // Set layout to BorderLayout
        ImageIcon icon = new ImageIcon(getClass().getResource("/motion cover.gif"));
        backgroundImage = new JLabel(icon);
        add(backgroundImage); // Add background image label to panel
        backgroundImage.setLayout(new GridBagLayout()); // Use GridBagLayout for precise positioning

        startButton = createButton("Start"); // Create Start button
        exitButton = createButton("Exit"); // Create Exit button

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundImage.add(startButton, gbc); // Add Start button to background image label

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        backgroundImage.add(exitButton, gbc); // Add Exit button to background image label

        startButton.addActionListener(e -> {
            backgroundMusicClip.stop(); // Stop background music
            GamePanel gamePanel = new GamePanel(frame); // Create GamePanel and pass frame reference
            frame.setContentPane(gamePanel); // Set GamePanel as content pane
            frame.validate();
            gamePanel.requestFocusInWindow(); // Ensure GamePanel gets focus
        });

        exitButton.addActionListener(e -> {
            stopBackgroundMusic(); // Stop background music
            System.exit(0); // Exit program
        });

        playBackgroundMusic(); // Play background music
    }

    // Method to create buttons
    public JButton createButton(String text) {
        JButton button = new JButton("<html><div style='border:2px solid white; padding:5px; color:white;'>" + text + "</div></html>");
        button.setFont(new Font("Arial", Font.BOLD, 32));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        // Mouse listener to change text and border color on hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setText("<html><div style='border:2px solid #00FF00; padding:5px; color:#00FF00;'>" + text + "</div></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setText("<html><div style='border:2px solid white; padding:5px; color:white;'>" + text + "</div></html>");
            }
        });

        return button;
    }

    // Method to play background music
    public void playBackgroundMusic() {
        try {
            URL url = getClass().getResource("/pacman_beginning.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
            backgroundMusicClip.start();
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop background music
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }
}
