package us.projektdeus.client.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private String keyPressed = ""; // Variable to store the current key being pressed

    public KeyHandler() {
        // Default constructor
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required by KeyListener
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP -> keyPressed = "W";//Up
            case KeyEvent.VK_LEFT -> keyPressed = "A";//Left
            case KeyEvent.VK_DOWN -> keyPressed = "S";//Down
            case KeyEvent.VK_RIGHT -> keyPressed = "D";//Right
            case KeyEvent.VK_P -> keyPressed = "P";//Pause
            case KeyEvent.VK_R -> keyPressed = "";//Unused
            case KeyEvent.VK_ESCAPE -> keyPressed = "quit";
            default -> keyPressed = ""; // Reset if unhandled key
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Reset the key variable when the key is released
        keyPressed = "";
    }

    // Getter to expose the current key being pressed
    public String getKeyPressed() {
        return keyPressed;
    }
}