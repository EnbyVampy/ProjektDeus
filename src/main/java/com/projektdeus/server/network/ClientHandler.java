package com.projektdeus.server.network;

import com.projektdeus.server.logic.AccountManager;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final AccountManager accountManager;

    public ClientHandler(Socket socket, AccountManager accountManager) {
        this.socket = socket;
        this.accountManager = accountManager;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String input;
            while ((input = in.readLine()) != null) {
                String[] parts = input.split("\\|");
                if (parts.length < 3) continue;

                String command = parts[0];
                String user = parts[1];
                String pass = parts[2];

                if (command.equals("LOGIN")) {
                    if (accountManager.login(user, pass)) {
                        System.out.println("User Logged In: " + user);
                        out.println("AUTH_SUCCESS");
                    } else {
                        out.println("AUTH_FAIL|Invalid Username or Password");
                    }
                } else if (command.equals("SIGNUP")) {
                    if (accountManager.register(user, pass)) {
                        System.out.println("New User Registered: " + user);
                        out.println("AUTH_SUCCESS");
                    } else {
                        out.println("AUTH_FAIL|Username already exists");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Connection lost with: " + socket.getInetAddress());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}