package us.enbyvampy.TestSnakeGame.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FrameHandler {

    // Main Game Frame and GamePanel
    public void mainGameFramePanel() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        GameLoop gameLoop = new GameLoop();
        gameLoop.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        gameLoop.attachResizeListener(panel);
        gameLoop.start();

        panel.setName("GamePanel");
        panel.setLayout(new BorderLayout()); // Use absolute layout
        panel.setBackground(Color.DARK_GRAY);
        panel.setBorder(null);
        panel.setBounds(0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight());
        panel.add(gameLoop, BorderLayout.CENTER);

        frame.setTitle("GameFrame");
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true); // Allow resizing to test responsiveness
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension frameSize = frame.getSize();
                for (Component child : panel.getComponents()) {
                    child.setBounds(0, 0, panel.getWidth(), panel.getHeight()); // Match GameLoop size
                }
                panel.revalidate();
                panel.repaint();
            }
        });

    }
}