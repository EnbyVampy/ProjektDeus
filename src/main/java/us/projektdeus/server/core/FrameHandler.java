package us.projektdeus.server.core;

import javax.swing.*;
import java.awt.*;
public class FrameHandler {
    public static void main(String[] args) {createConsoleFrame();}
    private static JTextArea textArea; // Shared JTextArea for logs
    private static JTextField textField; // Shared JTextField for input
    public static void createConsoleFrame(){
        JFrame frame = new JFrame("Projekt Deus Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for positioning components
        textArea = new JTextArea();
        textArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(textArea); // Add to JScrollPane
        frame.add(scrollPane, BorderLayout.CENTER); // Add to the center region
        textField = new JTextField();
        frame.add(textField, BorderLayout.SOUTH); // Add to the bottom region
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setAlwaysOnTop(false);
    }
    public static JTextArea getTextArea() {return textArea;}
    public static JTextField getTextField() {return textField;}
}
