package com.projektdeus.game.network;

import java.io.*;
import java.net.Socket;

public class NetworkManager {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String sendRequest(String request) {
        try {
            out.println(request);
            return in.readLine();
        } catch (IOException e) {
            return "AUTH_FAIL|Server disconnected";
        }
    }

    public void disconnect() {
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
    }
}