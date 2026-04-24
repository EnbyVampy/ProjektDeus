package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public void render(GalaxyData galaxy, GalaxySettings settings) {
        if (galaxy.starPositions == null) return;

        // Draw the static ground ONLY if enabled
        if (settings.showFloor) {
            drawStaticGround(settings.armEnd);
        }

        glPushMatrix();
        glRotatef(settings.galaxyRotX, 1, 0, 0);
        glRotatef(settings.galaxyRotY, 0, 1, 0);

        glPointSize(settings.starSize);
        glEnable(GL_POINT_SMOOTH);
        glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

        glBegin(GL_POINTS);
        for (int i = 0; i < galaxy.currentStarCount; i++) {
            glColor3f(galaxy.starColors[i * 3], galaxy.starColors[i * 3 + 1], galaxy.starColors[i * 3 + 2]);
            glVertex3f(galaxy.starPositions[i * 3], galaxy.starPositions[i * 3 + 1], galaxy.starPositions[i * 3 + 2]);
        }
        glEnd();

        glDisable(GL_POINT_SMOOTH);
        glPopMatrix();
    }

    private void drawStaticGround(float radius) {
        glColor4f(0.2f, 0.2f, 0.3f, 0.5f);
        glBegin(GL_LINE_LOOP);
        int segments = 100;
        for (int i = 0; i < segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            glVertex3f((float) Math.cos(angle) * radius, 0, (float) Math.sin(angle) * radius);
        }
        glEnd();

        glBegin(GL_LINES);
        float step = radius / 5.0f;
        for (float i = -radius; i <= radius; i += step) {
            glVertex3f(i, 0, -radius); glVertex3f(i, 0, radius);
            glVertex3f(-radius, 0, i); glVertex3f(radius, 0, i);
        }
        glEnd();
    }
}