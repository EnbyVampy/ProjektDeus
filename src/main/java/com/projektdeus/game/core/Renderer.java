package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void render(GalaxyData galaxy) {
        // Safety check: Don't render if arrays aren't allocated yet
        if (galaxy.starPositions == null || galaxy.starColors == null) return;

        glPointSize(1.0f);
        glBegin(GL_POINTS);
        for (int i = 0; i < galaxy.currentStarCount; i++) {
            glColor3f(galaxy.starColors[i*3], galaxy.starColors[i*3+1], galaxy.starColors[i*3+2]);
            glVertex3f(galaxy.starPositions[i*3], galaxy.starPositions[i*3+1], galaxy.starPositions[i*3+2]);
        }
        glEnd();
    }
}