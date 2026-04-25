package com.projektdeus.game;

import com.projektdeus.game.core.*;
import com.projektdeus.game.ui.*;
import com.projektdeus.game.network.NetworkManager;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;

    // ImGui Backend Handlers
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    // Game & Core Logic
    private final GalaxySettings settings = new GalaxySettings();
    private final GalaxyData galaxy = new GalaxyData();
    private final NetworkManager network = new NetworkManager();
    private final InputHandler input = new InputHandler();

    // OS Applications
    private ArchitectApp architect;
    private GalaxyViewerApp viewer;
    private final LoginApp loginApp = new LoginApp();

    // OS State
    private boolean loggedIn = false;

    public void run() {
        init();
        loop();
        destroy();
    }

    private void init() {
        // 1. Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // 2. Create Window
        window = glfwCreateWindow(1600, 900, "Projekt Deus - DeusOS", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable V-Sync
        GL.createCapabilities();

        // 3. Initialize ImGui
        ImGui.createContext();
        ImGui.getIO().addConfigFlags(ImGuiConfigFlags.DockingEnable); // Enable window snapping/docking

        // IMPORTANT: The 'true' here tells ImGui to install its own input callbacks.
        // This is what allows you to type in input fields.
        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 130");

        // 4. Initialize OS Apps
        architect = new ArchitectApp(settings);
        viewer = new GalaxyViewerApp();

        // Setup input (Polling based, won't conflict with ImGui)
        input.setup(window, viewer);

        // 5. OpenGL Global Settings
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);

        // Deep space background
        glClearColor(0.0f, 0.0f, 0.02f, 1.0f);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // A. Poll hardware events (Essential for ImGui typing/clicking)
            glfwPollEvents();

            // B. Handle Procedural Data Generation
            if (loggedIn && settings.needsRegeneration) {
                galaxy.regenerate(settings);
                settings.needsRegeneration = false;
            }

            // C. Start Rendering
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            imGuiGlfw.newFrame();
            ImGui.newFrame();

            if (!loggedIn) {
                // --- LOGIN STATE ---
                loginApp.render(network, () -> loggedIn = true);
            } else {
                // --- OS STATE ---
                renderMainMenuBar();
                setupDockSpace();

                // Render Open Apps
                architect.render();
                viewer.render(galaxy, settings);

                // Update Camera controls (Polling based)
                input.update(window, viewer);
            }

            // D. Finalize Frame
            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());
            glfwSwapBuffers(window);
        }
    }

    /**
     * Creates an invisible DockSpace that covers the whole OS background.
     */
    private void setupDockSpace() {
        float menuBarHeight = ImGui.getFrameHeight();
        int[] w = new int[1], h = new int[1];
        glfwGetWindowSize(window, w, h);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0, 0);

        int windowFlags = ImGuiWindowFlags.NoDocking | ImGuiWindowFlags.NoTitleBar
                | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize
                | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoBringToFrontOnFocus
                | ImGuiWindowFlags.NoNavFocus | ImGuiWindowFlags.NoBackground;

        // Position the desktop area just below the menu bar
        ImGui.setNextWindowPos(0, menuBarHeight);
        ImGui.setNextWindowSize(w[0], h[0] - menuBarHeight);

        ImGui.begin("DeusOS_Desktop", windowFlags);
        ImGui.popStyleVar();

        // PassthruCentralNode allows the OpenGL galaxy background to show through
        ImGui.dockSpace(ImGui.getID("MainDockSpace"), 0, 0, ImGuiDockNodeFlags.PassthruCentralNode);

        ImGui.end();
    }

    private void renderMainMenuBar() {
        if (ImGui.beginMainMenuBar()) {
            if (ImGui.beginMenu("OS")) {
                if (ImGui.menuItem("Log Out")) {
                    loggedIn = false;
                    network.disconnect();
                }
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
        network.disconnect();
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}