package us.projektdeus.server.core;

import java.io.IOException;
import java.net.ServerSocket;

public class ClientListener {

    private static final int PORT = 8080; // Replace with your desired port
    private static boolean running = false;

    public static void startListener() {
        if (running) {
            System.out.println("Server is already running.");
            return;
        }

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                running = true;
                System.out.println("Server started. Listening for connections on port " + PORT + "...");

                // Example: Accepting clients
                while (running) {
                    try {
                        System.out.println("Waiting for client connection...");
                        serverSocket.accept();
                        System.out.println("Client connected!");
                        // Handle client connection in a separate thread here
                    } catch (IOException e) {
                        if (running) {
                            System.err.println("Error accepting client connection: " + e.getMessage());
                        }
                    }
                }
                System.out.println("Server stopped.");
            } catch (IOException e) {
                System.err.println("Error starting the server: " + e.getMessage());
            } finally {
                running = false;
            }
        }).start();
    }

    public static void stopListener() {
        System.out.println("Stopping server listener...");
        running = false;
    }
}