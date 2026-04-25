package com.projektdeus.server.gui;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ServerGUI extends JFrame {
    private JTextArea terminalArea;

    public ServerGUI() {
        setTitle("Projekt Deus - Dedicated Server Console");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Terminal Style Setup
        terminalArea = new JTextArea();
        terminalArea.setBackground(Color.BLACK);
        terminalArea.setForeground(new Color(0, 255, 65)); // Classic Matrix/Terminal Green
        terminalArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        terminalArea.setEditable(false);
        terminalArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(terminalArea);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Redirect System.out and System.err to the JTextArea
        redirectSystemStreams();

        setVisible(true);
        System.out.println("GUI Terminal Initialized.");
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) {
                updateTerminal(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) {
                updateTerminal(new String(b, off, len));
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

    private void updateTerminal(String text) {
        SwingUtilities.invokeLater(() -> {
            terminalArea.append(text);
            // Auto-scroll to bottom
            terminalArea.setCaretPosition(terminalArea.getDocument().getLength());
        });
    }

    public void log(String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("[" + timestamp + "] " + message);
    }
}