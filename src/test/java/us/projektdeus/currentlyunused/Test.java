package us.projektdeus.currentlyunused;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Test {
    private long window;        // Window handle
    private int width = 800;                     // Settings for the window
    private int height = 600;
    private String title = "LWJGL Blank Project";

    public void init() {// Initialize GLFW
        if (!glfwInit()) {throw new IllegalStateException("Unable to initialize GLFW");}
        glfwDefaultWindowHints();           // Configure GLFW
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Keep the window hidden until fully configured
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Allow resizing
        window = glfwCreateWindow(width, height, title, 0, 0);                  // Create the window
        if (window == 0) {throw new RuntimeException("Failed to create the GLFW window");}
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (vidMode != null) {// Center the window on the primary monitor
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - width) / 2,
                    (vidMode.height() - height) / 2
            );
        }
        glfwMakeContextCurrent(window);// Make the OpenGL context current
        glfwSwapInterval(1);// Enable v-sync
        glfwShowWindow(window);// Show the window
        GL.createCapabilities();// Create OpenGL capabilities (requires an active OpenGL context)
    }


    public void loop() {                                                            //Main game/rendering loop.
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);// Set the clear color (e.g., dark gray)
        while (!glfwWindowShouldClose(window)) {// Run the rendering loop until the user attempts to close the window
            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);// Clear the framebuffer


            // Rendering can go here...


            glfwSwapBuffers(window);// Swap the color buffers
            glfwPollEvents();// Poll for window events
        }
    }



    public void cleanup() {         //Clean up resources and terminate.
        glfwDestroyWindow(window); // Destroy the window


        glfwTerminate();// Terminate GLFW
    }


    public static void main(String[] args) {
        Test app = new Test();
        app.init();       // Initialize GLFW and OpenGL
        app.loop();       // Start the rendering loop
        app.cleanup();    // Free resources and shutdown
    }
}