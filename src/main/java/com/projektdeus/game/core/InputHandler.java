package com.projektdeus.game.core;

import com.projektdeus.game.ui.GalaxyViewerApp;
import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
    private double lastMouseX, lastMouseY;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void setup(long window, GalaxyViewerApp viewer) {
        Camera camera = viewer.getCamera();

        // --- Scroll Callback ---
        glfwSetScrollCallback(window, (w, xoff, yoff) -> {
            if (!viewer.isHovered()) return;
            camera.zoom += (float) yoff * 5.0f;
        });

        // --- Mouse Button Callback ---
        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            boolean pressed = (action == GLFW_PRESS);

            // Only allow the press if we are hovering the 3D area
            // BUT always allow the release so we don't get stuck "on"
            if (pressed && !viewer.isHovered()) return;

            if (button == GLFW_MOUSE_BUTTON_LEFT) leftPressed = pressed;
            if (button == GLFW_MOUSE_BUTTON_RIGHT) rightPressed = pressed;

            double[] x = new double[1], y = new double[1];
            glfwGetCursorPos(window, x, y);
            lastMouseX = x[0];
            lastMouseY = y[0];
        });

        // --- Cursor Position Callback ---
        glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
            float dx = (float) (xpos - lastMouseX);
            float dy = (float) (ypos - lastMouseY);

            // No hover check here! If we started the drag inside,
            // we want to be able to move the mouse outside and keep rotating.
            if (leftPressed) {
                camera.rotY += dx * 0.3f;
                camera.rotX += dy * 0.3f;
            } else if (rightPressed) {
                float panSensitivity = Math.abs(camera.zoom) / 1000.0f;
                camera.panX += dx * panSensitivity;
                camera.panY -= dy * panSensitivity;
            }

            lastMouseX = xpos;
            lastMouseY = ypos;
        });
    }
}