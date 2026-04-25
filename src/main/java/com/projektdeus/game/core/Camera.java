package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    public float rotX = 20, rotY = 45;
    public float zoom = -100.0f;

    // The Flat Screen Pan (Sliding the view)
    public float panX = 0, panY = 0;

    // The 3D Focus Point (The Star)
    public float pivotX = 0, pivotY = 0, pivotZ = 0;
    private float targetPX = 0, targetPY = 0, targetPZ = 0;

    public void update() {
        // Smoothly glide to the target star
        float lerp = 0.15f;
        pivotX += (targetPX - pivotX) * lerp;
        pivotY += (targetPY - pivotY) * lerp;
        pivotZ += (targetPZ - pivotZ) * lerp;
    }

    public void applyTransformation(GalaxySettings s) {
        glLoadIdentity();

        // 1. First, apply Zoom and the "Flat" Screen Panning
        // This makes the galaxy move exactly where your mouse goes
        glTranslatef(panX, panY, zoom);

        // 2. Apply Camera Orbit (User rotation)
        glRotatef(rotX, 1, 0, 0);
        glRotatef(rotY, 0, 1, 0);

        // 3. Apply Galaxy internal tilt
        glRotatef(s.galaxyRotX, 1, 0, 0);
        glRotatef(s.galaxyRotY, 0, 1, 0);

        // 4. Center the coordinate system on the Star
        glTranslatef(-pivotX, -pivotY, -pivotZ);
    }

    public void setPivot(float x, float y, float z) {
        this.targetPX = x;
        this.targetPY = y;
        this.targetPZ = z;
    }

    public void resetPan() {
        this.panX = 0;
        this.panY = 0;
    }
}