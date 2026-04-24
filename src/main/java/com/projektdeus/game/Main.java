package com.projektdeus.game;

import com.projektdeus.game.core.*;
import com.projektdeus.game.ui.*;
import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window;
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private GalaxySettings settings = new GalaxySettings();
    private GalaxyData galaxy = new GalaxyData();
    private ArchitectApp architect;
    private GalaxyViewerApp viewer;
    private InputHandler input = new InputHandler();

    public void run() {
        init();
        loop();
        destroy();
    }

    private void init() {
        glfwInit();
        window = glfwCreateWindow(1600, 900, "DeusOS", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        ImGui.createContext();
        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 130");

        architect = new ArchitectApp(settings);
        viewer = new GalaxyViewerApp();

        // Pass the camera inside the viewer to the input handler
        input.setup(window, viewer);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            if (settings.needsRegeneration) {
                galaxy.regenerate(settings);
                settings.needsRegeneration = false;
            }

            // Start ImGui Frame
            glClear(GL_COLOR_BUFFER_BIT);
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            // OS Components
            renderMainMenuBar();
            architect.render();
            viewer.render(galaxy, settings);

            // Final Render
            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void renderMainMenuBar() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("OS")) {
                if (ImGui.menuItem("Exit")) glfwSetWindowShouldClose(window, true);
                ImGui.endMenu();
            }
            if (ImGui.beginMenu("Applications")) {
                if (ImGui.menuItem("Galaxy Architect")) architect.visible.set(true);
                if (ImGui.menuItem("Galaxy Viewer")) viewer.visible.set(true);
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
    }

    private void destroy() {
        imGuiGl3.dispose(); imGuiGlfw.dispose();
        ImGui.destroyContext();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) { new Main().run(); }
}