package com.projektdeus.game.ui;

import com.projektdeus.game.core.GalaxySettings;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.function.Consumer;

public class DebugUI extends JFrame {
    private final GalaxySettings settings;
    private final JPanel content;
    private boolean isAdjustingInternal = false;

    private JSlider sliInner, sliOuter, sliArms;
    private JLabel valInner, valOuter, valArms;

    public DebugUI(GalaxySettings s) {
        this.settings = s;
        setTitle("Galaxy Architect");
        setSize(520, 950);
        setLocation(50, 50);

        content = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnLoad = new JButton("Import");
        JButton btnSave = new JButton("Export");
        btnSave.addActionListener(e -> exportPreset());
        btnLoad.addActionListener(e -> importPreset());
        toolbar.add(btnLoad); toolbar.add(btnSave);
        add(toolbar, BorderLayout.NORTH);

        refreshUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    private void refreshUI() {
        content.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; gbc.gridx = 0;
        gbc.insets = new Insets(2, 5, 2, 5);

        addSection("GLOBAL SETTINGS", gbc);
        addSlider("Total Stars", 100, 15000, settings.starCount, 1.0f, v -> { settings.starCount = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Star Size", 1, 15, (int)settings.starSize, 1.0f, v -> settings.starSize = v, gbc);
        addSlider("Sersic Index", 1, 50, (int)(settings.sersicIndex * 10), 10.0f, v -> { settings.sersicIndex = v / 10f; settings.needsRegeneration = true; }, gbc);

        addSection("DEBUG VISUALS", gbc);
        addCheckbox("Show Static Ground Plane", settings.showFloor, v -> settings.showFloor = v, gbc);

        addSection("GALAXY ORIENTATION", gbc);
        addSlider("Galaxy Rot X", 0, 360, (int)settings.galaxyRotX, 1.0f, v -> settings.galaxyRotX = v, gbc);
        addSlider("Galaxy Rot Y", 0, 360, (int)settings.galaxyRotY, 1.0f, v -> settings.galaxyRotY = v, gbc);

        addSection("LINKED DENSITY DISTRIBUTION (%)", gbc);
        setupLinkedSliders(gbc);

        addSection("INNER CORE", gbc);
        addSlider("Radius", 1, 50, (int)settings.innerCoreRadius, 1.0f, v -> { settings.innerCoreRadius = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Squish", 1, 10, (int)(settings.innerCoreSquish * 10), 10.0f, v -> { settings.innerCoreSquish = v / 10f; settings.needsRegeneration = true; }, gbc);
        addSlider("Noise", 0, 20, (int)settings.innerCoreNoise, 1.0f, v -> { settings.innerCoreNoise = v; settings.needsRegeneration = true; }, gbc);

        addSection("OUTER CORE", gbc);
        addSlider("Radius", 5, 150, (int)settings.outerCoreRadius, 1.0f, v -> { settings.outerCoreRadius = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Squish", 1, 10, (int)(settings.outerCoreSquish * 10), 10.0f, v -> { settings.outerCoreSquish = v / 10f; settings.needsRegeneration = true; }, gbc);
        addSlider("Noise", 0, 40, (int)settings.outerCoreNoise, 1.0f, v -> { settings.outerCoreNoise = v; settings.needsRegeneration = true; }, gbc);

        addSection("ARM GEOMETRY", gbc);
        addSlider("Count", 1, 8, settings.armCount, 1.0f, v -> { settings.armCount = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Start", 0, 100, (int)settings.armStart, 1.0f, v -> { settings.armStart = v; settings.needsRegeneration = true; }, gbc);
        addSlider("End", 20, 500, (int)settings.armEnd, 1.0f, v -> { settings.armEnd = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Curve", 0, 100, (int)(settings.armCurve * 10), 10.0f, v -> { settings.armCurve = v / 10f; settings.needsRegeneration = true; }, gbc);

        addSection("ARM THICKNESS", gbc);
        addSlider("Inner Spread", 1, 100, (int)settings.armInnerSpread, 1.0f, v -> { settings.armInnerSpread = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Outer Spread", 1, 100, (int)settings.armOuterSpread, 1.0f, v -> { settings.armOuterSpread = v; settings.needsRegeneration = true; }, gbc);
        addSlider("Inner Squish", 1, 10, (int)(settings.armInnerSquish * 10), 10.0f, v -> { settings.armInnerSquish = v / 10f; settings.needsRegeneration = true; }, gbc);
        addSlider("Outer Squish", 1, 10, (int)(settings.armOuterSquish * 10), 10.0f, v -> { settings.armOuterSquish = v / 10f; settings.needsRegeneration = true; }, gbc);
        addSlider("Arm Noise", 0, 50, (int)settings.armNoise, 1.0f, v -> { settings.armNoise = v; settings.needsRegeneration = true; }, gbc);

        content.revalidate();
        content.repaint();
    }

    private void addCheckbox(String name, boolean current, Consumer<Boolean> op, GridBagConstraints gbc) {
        JCheckBox cb = new JCheckBox(name, current);
        cb.addActionListener(e -> op.accept(cb.isSelected()));
        content.add(cb, gbc);
    }

    private void setupLinkedSliders(GridBagConstraints gbc) {
        content.add(createLinkedRow("Inner Core", (int)settings.innerCorePercent, 1), gbc);
        content.add(createLinkedRow("Outer Core", (int)settings.outerCorePercent, 2), gbc);
        content.add(createLinkedRow("Arms", (int)settings.armsPercent, 3), gbc);
    }

    private JPanel createLinkedRow(String name, int val, int id) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel lbl = new JLabel(name); lbl.setPreferredSize(new Dimension(110, 20));
        JLabel vlbl = new JLabel(val + "%"); vlbl.setPreferredSize(new Dimension(45, 20));
        vlbl.setHorizontalAlignment(SwingConstants.RIGHT);
        vlbl.setFont(new Font("Monospaced", Font.BOLD, 12));

        JSlider sli = new JSlider(0, 100, val);
        if (id == 1) { sliInner = sli; valInner = vlbl; }
        if (id == 2) { sliOuter = sli; valOuter = vlbl; }
        if (id == 3) { sliArms = sli; valArms = vlbl; }

        sli.addChangeListener(e -> {
            if (isAdjustingInternal) return;
            adjustLinkedDistribution(id, sli.getValue());
        });

        row.add(lbl, BorderLayout.WEST);
        row.add(sli, BorderLayout.CENTER);
        row.add(vlbl, BorderLayout.EAST);
        return row;
    }

    private void adjustLinkedDistribution(int sourceId, int newValue) {
        isAdjustingInternal = true;
        newValue = Math.max(0, Math.min(100, newValue));
        JSlider otherA = (sourceId == 1) ? sliOuter : sliInner;
        JSlider otherB = (sourceId == 3) ? sliOuter : sliArms;
        if (sourceId == 2) { otherA = sliInner; otherB = sliArms; }

        int remaining = 100 - newValue;
        int currentOtherSum = otherA.getValue() + otherB.getValue();

        if (currentOtherSum > 0) {
            float factor = (float) remaining / currentOtherSum;
            int newValA = Math.round(otherA.getValue() * factor);
            otherA.setValue(newValA);
            otherB.setValue(remaining - newValA);
        } else {
            otherA.setValue(remaining / 2);
            otherB.setValue(remaining - (remaining / 2));
        }

        settings.innerCorePercent = sliInner.getValue();
        settings.outerCorePercent = sliOuter.getValue();
        settings.armsPercent = sliArms.getValue();
        valInner.setText(sliInner.getValue() + "%");
        valOuter.setText(sliOuter.getValue() + "%");
        valArms.setText(sliArms.getValue() + "%");

        settings.needsRegeneration = true;
        isAdjustingInternal = false;
    }

    private void addSlider(String name, int min, int max, int current, float div, Consumer<Integer> op, GridBagConstraints gbc) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel lbl = new JLabel(name); lbl.setPreferredSize(new Dimension(110, 20));
        JLabel vlbl = new JLabel(String.format("%.1f", current / div));
        vlbl.setPreferredSize(new Dimension(45, 20));
        vlbl.setHorizontalAlignment(SwingConstants.RIGHT);
        vlbl.setFont(new Font("Monospaced", Font.BOLD, 12));

        JSlider sli = new JSlider(min, max, current);
        sli.addChangeListener(e -> {
            op.accept(sli.getValue());
            vlbl.setText(String.format("%.1f", sli.getValue() / div));
        });

        row.add(lbl, BorderLayout.WEST);
        row.add(sli, BorderLayout.CENTER);
        row.add(vlbl, BorderLayout.EAST);
        content.add(row, gbc);
    }

    private void addSection(String title, GridBagConstraints gbc) {
        JLabel l = new JLabel(title);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(new Color(0, 102, 204));
        l.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 0));
        content.add(l, gbc);
    }

    private void exportPreset() {
        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try { settings.saveToFile(fc.getSelectedFile()); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    private void importPreset() {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try { settings.loadFromFile(fc.getSelectedFile()); refreshUI(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}