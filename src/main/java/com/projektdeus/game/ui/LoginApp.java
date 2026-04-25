package com.projektdeus.game.ui;

import com.projektdeus.game.network.NetworkManager;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImString;

public class LoginApp {
    private final ImString ip = new ImString("127.0.0.1", 128);
    private final ImString port = new ImString("4545", 16);
    private final ImString username = new ImString(128);
    private final ImString password = new ImString(128);
    private String errorMessage = "";

    public interface LoginCallback { void onLoginSuccess(); }

    public void render(NetworkManager net, LoginCallback callback) {
        // 1. Setup UI constants for perfect centering
        float windowWidth = 330;
        float windowHeight = 260;
        float padding = 5.0f;
        float itemSpacing = 4.0f;
        // Calculation: (Total Width - (Left Padding + Right Padding) - Gap between boxes) / 2
        float boxWidth = (windowWidth - (padding * 2) - itemSpacing) / 2.0f;

        // 2. Position and Style the Window
        ImGui.setNextWindowPos(
                ImGui.getIO().getDisplaySizeX() * 0.5f - (windowWidth / 2),
                ImGui.getIO().getDisplaySizeY() * 0.5f - (windowHeight / 2)
        );
        ImGui.setNextWindowSize(windowWidth, windowHeight);

        // Force exactly 5px padding from window edges
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, padding, padding);
        // Set small gap between the two boxes
        ImGui.pushStyleVar(ImGuiStyleVar.ItemSpacing, itemSpacing, 8.0f);

        int flags = ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize |
                ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoBackground;

        // We use a window without background to see our custom styled child or just the boxes
        if (ImGui.begin("DeusOS Login", flags)) {

            // Draw a header background manually or just text
            ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, 10);
            ImGui.button("SYSTEM AUTHENTICATION", windowWidth - (padding * 2), 0);
            ImGui.popStyleVar();

            ImGui.spacing();

            // Row 1: Server IP
            renderCenteredRow("Server IP", "##inputIp", ip, boxWidth, 0);

            // Row 2: Server Port
            renderCenteredRow("Server Port", "##inputPort", port, boxWidth, 0);

            ImGui.spacing();

            // Row 3: Username
            renderCenteredRow("Username", "##inputUser", username, boxWidth, 0);

            // Row 4: Password
            renderCenteredRow("Password", "##inputPass", password, boxWidth, ImGuiInputTextFlags.Password);

            // Error Message (Centered)
            if (!errorMessage.isEmpty()) {
                float textWidth = ImGui.calcTextSize(errorMessage).x;
                ImGui.setCursorPosX((windowWidth - textWidth) / 2);
                ImGui.textColored(1.0f, 0.4f, 0.4f, 1.0f, errorMessage);
            } else {
                ImGui.dummy(0, 18);
            }

            // Row 5: Action Buttons
            // Styled slightly differently to indicate they are clickable
            ImGui.pushStyleColor(imgui.flag.ImGuiCol.Button, 0.2f, 0.4f, 0.6f, 1.0f);
            if (ImGui.button("LOGIN", boxWidth, 35)) {
                attemptAuth(net, "LOGIN", callback);
            }
            ImGui.sameLine();
            if (ImGui.button("SIGN UP", boxWidth, 35)) {
                attemptAuth(net, "SIGNUP", callback);
            }
            ImGui.popStyleColor();
        }
        ImGui.end();
        ImGui.popStyleVar(2); // Pop WindowPadding and ItemSpacing
    }

    /**
     * Renders a label (as a button for centered text) and an input field side-by-side.
     */
    private void renderCenteredRow(String label, String inputId, ImString inputVal, float width, int flags) {
        // Use a non-interactive button for the label to get centered text and "box" shape
        ImGui.pushStyleColor(imgui.flag.ImGuiCol.ButtonActive, 0.15f, 0.15f, 0.15f, 1.0f);
        ImGui.pushStyleColor(imgui.flag.ImGuiCol.ButtonHovered, 0.15f, 0.15f, 0.15f, 1.0f);
        ImGui.pushStyleColor(imgui.flag.ImGuiCol.Button, 0.15f, 0.15f, 0.15f, 1.0f);

        ImGui.button(label, width, 0); // Height 0 uses default frame height

        ImGui.popStyleColor(3);

        ImGui.sameLine();

        // Input box
        ImGui.setNextItemWidth(width);
        ImGui.inputText(inputId, inputVal, flags);
    }

    private void attemptAuth(NetworkManager net, String type, LoginCallback callback) {
        if (username.get().isEmpty() || password.get().isEmpty()) {
            errorMessage = "Fields cannot be empty";
            return;
        }

        if (!net.connect(ip.get(), Integer.parseInt(port.get()))) {
            errorMessage = "Server Offline";
            return;
        }

        String response = net.sendRequest(type + "|" + username.get() + "|" + password.get());
        if (response != null && response.startsWith("AUTH_SUCCESS")) {
            callback.onLoginSuccess();
        } else {
            errorMessage = (response != null && response.contains("|")) ? response.split("\\|")[1] : "Failure";
            net.disconnect();
        }
    }
}