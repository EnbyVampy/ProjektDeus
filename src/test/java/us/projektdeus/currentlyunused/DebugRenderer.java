package us.projektdeus.currentlyunused;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.*;
import static org.lwjgl.opengl.GL11.*;

public class DebugRenderer {
    private long nvg; // NanoVG context
    private int fontHandle; // Font handle ID

    /**
     * Initialize NanoVG and load the debug font.
     */
    public void init() {
        System.out.println("Initializing NanoVG...");
        nvg = nvgCreate(NVG_ANTIALIAS | NVG_STENCIL_STROKES);
        if (nvg == 0) {
            throw new RuntimeException("Failed to create NanoVG context.");
        }

        // Use the class loader to load resources from the "src/main/resources" directory
        String fontFile = getClass().getClassLoader().getResource("assets/fonts/helvetica-pixelated.ttf").getPath();

        System.out.println("Loading font from: " + fontFile);

        fontHandle = nvgCreateFont(nvg, "debugFont", fontFile);
        if (fontHandle == -1) {
            throw new RuntimeException("Failed to load font.");
        }
    }


    /**
     * Render dynamic debug information, such as coordinates or messages.
     *
     * @param x        The x position where text starts.
     * @param y        The y position where text starts.
     * @param text     The text to render.
     * @param fontSize The size of the font.
     * @param r        Red color component (0-255).
     * @param g        Green color component (0-255).
     * @param b        Blue color component (0-255).
     * @param a        Alpha (transparency) component (0-255).
     */
    public void renderDebugText(float x, float y, String text, float fontSize, int r, int g, int b, int a) {
        NVGColor color = NVGColor.calloc();
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        nvgFontSize(nvg, fontSize);
        nvgFontFace(nvg, "debugFont");
        nvgFillColor(nvg, color);
        nvgText(nvg, x, y, text);

        color.free();
    }

    /**
     * Main rendering method for a given frame. Starts and ends a NanoVG frame.
     * Call this once per frame.
     *
     * @param width       The width of the screen or viewport.
     * @param height      The height of the screen or viewport.
     * @param coordinates The coordinates to display (e.g., "(x: 50, y: 100)").
     */
    public void renderFrame(int width, int height, String coordinates) {
        float pixelRatio = 1; // Adjust this if working on high-DPI screens
        nvgBeginFrame(nvg, width, height, pixelRatio);

        renderDebugText(10, 30, "Debug Coordinates: " + coordinates, 24.0f, 255, 255, 255, 255);
        renderDebugText(10, 60, "FPS: 60", 24.0f, 0, 255, 0, 255);

        nvgEndFrame(nvg);
    }

    /**
     * Release NanoVG resources. Call this on application shutdown.
     */
    public void cleanup() {
        nvgDelete(nvg);
    }

    /**
     * Main method for testing.
     */
    public static void main(String[] args) {
        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create a window
        int windowWidth = 800;
        int windowHeight = 600;
        long window = glfwCreateWindow(windowWidth, windowHeight, "NanoVG Test", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
                window,
                (vidmode.width() - windowWidth) / 2,
                (vidmode.height() - windowHeight) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); // Enable v-sync
        glfwShowWindow(window);

        // Initialize OpenGL
        GL.createCapabilities();

        // Define the clear color (e.g., dark gray)
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        // Create an instance of DebugRenderer
        DebugRenderer renderer = new DebugRenderer();
        renderer.init();

        long lastTime = System.nanoTime();
        int fps = 0;

        // Main loop
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            // Calculate FPS
            long currentTime = System.nanoTime();
            fps++;
            if (currentTime - lastTime >= 1_000_000_000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                lastTime = currentTime;
            }

            // Update the NanoVG frame
            renderer.renderFrame(windowWidth, windowHeight, "(x: 50, y: 100)");

            // Swap the frame buffers
            glfwSwapBuffers(window);

            // Poll for window events
            glfwPollEvents();
        }

        // Cleanup
        renderer.cleanup();
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}