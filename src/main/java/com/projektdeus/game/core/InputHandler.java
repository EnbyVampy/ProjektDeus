package com.projektdeus.game.core;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
    private double lastMouseX, lastMouseY;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void setup(long window, Camera camera) {
        glfwSetScrollCallback(window, (w, xoff, yoff) -> {
            camera.zoom += yoff * 5.0f;
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT) leftPressed = (action == GLFW_PRESS);
            if (button == GLFW_MOUSE_BUTTON_RIGHT) rightPressed = (action == GLFW_PRESS);

            double[] x = new double[1], y = new double[1];
            glfwGetCursorPos(window, x, y);
            lastMouseX = x[0];
            lastMouseY = y[0];
        });

        glfwSetCursorPosCallback(window, (w, xpos, ypos) -> {
            float dx = (float) (xpos - lastMouseX);
            float dy = (float) (ypos - lastMouseY);

            if (leftPressed) {
                camera.rotY += dx * 0.3f;
                camera.rotX += dy * 0.3f;
            } else if (rightPressed) {
                // Adjusting pan speed based on zoom level so it doesn't feel too fast/slow
                float panSensitivity = Math.abs(camera.zoom) / 1000.0f;
                camera.panX += dx * panSensitivity;
                camera.panY -= dy * panSensitivity;
            }

            lastMouseX = xpos;
            lastMouseY = ypos;
        });
    }
}