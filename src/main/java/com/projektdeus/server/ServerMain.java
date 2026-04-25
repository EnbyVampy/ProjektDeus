package com.projektdeus.server;

import com.projektdeus.server.gui.ServerGUI;
import com.projektdeus.server.network.GameServer;

public class ServerMain {
    public static void main(String[] args) {
        boolean useGui = true;

        // Check for -nogui flag
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-nogui")) {
                useGui = false;
                break;
            }
        }

        if (useGui) {
            // Launch the Swing GUI
            // Note: The GUI class handles System.out redirection internally
            new ServerGUI();
        }

        System.out.println("========================================");
        System.out.println("      PROJEKT DEUS - DEDICATED SERVER    ");
        if (!useGui) System.out.println("           (HEADLESS MODE ACTIVE)       ");
        System.out.println("========================================");

        int port = 4545;
        GameServer server = new GameServer(port);
        server.start();

        // Shutdown handling
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Server shutting down...");
            server.stopServer();
        }));
    }
}