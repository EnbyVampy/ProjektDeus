package us.enbyvampy.TestSnakeGame.core;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private SnakeGame snakeGame; // Reference to the game instance

    public KeyHandler(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required by KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key press events
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                snakeGame.changeDirection(new Point(0, -1));
                break;
            case KeyEvent.VK_DOWN:
                snakeGame.changeDirection(new Point(0, 1));
                break;
            case KeyEvent.VK_LEFT:
                snakeGame.changeDirection(new Point(-1, 0));
                break;
            case KeyEvent.VK_RIGHT:
                snakeGame.changeDirection(new Point(1, 0));
                break;
            case KeyEvent.VK_P:
                snakeGame.togglePause(); // Pause or resume the game
                break;
            case KeyEvent.VK_R:
                snakeGame.restartGame(); // Restart the game
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used but required by KeyListener interface
    }
}