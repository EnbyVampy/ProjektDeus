package com.projektdeus.game;

import com.projektdeus.game.core.*;
import com.projektdeus.game.ui.DebugUI;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    private long window;
    private int width = 1280, height = 720;
    private final GalaxySettings settings = new GalaxySettings();
    private final GalaxyData galaxy = new GalaxyData();
    private final Camera camera = new Camera();
    private final Renderer renderer = new Renderer();
    private final InputHandler input = new InputHandler();

    public void run() {
        init();
        new DebugUI(settings);
        loop();
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init() {
        if (!glfwInit()) throw new IllegalStateException("GLFW failed");
        window = glfwCreateWindow(width, height, "Galaxy Progenitor", NULL, NULL);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        input.setup(window, camera);
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> updateProjection(w, h));
        updateProjection(width, height);
        glEnable(GL_DEPTH_TEST);
        glClearColor(0, 0, 0.02f, 1);
    }

    private void updateProjection(int w, int h) {
        glViewport(0, 0, w, h);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = (float) w / (float) h;
        float f = (float) (1.0 / Math.tan(Math.toRadians(45.0) / 2.0));
        glMultMatrixf(new float[]{f/aspect,0,0,0, 0,f,0,0, 0,0,1000.1f/-999.9f,-1, 0,0,(2*1000*0.1f)/-999.9f,0});
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            if (settings.needsRegeneration) {
                galaxy.regenerate(settings);
                settings.needsRegeneration = false;
            }
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.applyTransformation();
            renderer.render(galaxy, settings);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public static void main(String[] args) { new Main().run(); }
}