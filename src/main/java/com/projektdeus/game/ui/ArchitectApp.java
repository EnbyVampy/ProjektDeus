package com.projektdeus.game.ui;

import com.projektdeus.game.core.GalaxySettings;
import imgui.ImGui;
import imgui.type.ImBoolean;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;

public class ArchitectApp {
    private final GalaxySettings s;
    public ImBoolean visible = new ImBoolean(true);

    public ArchitectApp(GalaxySettings settings) {
        this.s = settings;
    }

    public void render() {
        if (!visible.get()) return;

        if (ImGui.begin("Galaxy Architect", visible)) {

            if (ImGui.collapsingHeader("Global Settings")) {
                int[] starCount = {s.starCount};
                if (ImGui.sliderInt("Star Count", starCount, 100, 15000)) {
                    s.starCount = starCount[0];
                    s.needsRegeneration = true;
                }

                float[] starSize = {s.starSize};
                if (ImGui.sliderFloat("Star Size", starSize, 1.0f, 15.0f)) s.starSize = starSize[0];

                float[] sersic = {s.sersicIndex};
                if (ImGui.sliderFloat("Sersic Index", sersic, 0.1f, 5.0f)) {
                    s.sersicIndex = sersic[0];
                    s.needsRegeneration = true;
                }
            }

            if (ImGui.collapsingHeader("Linked Density (%)")) {
                renderLinkedSliders();
            }

            if (ImGui.collapsingHeader("Core Architecture")) {
                ImGui.text("Inner Core");
                float[] icr = {s.innerCoreRadius};
                if (ImGui.sliderFloat("In Radius##1", icr, 1, 50)) { s.innerCoreRadius = icr[0]; s.needsRegeneration = true; }
                float[] ics = {s.innerCoreSquish};
                if (ImGui.sliderFloat("In Squish##1", ics, 0.1f, 1.0f)) { s.innerCoreSquish = ics[0]; s.needsRegeneration = true; }

                ImGui.separator();

                ImGui.text("Outer Core");
                float[] ocr = {s.outerCoreRadius};
                if (ImGui.sliderFloat("Out Radius##2", ocr, 5, 150)) { s.outerCoreRadius = ocr[0]; s.needsRegeneration = true; }
                float[] ocs = {s.outerCoreSquish};
                if (ImGui.sliderFloat("Out Squish##2", ocs, 0.1f, 1.0f)) { s.outerCoreSquish = ocs[0]; s.needsRegeneration = true; }
            }

            if (ImGui.collapsingHeader("Arm Morphology")) {
                int[] arms = {s.armCount};
                if (ImGui.sliderInt("Arm Count", arms, 1, 8)) { s.armCount = arms[0]; s.needsRegeneration = true; }
                float[] curve = {s.armCurve};
                if (ImGui.sliderFloat("Arm Curve", curve, 0, 10)) { s.armCurve = curve[0]; s.needsRegeneration = true; }
                float[] as = {s.armStart};
                if (ImGui.sliderFloat("Start Path", as, 0, 100)) { s.armStart = as[0]; s.needsRegeneration = true; }
                float[] ae = {s.armEnd};
                if (ImGui.sliderFloat("End Path", ae, 20, 500)) { s.armEnd = ae[0]; s.needsRegeneration = true; }
            }

            if (ImGui.collapsingHeader("Orientation & Debug")) {
                ImBoolean showFloor = new ImBoolean(s.showFloor);
                if (ImGui.checkbox("Show Static Ground Plane", showFloor)) s.showFloor = showFloor.get();

                float[] rotX = {s.galaxyRotX};
                if (ImGui.sliderFloat("Galaxy Rot X", rotX, 0, 360)) s.galaxyRotX = rotX[0];
                float[] rotY = {s.galaxyRotY};
                if (ImGui.sliderFloat("Galaxy Rot Y", rotY, 0, 360)) s.galaxyRotY = rotY[0];
            }

            ImGui.separator();

            // --- EXPORT LOGIC ---
            if (ImGui.button("Export Preset")) {
                String path = TinyFileDialogs.tinyfd_saveFileDialog("Export Galaxy Preset", "my_galaxy.props", null, "Galaxy Preset (*.props)");
                if (path != null) {
                    try {
                        s.saveToFile(new File(path));
                    } catch (Exception ex) {
                        System.err.println("Failed to save: " + ex.getMessage());
                    }
                }
            }

            ImGui.sameLine();

            // --- IMPORT LOGIC ---
            if (ImGui.button("Import Preset")) {
                String path = TinyFileDialogs.tinyfd_openFileDialog("Import Galaxy Preset", "", null, "Galaxy Preset (*.props)", false);
                if (path != null) {
                    try {
                        s.loadFromFile(new File(path));
                    } catch (Exception ex) {
                        System.err.println("Failed to load: " + ex.getMessage());
                    }
                }
            }
        }
        ImGui.end();
    }

    private void renderLinkedSliders() {
        float[] icp = {s.innerCorePercent};
        float[] ocp = {s.outerCorePercent};
        float[] ap = {s.armsPercent};

        if (ImGui.sliderFloat("Inner Core %", icp, 0, 100)) adjustLinked(1, icp[0]);
        if (ImGui.sliderFloat("Outer Core %", ocp, 0, 100)) adjustLinked(2, ocp[0]);
        if (ImGui.sliderFloat("Arms %", ap, 0, 100)) adjustLinked(3, ap[0]);
    }

    private void adjustLinked(int source, float newVal) {
        float remaining = 100f - newVal;
        if (source == 1) {
            float sum = s.outerCorePercent + s.armsPercent;
            s.innerCorePercent = newVal;
            if (sum > 0) {
                float f = remaining / sum;
                s.outerCorePercent *= f;
                s.armsPercent = remaining - s.outerCorePercent;
            } else {
                s.outerCorePercent = remaining / 2f; s.armsPercent = remaining / 2f;
            }
        } else if (source == 2) {
            float sum = s.innerCorePercent + s.armsPercent;
            s.outerCorePercent = newVal;
            if (sum > 0) {
                float f = remaining / sum;
                s.innerCorePercent *= f;
                s.armsPercent = remaining - s.innerCorePercent;
            } else {
                s.innerCorePercent = remaining / 2f; s.armsPercent = remaining / 2f;
            }
        } else {
            float sum = s.innerCorePercent + s.outerCorePercent;
            s.armsPercent = newVal;
            if (sum > 0) {
                float f = remaining / sum;
                s.innerCorePercent *= f;
                s.outerCorePercent = remaining - s.innerCorePercent;
            } else {
                s.innerCorePercent = remaining / 2f; s.outerCorePercent = remaining / 2f;
            }
        }
        s.needsRegeneration = true;
    }
}