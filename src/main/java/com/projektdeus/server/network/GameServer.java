package com.projektdeus.server.network;

import com.projektdeus.server.logic.AccountManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread {
    private final int port;
    private ServerSocket serverSocket;
    private boolean running = false;
    private final List<ClientHandler> clients = new ArrayList<>();

    // Create one AccountManager for the whole server
    private final AccountManager accountManager = new AccountManager();

    public GameServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("Server started on port: " + port);

            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection: " + clientSocket.getInetAddress());

                // Pass the socket AND the accountManager to the handler
                ClientHandler handler = new ClientHandler(clientSocket, accountManager);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            if (running) System.err.println("Server Error: " + e.getMessage());
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}