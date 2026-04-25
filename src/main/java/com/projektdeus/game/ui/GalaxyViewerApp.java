package com.projektdeus.game.ui;

import com.projektdeus.game.core.*;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import static org.lwjgl.opengl.GL11.*;

public class GalaxyViewerApp {
    public ImBoolean visible = new ImBoolean(true);
    private boolean hovered = false;
    private int selectedStarIdx = -1;

    private final FrameBuffer fb;
    private final Renderer renderer = new Renderer();
    private final Camera camera = new Camera();

    public GalaxyViewerApp() {
        this.fb = new FrameBuffer(800, 600);
    }

    public void render(GalaxyData data, GalaxySettings settings) {
        if (!visible.get()) return;

        ImGui.setNextWindowSizeConstraints(400, 300, Float.MAX_VALUE, Float.MAX_VALUE);
        ImGui.setNextWindowSize(800, 600, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Galaxy Viewer", visible)) {
            // HUD
            if (ImGui.button("Reset View")) {
                camera.setPivot(0, 0, 0);
                camera.resetPan();
                selectedStarIdx = -1;
            }
            ImGui.sameLine();
            String status = (selectedStarIdx == -1) ? "No Selection" : "Target: " + data.starNames[selectedStarIdx];
            ImGui.textColored(0.7f, 0.7f, 0.7f, 1.0f, status);

            float startX = ImGui.getCursorScreenPosX();
            float startY = ImGui.getCursorScreenPosY();
            ImVec2 availSize = ImGui.getContentRegionAvail();

            fb.rescale((int)availSize.x, (int)availSize.y);

            fb.bind();
            glViewport(0, 0, fb.width, fb.height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(0, 0, 0.01f, 1);

            updateProjection(fb.width, fb.height, 45f, 0.01f, 5000f);
            camera.update();
            camera.applyTransformation();

            float[] modelView = new float[16];
            float[] projection = new float[16];
            glGetFloatv(GL_MODELVIEW_MATRIX, modelView);
            glGetFloatv(GL_PROJECTION_MATRIX, projection);

            renderer.render(data, settings, selectedStarIdx);

            // Gizmo
            glClear(GL_DEPTH_BUFFER_BIT);
            int gizmoSize = 100;
            glViewport(fb.width - gizmoSize, 0, gizmoSize, gizmoSize);
            updateProjection(gizmoSize, gizmoSize, 30f, 0.1f, 10f);
            renderer.renderGizmo(camera);

            fb.unbind();

            ImGui.image(fb.getTextureId(), availSize.x, availSize.y, 0, 1, 1, 0);

            ImGui.setCursorScreenPos(startX, startY);
            ImGui.invisibleButton("galaxyViewport", availSize.x, availSize.y);
            hovered = ImGui.isItemHovered();

            // Left Click Selection
            if (hovered && ImGui.isMouseReleased(0)) {
                // Only select if it was a click, not a drag
                if (Math.hypot(ImGui.getMouseDragDeltaX(0), ImGui.getMouseDragDeltaY(0)) < 4.0f) {
                    handlePicking(data, modelView, projection, ImGui.getMousePosX() - startX, ImGui.getMousePosY() - startY);
                }
            }

            // NOTE: Right-click logic removed from here to allow InputHandler
            // to use it for panning without resetting the selection.

        } else {
            hovered = false;
        }
        ImGui.end();
    }

    private void handlePicking(GalaxyData data, float[] mv, float[] pj, float mouseX, float mouseY) {
        if (data.starPositions == null) return;
        int closestIdx = -1;
        float minScreenDist = 12.0f;

        for (int i = 0; i < data.currentStarCount; i++) {
            float[] screenPos = project(data.starPositions[i*3], data.starPositions[i*3+1], data.starPositions[i*3+2], mv, pj, fb.width, fb.height);
            if (screenPos != null) {
                float dist = (float) Math.hypot(mouseX - screenPos[0], mouseY - screenPos[1]);
                if (dist < minScreenDist) {
                    minScreenDist = dist;
                    closestIdx = i;
                }
            }
        }

        if (closestIdx != -1) {
            selectedStarIdx = closestIdx;
            camera.setPivot(data.starPositions[selectedStarIdx*3], data.starPositions[selectedStarIdx*3+1], data.starPositions[selectedStarIdx*3+2]);
            camera.resetPan();
        }
    }

    private float[] project(float x, float y, float z, float[] mv, float[] pj, int w, int h) {
        float[] in = {x, y, z, 1.0f};
        float[] out = multiplyMatrixVector(mv, in);
        out = multiplyMatrixVector(pj, out);
        if (out[3] == 0 || out[2] < -out[3]) return null;
        return new float[]{ (out[0]/out[3] * 0.5f + 0.5f) * w, (1.0f - (out[1]/out[3] * 0.5f + 0.5f)) * h };
    }

    private float[] multiplyMatrixVector(float[] m, float[] v) {
        return new float[]{
                m[0]*v[0] + m[4]*v[1] + m[8]*v[2] + m[12]*v[3],
                m[1]*v[0] + m[5]*v[1] + m[9]*v[2] + m[13]*v[3],
                m[2]*v[0] + m[6]*v[1] + m[10]*v[2] + m[14]*v[3],
                m[3]*v[0] + m[7]*v[1] + m[11]*v[2] + m[15]*v[3]
        };
    }

    private void updateProjection(int w, int h, float fov, float near, float far) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = (float) w / h;
        float f = (float) (1.0 / Math.tan(Math.toRadians(fov) / 2.0));
        glMultMatrixf(new float[]{f/aspect, 0, 0, 0, 0, f, 0, 0, 0, 0, (far+near)/(near-far), -1, 0, 0, (2*far*near)/(near-far), 0});
        glMatrixMode(GL_MODELVIEW);
    }

    public boolean isHovered() { return hovered; }
    public Camera getCamera() { return camera; }
}