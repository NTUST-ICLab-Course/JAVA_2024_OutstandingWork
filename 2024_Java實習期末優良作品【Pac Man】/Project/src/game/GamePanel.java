package game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class GamePanel extends JPanel {
    public static final int FRAME = 1000; // Window size
    public static final Color BACKGROUND = new Color(0, 0, 0); // Background color
    public BufferedImage myImage; // Game canvas
    public Graphics myBuffer; // Canvas buffer

    public PacMan PM; // PacMan object
    public Blinky GB; // Blinky ghost object
    public Pinky GP; // Pinky ghost object
    public Inky GI; // Inky ghost object
    public Clyde GC; // Clyde ghost object

    public bean[] beans; // Bean object array
    public Wall[] Walls; // Wall object array
    public Door DR; // Door object

    public Timer ready1, ready2, ready3, ready4;
    public Timer drawAll, gameLost1, gameLost2, gameWin1, gameWin2, gameReset;
    public Timer PMmove, ghostMove, ghostRun, GhostFMdownTime;
    public int Score = 0; // Score
    public int Wins = 0; // Number of wins
    public int Losts = 0; // Number of losses
    public String Score_String; // Score string
    public JFrame frame; // Main frame
    public Clip eatBeanClip; // Eat bean sound effect
    public Clip deathClip; // Death sound effect
    public ImageIcon deathGif; // Pac-Man death animation
    public boolean playingDeathAnimation = false; // Flag variable
    public Clip upLevelClip; // Level up sound effect
    public Clip backgroundClip; // Background music
    public Image pacManLifeImage; // Pac-Man life image

    // Constructor
    public GamePanel(JFrame frame) {
        this.frame = frame;
        myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(BACKGROUND);
        myBuffer.fillRect(0, 0, FRAME, FRAME);

        PM = new PacMan();
        GB = new Blinky();
        GP = new Pinky();
        GI = new Inky();
        GC = new Clyde();

        WallsCreate();
        DR = new Door();
        beansCreate();
        beansSet();

        loadEatBeanSound(); // Load eat bean sound effect
        loadDeathSound(); // Load death sound effect
        loadDeathGif(); // Load death animation GIF
        loadUpLevelSound(); // Load level up sound effect
        loadBackgroundSound(); // Load background music
        loadPacManLifeImage(); // Load Pac-Man life image

        ready1 = new Timer(1000, new ready1List());
        ready2 = new Timer(3000, new ready2List());
        ready3 = new Timer(3000, new ready3List());
        ready4 = new Timer(3000, new ready4List());
        drawAll = new Timer(50, new drawAllList());
        PMmove = new Timer(75, new PMmoveList());
        ghostMove = new Timer(100, new ghostMoveList());
        GhostFMdownTime = new Timer(5000, new GhostFMdownTimeList());
        ghostRun = new Timer(20000, new ghostRunList());
        gameLost1 = new Timer(10, new gameLost1List());
        gameLost2 = new Timer(50, new gameLost2List());
        gameWin1 = new Timer(10, new gameWin1List());
        gameWin2 = new Timer(50, new gameWin2List());
        gameReset = new Timer(3000, new gameResetList());

        ready1.start();
        drawAll.start();

        addKeyListener(new Key());
        setFocusable(true);
        playBackgroundSound(); // Play background music
    }

    public void loadPacManLifeImage() {
        URL url = getClass().getResource("/pac man life.png"); // Ensure correct file path
        if (url != null) {
            pacManLifeImage = new ImageIcon(url).getImage();
            System.out.println("Pac-Man life image loaded successfully.");
        } else {
            System.out.println("Failed to load Pac-Man life image.");
        }
    }

    public void loadEatBeanSound() {
        try {
            URL url = getClass().getResource("/pacman_chomp.wav"); // Ensure correct file path
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            eatBeanClip = AudioSystem.getClip();
            eatBeanClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playEatBeanSound() {
        if (eatBeanClip != null) {
            if (eatBeanClip.isRunning()) {
                eatBeanClip.stop();
            }
            eatBeanClip.setFramePosition(0);
            eatBeanClip.start();
        }
    }

    public void loadDeathSound() {
        try {
            URL url = getClass().getResource("/pacman_death.wav"); // Ensure correct file path
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            deathClip = AudioSystem.getClip();
            deathClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void loadDeathGif() {
        URL url = getClass().getResource("/pacman_death.gif"); // Ensure correct file path
        if (url != null) {
            deathGif = new ImageIcon(url);
            System.out.println("Death GIF loaded successfully.");
        } else {
            System.out.println("Failed to load death GIF.");
        }
    }

    public void playDeathSound() {
        if (deathClip != null) {
            if (deathClip.isRunning()) {
                deathClip.stop();
            }
            deathClip.setFramePosition(0);
            deathClip.start();
        }
    }

    public void playDeathAnimation() {
        // Stop other timers
        ready1.stop();
        ready2.stop();
        ready3.stop();
        ready4.stop();
        PMmove.stop();
        ghostMove.stop();
        ghostRun.stop();
        GhostFMdownTime.stop();

        // Check if death GIF is loaded
        if (deathGif != null) {
            System.out.println("Playing death animation.");
            myBuffer.drawImage(deathGif.getImage(), (int) PM.getX() * 30, (int) PM.getY() * 30, this);
        } else {
            System.out.println("Death GIF is not loaded.");
        }
        repaint();

        // Start timer to handle game over logic after GIF animation ends
        Timer deathAnimationTimer = new Timer(deathGif.getIconWidth() * 100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameLost1.start(); // Trigger game over logic after animation ends
            }
        });
        deathAnimationTimer.setRepeats(false); // Ensure timer runs only once
        deathAnimationTimer.start();
    }

    public void loadUpLevelSound() {
        try {
            URL url = getClass().getResource("/up level.wav"); // Ensure correct file path
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            upLevelClip = AudioSystem.getClip();
            upLevelClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playUpLevelSound() {
        if (upLevelClip != null) {
            if (upLevelClip.isRunning()) {
                upLevelClip.stop();
            }
            upLevelClip.setFramePosition(0);
            upLevelClip.start();
        }
    }

    public void loadBackgroundSound() {
        try {
            URL url = getClass().getResource("/kim-lightyear.wav"); // Ensure correct file path
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundSound() {
        if (backgroundClip != null) {
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop background music
            backgroundClip.start();
        }
    }

    public void stopBackgroundSound() {
        if (backgroundClip != null) {
            backgroundClip.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }

    public class Key extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP) PM.setDB(4);
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) PM.setDB(3);
            if (e.getKeyCode() == KeyEvent.VK_DOWN) PM.setDB(2);
            if (e.getKeyCode() == KeyEvent.VK_LEFT) PM.setDB(1);
        }
    }

    public class ready1List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PMmove.start();
            ghostMove.start();
            ghostRun.start();

            GB.setOutRoom(true);
            GB.setC(true);
            ready1.stop();
            ready2.start();
            repaint();
        }
    }

    public class ready2List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GP.setOutRoom(true);
            GP.setC(true);
            ready2.stop();
            ready3.start();
            repaint();
        }
    }

    public class ready3List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GI.setOutRoom(true);
            GI.setC(true);
            ready3.stop();
            ready4.start();
            repaint();
        }
    }

    public class ready4List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            GC.setOutRoom(true);
            GC.setC(true);
            ready4.stop();
            repaint();
        }
    }

    public class drawAllList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myBuffer.setColor(BACKGROUND);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            DrawWall();
            DR.draw(myBuffer);
            Drawbeans();

            // Ensure drawing only when Pac-Man is not playing death animation
            if (!playingDeathAnimation) {
                GB.draw(myBuffer);
                GP.draw(myBuffer);
                GI.draw(myBuffer);
                GC.draw(myBuffer);
                PM.draw(myBuffer);
            }

            ShowScore();
            drawPacManLives(); // Draw Pac-Man's lives
            repaint();
        }
    }

    public void drawPacManLives() {
        if (pacManLifeImage != null) {
            myBuffer.setFont(new Font("Dialog", Font.PLAIN, 32)); // Set font
            myBuffer.setColor(Color.pink); // Set color
            myBuffer.drawString("Life:", 240, 110); // Draw "Life:" text

            for (int i = 0; i < (3 - Losts); i++) {
                myBuffer.drawImage(pacManLifeImage, 320 + (i * 50), 80, 40, 40, this);
            }
        }
    }

    public class PMmoveList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PMmove();
            PMeat();
            AllCatch();
            repaint();
        }
    }

    public class ghostMoveList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AllGsmove();
            AllCatch();
            repaint();
        }
    }

    public class ghostRunList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AllGsRun();
            ghostRun.stop();
            repaint();
        }
    }

    public class GhostFMdownTimeList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AllGsFM(false);
            GhostFMdownTime.stop();
            repaint();
        }
    }

    public class gameWin1List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ready1.stop();
            ready2.stop();
            ready3.stop();
            ready4.stop();
            PMmove.stop();
            ghostMove.stop();
            ghostRun.stop();
            GhostFMdownTime.stop();
            Wins++;

            gameWin1.stop();
            if (Wins < 3) gameReset.start();
            else {
                gameWin2.start();
                drawAll.stop();
            }
            repaint();
        }
    }

    public class gameWin2List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopAllTimers();
            stopBackgroundSound(); // Stop background music
            frame.setContentPane(new GameCompletePanel(frame, Score)); // Show game complete screen
            frame.validate();
        }
    }

    public void stopAllTimers() {
        ready1.stop();
        ready2.stop();
        ready3.stop();
        ready4.stop();
        PMmove.stop();
        ghostMove.stop();
        ghostRun.stop();
        GhostFMdownTime.stop();
        gameLost1.stop();
        gameLost2.stop();
        gameWin1.stop();
        gameWin2.stop();
        gameReset.stop();
    }

    public class gameLost1List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ready1.stop();
            ready2.stop();
            ready3.stop();
            ready4.stop();
            PMmove.stop();
            ghostMove.stop();
            ghostRun.stop();
            GhostFMdownTime.stop();
            Losts++;

            gameLost1.stop();
            if (Losts < 3) {
                gameReset.start(); // If less than 3 deaths, restart the game
            } else {
                drawAll.stop();
                showGameOverPanel(); // Show Game Over screen after 3 deaths
            }
            repaint();
        }
    }

    public void showGameOverPanel() {
        stopBackgroundSound(); // Stop background music
        GameOverPanel gameOverPanel = new GameOverPanel(frame, Score);
        frame.setContentPane(gameOverPanel);
        frame.validate();
        frame.repaint();
    }

    public class gameLost2List implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            myBuffer.setColor(BACKGROUND);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            repaint();
        }
    }

  
    public class gameResetList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AllReset();
            ready1.start();
            gameReset.stop();
            repaint();
        }
    }

    public void WallsCreate() { // Map
        Walls = new Wall[54];
        Walls[0] = new Wall(0, 0, 28, 1); // boundary
        Walls[1] = new Wall(27, 0, 1, 10);
        Walls[2] = new Wall(0, 0, 1, 10);
        Walls[3] = new Wall(0, 9, 6, 1);
        Walls[4] = new Wall(22, 9, 6, 1);
        Walls[5] = new Wall(5, 9, 1, 5);
        Walls[6] = new Wall(22, 9, 1, 5);
        Walls[7] = new Wall(0, 13, 6, 1);
        Walls[8] = new Wall(22, 13, 6, 1);
        Walls[9] = new Wall(0, 15, 6, 1);
        Walls[10] = new Wall(22, 15, 6, 1);
        Walls[11] = new Wall(5, 15, 1, 5);
        Walls[12] = new Wall(22, 15, 1, 5);
        Walls[13] = new Wall(0, 19, 6, 1);
        Walls[14] = new Wall(22, 19, 6, 1);
        Walls[15] = new Wall(0, 19, 1, 12);
        Walls[16] = new Wall(27, 19, 1, 12);
        Walls[17] = new Wall(0, 30, 28, 1);

        Walls[18] = new Wall(2, 2, 4, 3); // upper part
        Walls[19] = new Wall(2, 6, 4, 2);
        Walls[20] = new Wall(7, 2, 5, 3);
        Walls[21] = new Wall(7, 6, 2, 8);
        Walls[22] = new Wall(7, 9, 5, 2);
        Walls[23] = new Wall(10, 6, 8, 2);
        Walls[24] = new Wall(13, 6, 2, 5);
        Walls[25] = new Wall(13, 0, 2, 5);
        Walls[26] = new Wall(16, 2, 5, 3);
        Walls[27] = new Wall(19, 6, 2, 8);
        Walls[28] = new Wall(16, 9, 5, 2);
        Walls[29] = new Wall(22, 2, 4, 3);
        Walls[30] = new Wall(22, 6, 4, 2);

        Walls[31] = new Wall(10, 12, 3.5, 1); // middle
        Walls[32] = new Wall(14.5, 12, 3.5, 1);
        Walls[33] = new Wall(10, 12, 1, 5);
        Walls[34] = new Wall(10, 16, 8, 1);
        Walls[35] = new Wall(17, 12, 1, 5);

        Walls[36] = new Wall(7, 15, 2, 5); // lower part
        Walls[37] = new Wall(19, 15, 2, 5);
        Walls[38] = new Wall(10, 18, 8, 2);
        Walls[39] = new Wall(13, 18, 2, 5);
        Walls[40] = new Wall(0, 24, 3, 2);
        Walls[41] = new Wall(2, 21, 4, 2);
        Walls[42] = new Wall(4, 21, 2, 5);
        Walls[43] = new Wall(7, 21, 5, 2);
        Walls[44] = new Wall(7, 24, 2, 5);
        Walls[45] = new Wall(2, 27, 10, 2);
        Walls[46] = new Wall(10, 24, 8, 2);
        Walls[47] = new Wall(13, 24, 2, 5);
        Walls[48] = new Wall(16, 21, 5, 2);
        Walls[49] = new Wall(22, 21, 4, 2);
        Walls[50] = new Wall(22, 21, 2, 5);
        Walls[51] = new Wall(19, 24, 2, 5);
        Walls[52] = new Wall(16, 27, 10, 2);
        Walls[53] = new Wall(25, 24, 3, 2);
    }

    public void DrawWall() {
        for (Wall wall : Walls) {
            wall.draw(myBuffer);
        }
    }

    public void beansCreate() {
        beans = new bean[868];
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 31; j++) {
                beans[i + j * 28] = new bean(i, j, false, false);
            }
        }
    }

    public void beansSet() {
        //Rows (X1, X2, Y)
        beansSetRows(1, 12, 1);
        beansSetRows(15, 26, 1);
        beansSetRows(1, 26, 5);
        beansSetRows(1, 6, 8);
        beansSetRows(9, 12, 8);
        beansSetRows(15, 18, 8);
        beansSetRows(21, 26, 8);
        beansSetRows(1, 12, 20);
        beansSetRows(15, 26, 20);
        beansSetRows(1, 3, 23);
        beansSetRows(6, 21, 23);
        beansSetRows(24, 26, 23);
        beansSetRows(1, 6, 26);
        beansSetRows(9, 12, 26);
        beansSetRows(15, 18, 26);
        beansSetRows(21, 26, 26);
        beansSetRows(1, 26, 29);

        //Cols (Y1, Y2, X)
        beansSetCols(1, 8, 1);
        beansSetCols(1, 26, 6);
        beansSetCols(1, 5, 12);
        beansSetCols(1, 5, 15);
        beansSetCols(1, 26, 21);
        beansSetCols(1, 8, 26);
        beansSetCols(5, 8, 9);
        beansSetCols(5, 8, 18);
        beansSetCols(20, 23, 1);
        beansSetCols(20, 23, 12);
        beansSetCols(20, 23, 15);
        beansSetCols(20, 23, 26);
        beansSetCols(23, 26, 3);
        beansSetCols(23, 26, 6);
        beansSetCols(23, 26, 18);
        beansSetCols(23, 26, 24);
        beansSetCols(26, 29, 1);
        beansSetCols(26, 29, 12);
        beansSetCols(26, 29, 15);
        beansSetCols(26, 29, 26);

        // Big beans
        beans[1 + 3 * 28].setB(true);
        beans[26 + 3 * 28].setB(true);
        beans[1 + 23 * 28].setB(true);
        beans[26 + 23 * 28].setB(true);
        // Pac-Man starting position
        beans[14 + 23 * 28].setS(false);
    }

    public void beansSetRows(int x1, int x2, int y) {
        for (int i = x1; i <= x2; i++) {
            beans[i + y * 28].setS(true);
        }
    }

    public void beansSetCols(int y1, int y2, int x) {
        for (int i = y1; i <= y2; i++) {
            beans[x + i * 28].setS(true);
        }
    }

    public void Drawbeans() {
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 31; j++) {
                beans[i + j * 28].draw(myBuffer);
            }
        }
    }

    public void ShowScore() {
        Score_String = Score + "";
        myBuffer.setFont(new Font("Dialog", Font.PLAIN, 30));
        myBuffer.setColor(Color.white);
        myBuffer.drawString("SCORE", 680, 110);
        myBuffer.drawString(Score_String, 770 - (int) (Math.log10(Score)) * 15, 140);
    }

    public void AllGsmove() {
        GSmove(GB);
        GSmove(GP);
        GSmove(GI);
        GSmove(GC);
    }

    public void AllGsRun() {
        GB.setR(true);
        GP.setR(true);
        GI.setR(true);
        GC.setR(true);
    }

    public void AllGsFM(boolean on) {
        GB.setFM(on);
        GP.setFM(on);
        GI.setFM(on);
        GC.setFM(on);
    }

    public void AllCatch() {
        Catch(GB);
        Catch(GP);
        Catch(GI);
        Catch(GC);
    }

    public void AllReset() {
        PM.setX(14);
        PM.setY(23);
        PM.setD(1);
        PM.setDead(false); // Ensure Pac-Man is alive when respawning
        GB.Reset();
        GP.Reset();
        GI.Reset();
        GC.Reset();
        if (!CheckBeans()) beansSet();
    }

    public void PMmove() {
        // new dir feasible?
        if (PM.getDB() == 1) if (!block(PM.getX() - 0.5, PM.getY())) PM.setD(1);
        if (PM.getDB() == 2) if (!block(PM.getX(), PM.getY() + 0.5)) PM.setD(2);
        if (PM.getDB() == 3) if (!block(PM.getX() + 0.5, PM.getY())) PM.setD(3);
        if (PM.getDB() == 4) if (!block(PM.getX(), PM.getY() - 0.5)) PM.setD(4);

        // overwrite dir
        if (PM.getD() == 1) if (!block(PM.getX() - 0.5, PM.getY()) || (PM.getX() <= 1 && PM.getY() == 14)) PM.setX(PM.getX() - 0.5);
        if (PM.getD() == 2) if (!block(PM.getX(), PM.getY() + 0.5)) PM.setY(PM.getY() + 0.5);
        if (PM.getD() == 3) if (!block(PM.getX() + 0.5, PM.getY())) PM.setX(PM.getX() + 0.5);
        if (PM.getD() == 4) if (!block(PM.getX(), PM.getY() - 0.5)) PM.setY(PM.getY() - 0.5);
    }

    public void PMeat() {
        if ((PM.getX() % 1 == 0) && PM.getY() % 1 == 0) {
            if (beans[(int) (PM.getX() + PM.getY() * 28)].getS()) {
                beans[(int) (PM.getX() + PM.getY() * 28)].setS(false);
                if (beans[(int) (PM.getX() + PM.getY() * 28)].getB()) {
                    Score += 50;
                    AllGsFM(true);  // Set all ghosts to blue state
                    GhostFMdownTime.start();  // Start timer to revert ghosts to normal state
                } else {
                    Score += 10;
                    playEatBeanSound();  // Play eat bean sound effect
                }
                if (!CheckBeans()) {
                    playUpLevelSound(); // Play level up sound effect
                    gameWin1.start();
                }
            }
        }
    }

    public boolean CheckBeans() {
        for (bean bean : beans) if (bean.getS()) return true;
        return false;
    }

    public boolean block(double X, double Y) {
        for (Wall wall : Walls)
            if (X >= wall.getX() - 0.5 &&
                    X <= wall.getX() + wall.getXWidth() - 0.5 &&
                    Y >= wall.getY() - 0.5 &&
                    Y <= wall.getY() + wall.getYWidth() - 0.5)
                return true;
        if ((X >= 10 && X <= 17 && Y >= 12 && Y <= 16) ||
                (X > 0 && X <= 5 && Y >= 9 && Y <= 13) ||
                (X > 0 && X <= 5 && Y >= 16 && Y <= 19) ||
                (X >= 22 && X < 28 && Y >= 9 && Y <= 13) ||
                (X >= 22 && X < 28 && Y >= 16 && Y <= 19) ||
                (X >= 28) || (X <= 0) || (Y >= 31) || (Y <= 0))
            return true;
        return false;
    }

    public int[] Go_to_target(double Gx, double Gy, double Px, double Py) {
        double Nx = Px, Ny = Py;
        double GNS;
        double Straight_line_distance = Math.sqrt(Math.pow(Gx - Px, 2) + Math.pow(Gy - Py, 2));

        // Reduce the distance when the distance is too far
        if (Straight_line_distance >= 6)
            GNS = Get_nearest_space(Gx + ((Px - Gx) * 6 / Straight_line_distance),
                    Gy + ((Py - Gy) * 6 / Straight_line_distance));
        else GNS = Get_nearest_space(Px, Py);
        Nx = GNS % 28;
        Ny = (GNS - Nx) / 56;
        int[] dir = new int[100];
        int[] out = new int[6];
        int distance = 1;
        int max_distance = 1;
        int a, b, c, d;
        for (int i = 0; i < 100; i++) dir[i] = 0;

        if (Gx == Nx && Gy == Ny) {
            System.arraycopy(dir, 0, out, 0, out.length);
            return out;
        }
        dir[0] = 1;

        while (true) {
            a = 0;
            b = 0;
            c = 0;
            d = 0;
            for (int i = 0; i < distance; i++) { // Direction to relative position
                if (dir[i] == 1) a++;
                if (dir[i] == 2) b++;
                if (dir[i] == 3) c++;
                if (dir[i] == 4) d++;
            }
            //////////////////
            if (Gx - a * 0.5 + c * 0.5 == Nx && // arrived
                    Gy + b * 0.5 - d * 0.5 == Ny) {
                System.arraycopy(dir, 0, out, 0, out.length);
                return out;
            }
            //////////////////
            else if (block(Gx - a * 0.5 + c * 0.5, Gy + b * 0.5 - d * 0.5) || // go back
                    distance == max_distance) {
                dir[distance - 1]++;
                if (distance > 1)
                    if ((dir[distance - 1] == 1 && dir[distance - 2] == 3) ||
                            (dir[distance - 1] == 2 && dir[distance - 2] == 4) ||
                            (dir[distance - 1] == 3 && dir[distance - 2] == 1) ||
                            (dir[distance - 1] == 4 && dir[distance - 2] == 2))
                        dir[distance - 1]++;

                while (dir[distance - 1] == 5) {
                    if (distance > 1) {
                        distance--;
                        dir[distance] = 0;
                        dir[distance - 1]++;
                        if (distance > 1)
                            if ((dir[distance - 1] == 1 && dir[distance - 2] == 3) ||
                                    (dir[distance - 1] == 2 && dir[distance - 2] == 4) ||
                                    (dir[distance - 1] == 3 && dir[distance - 2] == 1) ||
                                    (dir[distance - 1] == 4 && dir[distance - 2] == 2))
                                dir[distance - 1]++;
                    } else {
                        dir[distance - 1] = 1; // dir[0]
                        max_distance++;
                    }
                }
            }
            ///////////
            else { // forward
                dir[distance] = 1;
                distance++;
            }
        }
    }

    public double Get_nearest_space(double x, double y) {
        double rx = 0, ry = 0;
        double nx = (double) ((int) (x * 2)) / 2;
        double ny = (double) ((int) (y * 2)) / 2;
        while (true) {
            if (nx + rx < 28 && nx + rx >= 0 && ny + ry < 31 && ny + ry >= 0)
                if (!block((nx + rx), (ny + ry))) return nx + rx + (ny + ry) * 56;
            rx = -ry;
            rx += 0.5;
            ry = 0;

            if (nx + rx < 28 && nx + rx >= 0 && ny + ry < 31 && ny + ry >= 0)
                if (!block((nx + rx), (ny + ry))) return nx + rx + (ny + ry) * 56;
            ry = rx;
            rx = 0;

            if (nx + rx < 28 && nx + rx >= 0 && ny + ry < 31 && ny + ry >= 0)
                if (!block((nx + rx), (ny + ry))) return nx + rx + (ny + ry) * 56;
            rx = -ry;
            ry = 0;
            if (nx + rx < 28 && nx + rx >= 0 && ny + ry < 31 && ny + ry >= 0)
                if (!block((nx + rx), (ny + ry))) return nx + rx + (ny + ry) * 56;
            ry = rx;
            rx = 0;
        }
    }

    public void GSmove(Ghost G) {
        double getpoint;
        double Nx, Ny;
        if (G.getEnterRoom()) {
            Nx = G.getStartPoint() % 28;
            Ny = (G.getStartPoint() - Nx) / 56;
            if (G.getY() != Ny) G.setY(G.getY() + 0.5);
            else {
                if (G.getX() > Nx) G.setX(G.getX() - 0.5);
                else if (G.getX() < Nx) G.setX(G.getX() + 0.5);
                else {
                    G.setEnterRoom(false);
                    G.setDied(false);
                    G.setOutRoom(true);
                }
            }
        } else if (G.getOutRoom()) {
            if (G.getX() > 13.5) G.setX(G.getX() - 0.5);
            else if (G.getX() < 13.5) G.setX(G.getX() + 0.5);
            else {
                if (G.getY() > 11) G.setY(G.getY() - 0.5);
                else G.setOutRoom(false);
            }
        } else if (G.getC()) {
            if (G.getDRun() >= 6) {
                if (G.getX() + G.getY() * 56 == G.getRunPoint()) G.setR(false);
                if (G.getDied()) {
                    getpoint = (13.5 + 11 * 56);
                    if (G.getX() == 13.5 && G.getY() == 11) G.setEnterRoom(true);
                } else if (G.getR() || G.getFM()) getpoint = G.getRunPoint();
                else getpoint = G.Chase(PM.getX(), PM.getY());
                G.setD(Go_to_target(G.getX(), G.getY(), getpoint % 28, getpoint / 56));
                G.setDRun(0);
            }
            if (G.getDir() == 1) G.setX(G.getX() - 0.5);
            else if (G.getDir() == 2) G.setY(G.getY() + 0.5);
            else if (G.getDir() == 3) G.setX(G.getX() + 0.5);
            else if (G.getDir() == 4) G.setY(G.getY() - 0.5);
            else if (G.getDir() == 0) G.ResetDRun();
            G.setDRun(G.getDRun() + 1);
        }
    }

    public void Catch(Ghost G) {
        if (PM.getX() == G.getX() && PM.getY() == G.getY()) {
            if (G.getDied()) {
                // If the ghost is already "dead", do nothing
            } else if (G.getFM()) {
                G.setDied(true);
                Score += 200;
            } else {
                playDeathSound(); // Play death sound effect
                PM.setDead(true); // Set Pac-Man to dead state
                playDeathAnimation(); // Play death animation
            }
        }
    }
}
