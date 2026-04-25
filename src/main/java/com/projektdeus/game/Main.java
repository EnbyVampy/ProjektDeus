package com.projektdeus.game;

import com.projektdeus.game.core.*;
import com.projektdeus.game.ui.*;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    private long window;
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private final GalaxySettings settings = new GalaxySettings();
    private final GalaxyData galaxy = new GalaxyData();
    private ArchitectApp architect;
    private GalaxyViewerApp viewer;
    private final InputHandler input = new InputHandler();

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

        // --- NEW: ENABLE DOCKING ---
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable);
        ImGui.getStyle().setWindowRounding(0.0f); // Square windows look more like a pro tool
        ImGui.getStyle().setFrameRounding(2.0f);  // Slightly rounded buttons/sliders

        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 130");

        architect = new ArchitectApp(settings);
        viewer = new GalaxyViewerApp();
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

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            // 1. Render the Menu Bar FIRST so we can calculate its height if needed
            // (Though ImGui.getFrameHeight() usually handles this automatically)
            renderMainMenuBar();

            // 2. Setup the DockSpace with an offset
            setupDockSpace();

            // 3. Render Apps
            architect.render();
            viewer.render(galaxy, settings);

            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void setupDockSpace() {
        // Determine the height of the top menu bar
        float menuBarHeight = ImGui.getFrameHeight();

        // Get current GLFW window dimensions
        int[] w = new int[1], h = new int[1];
        glfwGetWindowSize(window, w, h);

        // 1. Remove all padding
        ImGui.pushStyleVar(imgui.flag.ImGuiStyleVar.WindowPadding, 0, 0);

        // 2. Window Flags
        int windowFlags = imgui.flag.ImGuiWindowFlags.NoDocking
                | imgui.flag.ImGuiWindowFlags.NoTitleBar
                | imgui.flag.ImGuiWindowFlags.NoCollapse
                | imgui.flag.ImGuiWindowFlags.NoResize
                | imgui.flag.ImGuiWindowFlags.NoMove
                | imgui.flag.ImGuiWindowFlags.NoBringToFrontOnFocus
                | imgui.flag.ImGuiWindowFlags.NoNavFocus
                | imgui.flag.ImGuiWindowFlags.NoBackground;

        // 3. SET POSITION TO START BELOW THE MENU BAR
        ImGui.setNextWindowPos(0, menuBarHeight);
        // SET SIZE TO TOTAL HEIGHT MINUS THE MENU BAR
        ImGui.setNextWindowSize(w[0], h[0] - menuBarHeight);

        ImGui.begin("DeusOS_Desktop_Background", windowFlags);
        ImGui.popStyleVar();

        // 4. Create the DockSpace
        int dockspaceID = ImGui.getID("MainDockSpace");
        // PassthruCentralNode allows the OpenGL background to be seen
        ImGui.dockSpace(dockspaceID, 0, 0, imgui.flag.ImGuiDockNodeFlags.PassthruCentralNode);

        ImGui.end();
    }

    private void renderMainMenuBar() {
        if (ImGui.beginMainMenuBar()) {
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