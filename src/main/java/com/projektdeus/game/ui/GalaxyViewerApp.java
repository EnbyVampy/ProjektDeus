package com.projektdeus.game.ui;

import com.projektdeus.game.core.*;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import static org.lwjgl.opengl.GL11.*;

public class GalaxyViewerApp {
    public ImBoolean visible = new ImBoolean(true);
    private boolean hovered = false; // Is the mouse over the 3D area?

    private final FrameBuffer fb;
    private final Renderer renderer = new Renderer();
    private final Camera camera = new Camera();

    public GalaxyViewerApp() {
        this.fb = new FrameBuffer(600, 400);
    }

    public void render(GalaxyData data, GalaxySettings settings) {
        if (!visible.get()) return;

        ImGui.setNextWindowSize(600, 400, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Galaxy Viewer", visible)) {
            float startX = ImGui.getCursorScreenPosX();
            float startY = ImGui.getCursorScreenPosY();

            ImVec2 size = ImGui.getContentRegionAvail();
            fb.rescale((int)size.x, (int)size.y);

            // 1. Render 3D Scene
            fb.bind();
            glViewport(0, 0, fb.width, fb.height);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glClearColor(0, 0, 0.01f, 1);
            updateProjection(fb.width, fb.height);
            camera.applyTransformation();
            renderer.render(data, settings);
            fb.unbind();

            // 2. Draw the 3D Texture
            ImGui.image(fb.getTextureId(), size.x, size.y, 0, 1, 1, 0);

            // 3. LAYER AN INVISIBLE BUTTON
            // This blocks the window from being dragged when clicking the galaxy
            ImGui.setCursorScreenPos(startX, startY);
            ImGui.invisibleButton("galaxyViewport", size.x, size.y);

            // We use isItemHovered() for the InputHandler to decide if it can start a drag
            hovered = ImGui.isItemHovered();

        } else {
            hovered = false;
        }
        ImGui.end();
    }

    private void updateProjection(int w, int h) {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = (float) w / h;
        float f = (float) (1.0 / Math.tan(Math.toRadians(45.0) / 2.0));
        glMultMatrixf(new float[]{f/aspect, 0, 0, 0, 0, f, 0, 0, 0, 0, 1000.1f/-999.9f, -1, 0, 0, (2*1000*0.1f)/-999.9f, 0});
        glMatrixMode(GL_MODELVIEW);
    }

    public boolean isHovered() { return hovered; }
    public Camera getCamera() { return camera; }
}