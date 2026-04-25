package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    public float rotX = 20, rotY = 45;
    public float zoom = -100.0f;
    public float panX = 0, panY = 0;

    public float pivotX = 0, pivotY = 0, pivotZ = 0;
    private float targetPX = 0, targetPY = 0, targetPZ = 0;

    public void update() {
        float lerp = 0.15f;
        pivotX += (targetPX - pivotX) * lerp;
        pivotY += (targetPY - pivotY) * lerp;
        pivotZ += (targetPZ - pivotZ) * lerp;
    }

    public void applyTransformation() {
        glLoadIdentity();
        // 1. User Screen Pan & Zoom
        glTranslatef(panX, panY, zoom);
        // 2. User View Orbit
        glRotatef(rotX, 1, 0, 0);
        glRotatef(rotY, 0, 1, 0);
        // 3. Focal Point
        glTranslatef(-pivotX, -pivotY, -pivotZ);
    }

    public void setPivot(float x, float y, float z) {
        this.targetPX = x;
        this.targetPY = y;
        this.targetPZ = z;
    }

    public void resetPan() { this.panX = 0; this.panY = 0; }
}