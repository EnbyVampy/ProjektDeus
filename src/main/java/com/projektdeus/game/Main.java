package com.projektdeus.game;

import com.projektdeus.game.core.*;
import com.projektdeus.game.ui.DebugUI;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private GalaxySettings settings = new GalaxySettings();
    private GalaxyData galaxy = new GalaxyData();
    private Camera camera = new Camera();
    private Renderer renderer = new Renderer();
    private InputHandler input = new InputHandler();

    public void run() {
        init();
        new DebugUI(settings);
        loop();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        if (!glfwInit()) throw new IllegalStateException("Unable to init GLFW");
        window = glfwCreateWindow(1280, 720, "Galaxy Progenitor", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        input.setup(window, camera);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> updateProjection(w, h));

        // Initial Projection Setup
        updateProjection(1280, 720);

        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.02f, 1.0f);
    }

    private void updateProjection(int w, int h) {
        if (h == 0) h = 1;
        glViewport(0, 0, w, h);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        float aspect = (float) w / h;
        float fov = 45.0f;
        float near = 0.1f;
        float far = 5000.0f;

        // Manual Perspective Matrix
        float f = (float) (1.0 / Math.tan(Math.toRadians(fov) / 2.0));
        glMultMatrixf(new float[]{
                f/aspect, 0, 0, 0,
                0, f, 0, 0,
                0, 0, (far+near)/(near-far), -1,
                0, 0, (2*far*near)/(near-far), 0
        });

        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // Regeneration logic
            if (settings.needsRegeneration) {
                galaxy.regenerate(settings);
                settings.needsRegeneration = false;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.applyTransformation();
            renderer.render(galaxy);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}