package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Camera {
    public float rotX = 20, rotY = 45;
    public float panX = 0, panY = 0;
    public float zoom = -100.0f;

    public void applyTransformation() {
        glLoadIdentity();

        // To make panning screen-relative, we translate on the X and Y axes
        // AFTER the zoom but BEFORE the rotations are applied to the world.
        glTranslatef(panX, panY, zoom);

        // Then we rotate the world relative to that panned position
        glRotatef(rotX, 1, 0, 0);
        glRotatef(rotY, 0, 1, 0);
    }
}