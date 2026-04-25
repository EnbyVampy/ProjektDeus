package com.projektdeus.game.core;

import com.projektdeus.game.ui.GalaxyViewerApp;
import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
    private double lastMouseX, lastMouseY;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    public void setup(long window, GalaxyViewerApp viewer) {
        Camera camera = viewer.getCamera();

        glfwSetScrollCallback(window, (w, xoff, yoff) -> {
            if (!viewer.isHovered()) return;
            float zoomSpeed = Math.abs(camera.zoom) * 0.1f;
            camera.zoom += (float) yoff * Math.max(zoomSpeed, 0.5f);
            if (camera.zoom > -0.1f) camera.zoom = -0.1f;
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> {
            boolean pressed = (action == GLFW_PRESS);
            if (pressed && !viewer.isHovered()) return;

            if (button == GLFW_MOUSE_BUTTON_LEFT) leftPressed = pressed;
            if (button == GLFW_MOUSE_BUTTON_RIGHT) rightPressed = pressed;

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
                // FLAT SCREEN PANNING
                // Moving mouse Right (+dx) increases panX (Galaxy moves Right)
                // Moving mouse Down (+dy) decreases panY (Galaxy moves Down)
                float sensitivity = Math.abs(camera.zoom) / 1000f;
                camera.panX += dx * sensitivity;
                camera.panY -= dy * sensitivity;
            }

            lastMouseX = xpos;
            lastMouseY = ypos;
        });
    }
}