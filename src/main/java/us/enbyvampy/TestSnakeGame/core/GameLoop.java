package us.enbyvampy.TestSnakeGame.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;

public class GameLoop extends JPanel implements Runnable {
    private boolean running = false; // Controls whether the game loop is running
    private final int FPS = 60; // Target frames per second
    private final long FRAME_TIME = 1000 / FPS; // Time per frame in milliseconds
    private SnakeGame snakeGame; // Your game logic handler
    private KeyHandler keyHandler; // KeyHandler instance

    public GameLoop() {
        this.snakeGame = new SnakeGame(); // Initialize the game instance
        this.keyHandler = new KeyHandler(snakeGame); // Create KeyHandler and pass the game instance
        this.addKeyListener(keyHandler); // Add the KeyListener to the JPanel
        this.setFocusable(true); // Allow the JPanel to receive keyboard focus
        this.requestFocusInWindow(); // Ensure the panel can listen for key events
    }

    // Start the game loop in a new thread
    public void start() {
        running = true; // Flag to keep the loop running
        Thread thread = new Thread(this);
        thread.start(); // Start the game loop thread
    }

    // Stop the game loop
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            // Track the start time of each frame
            long frameStartTime = System.currentTimeMillis();

            // Update the game logic
            update();

            // Repaint the screen (render the game)
            repaint();

            // Calculate the time taken for this frame
            long frameEndTime = System.currentTimeMillis();
            long elapsedFrameTime = frameEndTime - frameStartTime;

            // Sleep for the remaining time if the frame completed early
            if (elapsedFrameTime < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - elapsedFrameTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Delegate the game state update to the Game object
    private void update() {
        snakeGame.update(); // Update the game logic
    }

    // Delegate rendering to the Game object
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the screen
        snakeGame.paintComponent(g); // Draw the game
    }

    // Allow child components to resize dynamically
    public void attachResizeListener(JPanel parentPanel) {
        parentPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                revalidate(); // Refresh layout
                repaint(); // Trigger a repaint
            }
        });
    }
}