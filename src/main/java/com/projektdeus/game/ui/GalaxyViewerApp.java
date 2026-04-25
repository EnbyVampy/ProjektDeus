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
            // UI Overlay
            if (ImGui.button("Reset View")) {
                camera.setPivot(0, 0, 0);
                camera.resetPan();
                selectedStarIdx = -1;
            }
            ImGui.sameLine();
            ImGui.text("Selection: " + (selectedStarIdx == -1 ? "None" : "Star #" + selectedStarIdx));

            float startX = ImGui.getCursorScreenPosX();
            float startY = ImGui.getCursorScreenPosY();
            ImVec2 size = ImGui.getContentRegionAvail();
            fb.rescale((int)size.x, (int)size.y);

            fb.bind();
            glViewport(0, 0, fb.width, fb.height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(0, 0, 0.01f, 1);

            updateProjection(fb.width, fb.height, 45f, 0.01f, 5000f);
            camera.update();

            // Smoothly pull pan back to center ONLY if a star was just selected
            // We can add a flag here if we want, but for now we'll just let the selection
            // call handle the snap.

            camera.applyTransformation(settings);

            float[] mv = new float[16]; float[] pj = new float[16];
            glGetFloatv(GL_MODELVIEW_MATRIX, mv);
            glGetFloatv(GL_PROJECTION_MATRIX, pj);

            renderer.render(data, settings, selectedStarIdx);

            // GIZMO PASS
            glClear(GL_DEPTH_BUFFER_BIT);
            int gizmoSize = 100;
            glViewport(fb.width - gizmoSize, 0, gizmoSize, gizmoSize);
            updateProjection(gizmoSize, gizmoSize, 30f, 0.1f, 10f);
            renderer.renderGizmo(camera, settings);

            fb.unbind();

            ImGui.image(fb.getTextureId(), size.x, size.y, 0, 1, 1, 0);

            ImGui.setCursorScreenPos(startX, startY);
            ImGui.invisibleButton("viewport", size.x, size.y);
            hovered = ImGui.isItemHovered();

            // Left click to select
            if (hovered && ImGui.isMouseReleased(0) && Math.hypot(ImGui.getMouseDragDeltaX(), ImGui.getMouseDragDeltaY()) < 4) {
                handlePicking(data, mv, pj, ImGui.getMousePosX() - startX, ImGui.getMousePosY() - startY);
            }
        } else {
            hovered = false;
        }
        ImGui.end();
    }

    private void handlePicking(GalaxyData data, float[] mv, float[] pj, float mx, float my) {
        if (data.starPositions == null) return;
        int closest = -1; float minDist = 15f;
        for (int i = 0; i < data.currentStarCount; i++) {
            float[] screen = project(data.starPositions[i*3], data.starPositions[i*3+1], data.starPositions[i*3+2], mv, pj, fb.width, fb.height);
            if (screen != null) {
                float d = (float) Math.hypot(mx - screen[0], my - screen[1]);
                if (d < minDist) { minDist = d; closest = i; }
            }
        }
        if (closest != -1) {
            selectedStarIdx = closest;
            camera.setPivot(data.starPositions[closest*3], data.starPositions[closest*3+1], data.starPositions[closest*3+2]);
            camera.resetPan(); // Centering logic
        }
    }

    private float[] project(float x, float y, float z, float[] mv, float[] pj, int w, int h) {
        float[] in = {x, y, z, 1.0f};
        float[] out = multiply(mv, in); out = multiply(pj, out);
        if (out[3] == 0 || out[2] < -out[3]) return null;
        return new float[]{ (out[0]/out[3] * 0.5f + 0.5f) * w, (1.0f - (out[1]/out[3] * 0.5f + 0.5f)) * h };
    }

    private float[] multiply(float[] m, float[] v) {
        return new float[]{ m[0]*v[0]+m[4]*v[1]+m[8]*v[2]+m[12]*v[3], m[1]*v[0]+m[5]*v[1]+m[9]*v[2]+m[13]*v[3], m[2]*v[0]+m[6]*v[1]+m[10]*v[2]+m[14]*v[3], m[3]*v[0]+m[7]*v[1]+m[11]*v[2]+m[15]*v[3] };
    }

    private void updateProjection(int w, int h, float fov, float near, float far) {
        glMatrixMode(GL_PROJECTION); glLoadIdentity();
        float aspect = (float) w / h;
        float f = (float) (1.0 / Math.tan(Math.toRadians(fov) / 2.0));
        glMultMatrixf(new float[]{f/aspect,0,0,0, 0,f,0,0, 0,0,(far+near)/(near-far),-1, 0,0,(2*far*near)/(near-far),0});
        glMatrixMode(GL_MODELVIEW);
    }

    public boolean isHovered() { return hovered; }
    public Camera getCamera() { return camera; }
}