package com.projektdeus.game.core;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    public void render(GalaxyData galaxy, GalaxySettings settings, int selectedIdx) {
        if (galaxy.starPositions == null) return;

        // Ground Plane is now essentially the "World Floor"
        if (settings.showFloor) drawWorldFloor(settings.armEnd);

        glPointSize(settings.starSize);
        glEnable(GL_POINT_SMOOTH);
        glBegin(GL_POINTS);
        for (int i = 0; i < galaxy.currentStarCount; i++) {
            glColor3f(galaxy.starColors[i * 3], galaxy.starColors[i * 3 + 1], galaxy.starColors[i * 3 + 2]);
            glVertex3f(galaxy.starPositions[i * 3], galaxy.starPositions[i * 3 + 1], galaxy.starPositions[i * 3 + 2]);
        }
        glEnd();

        if (selectedIdx >= 0) {
            drawSelectionRing(galaxy.starPositions[selectedIdx * 3],
                    galaxy.starPositions[selectedIdx * 3 + 1],
                    galaxy.starPositions[selectedIdx * 3 + 2], settings.starSize);
        }
    }

    public void renderGizmo(Camera cam) {
        glPushMatrix();
        glLoadIdentity();
        glTranslatef(0, 0, -5);

        // This only rotates with your mouse, NOT with the galaxy tilt
        // Making it a stable reference compass
        glRotatef(cam.rotX, 1, 0, 0);
        glRotatef(cam.rotY, 0, 1, 0);

        glLineWidth(2.0f);
        glBegin(GL_LINES);
        glColor3f(1, 0, 0); glVertex3f(0,0,0); glVertex3f(1,0,0); // X
        glColor3f(0, 1, 0); glVertex3f(0,0,0); glVertex3f(0,1,0); // Y
        glColor3f(0, 0.5f, 1); glVertex3f(0,0,0); glVertex3f(0,0,1); // Z
        glEnd();
        glLineWidth(1.0f);
        glPopMatrix();
    }

    private void drawSelectionRing(float x, float y, float z, float size) {
        glPushMatrix();
        glTranslatef(x, y, z);
        glColor3f(0, 1, 1);
        glBegin(GL_LINE_LOOP);
        for(int i=0; i<32; i++){
            double a = Math.PI*2*i/32;
            glVertex3f((float)Math.cos(a)*(size*0.1f+0.3f), 0, (float)Math.sin(a)*(size*0.1f+0.3f));
        }
        glEnd();
        glPopMatrix();
    }

    private void drawWorldFloor(float r) {
        glColor4f(0.2f, 0.2f, 0.3f, 0.4f);
        glBegin(GL_LINE_LOOP);
        for(int i=0; i<64; i++){
            double a = Math.PI*2*i/64;
            glVertex3f((float)Math.cos(a)*r, 0, (float)Math.sin(a)*r);
        }
        glEnd();
    }
}