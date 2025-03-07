package us.projektdeus.server.core;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConsoleHandler {
    public static void redirectConsole(JTextArea textArea, JTextField textField) {
        // Redirect System.out and System.err to the JTextArea
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Append output to the JTextArea
                textArea.append(String.valueOf((char) b));
                textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll
            }
        });
        System.setOut(printStream); // Redirect standard output
        System.setErr(printStream); // Redirect standard error

        // Add ActionListener to JTextField for capturing user input
        textField.addActionListener(e -> {
            String inputText = textField.getText();
            textField.setText(""); // Clear input after use

            // Check if the input is a command (starts with /)
            if (inputText.startsWith("/")) {
                Commands.executeCommand(inputText); // Handle the command
            } else {
                // If not a command, just log the input
                System.out.println("Unknow command \""+inputText+"\" \nType /help for help");
            }
        });
    }
}