package com.projektdeus.game.core;

import com.projektdeus.game.ui.GalaxyViewerApp;
import imgui.ImGui;
import imgui.ImGuiIO;

public class InputHandler {
    private float lastMouseX, lastMouseY;
    private boolean isDragging = false;

    public void setup(long window, GalaxyViewerApp viewer) {
        // No GLFW callbacks used here to prevent ImGui conflicts
    }

    public void update(long window, GalaxyViewerApp viewer) {
        ImGuiIO io = ImGui.getIO();
        Camera camera = viewer.getCamera();

        // 1. Handle Scrolling (Zoom)
        // Only zoom if the mouse is inside the viewer window
        if (viewer.isHovered()) {
            float wheel = io.getMouseWheel();
            if (wheel != 0) {
                float zoomSpeed = Math.abs(camera.zoom) * 0.1f;
                camera.zoom += wheel * Math.max(zoomSpeed, 0.5f);
                if (camera.zoom > -0.1f) camera.zoom = -0.1f;
            }
        }

        float mouseX = io.getMousePosX();
        float mouseY = io.getMousePosY();

        // 2. Detect Initial Click (The Anchor)
        // If we just pressed the button this frame, anchor the position
        // to prevent the "jump" caused by deltas from previous mouse positions.
        if (ImGui.isMouseClicked(0) || ImGui.isMouseClicked(1)) {
            if (viewer.isHovered()) {
                lastMouseX = mouseX;
                lastMouseY = mouseY;
                isDragging = true;
            }
        }

        // 3. Handle Dragging (Rotate / Pan)
        if (isDragging) {
            float dx = mouseX - lastMouseX;
            float dy = mouseY - lastMouseY;

            if (ImGui.isMouseDown(0)) { // Left Click: Rotate
                camera.rotY += dx * 0.3f;
                camera.rotX += dy * 0.3f;
            }
            else if (ImGui.isMouseDown(1)) { // Right Click: Pan
                float sensitivity = Math.abs(camera.zoom) / 1000f;
                camera.panX += dx * sensitivity;
                camera.panY -= dy * sensitivity;
            }

            // If all buttons released, stop dragging
            if (!ImGui.isMouseDown(0) && !ImGui.isMouseDown(1)) {
                isDragging = false;
            }
        }

        // Always update last positions while dragging or hovering
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }
}