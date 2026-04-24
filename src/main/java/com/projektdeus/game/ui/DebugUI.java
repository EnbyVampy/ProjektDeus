package com.projektdeus.game.ui;

import com.projektdeus.game.core.GalaxySettings;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DebugUI extends JFrame {
    private final GalaxySettings settings;
    private final JPanel content;

    public DebugUI(GalaxySettings s) {
        this.settings = s;
        setTitle("Galaxy Architect");
        setSize(500, 800);

        content = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Top Buttons
        JPanel topPanel = new JPanel();
        JButton btnLoad = new JButton("Import Preset");
        JButton btnSave = new JButton("Export Preset");

        btnSave.addActionListener(e -> exportPreset());
        btnLoad.addActionListener(e -> importPreset());

        topPanel.add(btnLoad);
        topPanel.add(btnSave);
        add(topPanel, BorderLayout.NORTH);

        refreshUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void refreshUI() {
        content.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        addSection("GLOBAL", gbc);
        addSlider("Stars", 1000, 300000, settings.starCount, 1, v -> settings.starCount = v, gbc);
        addSlider("Sersic", 1, 50, (int)(settings.sersicIndex*10), 10, v -> settings.sersicIndex = v/10f, gbc);

        addSection("INNER CORE", gbc);
        addSlider("Radius", 1, 50, (int)settings.innerCoreRadius, 1, v -> settings.innerCoreRadius = v, gbc);
        addSlider("Squish", 1, 10, (int)(settings.innerCoreSquish*10), 10, v -> settings.innerCoreSquish = v/10f, gbc);

        addSection("OUTER CORE", gbc);
        addSlider("Radius", 5, 150, (int)settings.outerCoreRadius, 1, v -> settings.outerCoreRadius = v, gbc);
        addSlider("Squish", 1, 10, (int)(settings.outerCoreSquish*10), 10, v -> settings.outerCoreSquish = v/10f, gbc);

        addSection("ARM PATH", gbc);
        addSlider("Count", 1, 8, settings.armCount, 1, v -> settings.armCount = v, gbc);
        addSlider("Start", 0, 100, (int)settings.armStart, 1, v -> settings.armStart = v, gbc);
        addSlider("End", 20, 500, (int)settings.armEnd, 1, v -> settings.armEnd = v, gbc);
        addSlider("Curve", 0, 100, (int)(settings.armCurve*10), 10, v -> settings.armCurve = v/10f, gbc);

        addSection("ARM WIDTH/SLOPE", gbc);
        addSlider("In Spread", 1, 100, (int)settings.armInnerSpread, 1, v -> settings.armInnerSpread = v, gbc);
        addSlider("Out Spread", 1, 100, (int)settings.armOuterSpread, 1, v -> settings.armOuterSpread = v, gbc);
        addSlider("In Squish", 1, 10, (int)(settings.armInnerSquish*10), 10, v -> settings.armInnerSquish = v/10f, gbc);
        addSlider("Out Squish", 1, 10, (int)(settings.armOuterSquish*10), 10, v -> settings.armOuterSquish = v/10f, gbc);

        content.revalidate();
        content.repaint();
    }

    private void addSection(String title, GridBagConstraints gbc) {
        JLabel l = new JLabel(title);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        l.setBorder(BorderFactory.createEmptyBorder(10, 5, 2, 0));
        content.add(l, gbc);
    }

    private void addSlider(String name, int min, int max, int current, float divisor, java.util.function.Consumer<Integer> op, GridBagConstraints gbc) {
        JPanel row = new JPanel(new BorderLayout(5, 0));
        JLabel lblName = new JLabel(name);
        lblName.setPreferredSize(new Dimension(80, 20));

        JLabel lblVal = new JLabel(String.valueOf(current / divisor));
        lblVal.setPreferredSize(new Dimension(45, 20));
        lblVal.setHorizontalAlignment(SwingConstants.RIGHT);

        JSlider slider = new JSlider(min, max, current);
        slider.addChangeListener(e -> {
            op.accept(slider.getValue());
            lblVal.setText(String.valueOf(slider.getValue() / divisor));
            settings.needsRegeneration = true;
        });

        row.add(lblName, BorderLayout.WEST);
        row.add(slider, BorderLayout.CENTER);
        row.add(lblVal, BorderLayout.EAST);

        content.add(row, gbc);
    }

    private void exportPreset() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                settings.saveToFile(fc.getSelectedFile());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage());
            }
        }
    }

    private void importPreset() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                settings.loadFromFile(fc.getSelectedFile());
                refreshUI(); // Re-sync sliders with loaded values
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading: " + ex.getMessage());
            }
        }
    }
}