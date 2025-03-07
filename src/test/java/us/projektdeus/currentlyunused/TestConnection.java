package us.projektdeus.currentlyunused;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TestConnection {

    // Main method to launch GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    // Method to create and display the GUI
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Test Connection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Add a label
        JLabel label = new JLabel("Enter Server Details:", SwingConstants.CENTER);
        frame.add(label, BorderLayout.NORTH);

        // Create input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Server IP:"));
        JTextField ipField = new JTextField("127.0.0.1");
        inputPanel.add(ipField);
        inputPanel.add(new JLabel("Port:"));
        JTextField portField = new JTextField("8080");
        inputPanel.add(portField);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Add a button to connect
        JButton connectButton = new JButton("Connect");
        frame.add(connectButton, BorderLayout.SOUTH);

        // Add button click functionality
        connectButton.addActionListener(e -> {
            String serverIP = ipField.getText();
            int port;
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid port number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Attempt to connect to the server
            try (Socket socket = new Socket(serverIP, port)) {
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println("Hello Server!");

                JOptionPane.showMessageDialog(frame, "Connection successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Failed to connect: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}