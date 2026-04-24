package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void render(GalaxyData galaxy, GalaxySettings settings) {
        if (galaxy.starPositions == null) return;

        // Use the dynamic star size from settings
        glPointSize(settings.starSize);

        // Optional: Smooth the points so they appear as circles rather than squares
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

        glBegin(GL_POINTS);
        for (int i = 0; i < galaxy.currentStarCount; i++) {
            glColor3f(galaxy.starColors[i*3], galaxy.starColors[i*3+1], galaxy.starColors[i*3+2]);
            glVertex3f(galaxy.starPositions[i*3], galaxy.starPositions[i*3+1], galaxy.starPositions[i*3+2]);
        }
        glEnd();

        glDisable(GL_POINT_SMOOTH);
    }
}