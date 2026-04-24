package com.projektdeus.game.core;

import java.io.*;
import java.util.Properties;

public class GalaxySettings {
    public volatile boolean needsRegeneration = true;

    // Global
    public int starCount = 5000;
    public float starSize = 3.0f;
    public float sersicIndex = 1.0f;

    // Inner Core
    public float innerCoreRadius = 5.0f;
    public float innerCoreSquish = 0.9f;
    public float innerCoreNoise = 1.0f;

    // Outer Core
    public float outerCoreRadius = 20.0f;
    public float outerCoreSquish = 0.6f;
    public float outerCoreNoise = 3.0f;

    // Arm Path
    public int armCount = 4;
    public float armStart = 15.0f;
    public float armEnd = 120.0f;
    public float armCurve = 2.0f;

    // Arm Thickness/Slope
    public float armInnerSpread = 20.0f;
    public float armOuterSpread = 8.0f;
    public float armInnerSquish = 0.4f;
    public float armOuterSquish = 0.1f;
    public float armNoise = 2.0f;

    public void saveToFile(File file) throws IOException {
        Properties p = new Properties();
        p.setProperty("starCount", String.valueOf(starCount));
        p.setProperty("starSize", String.valueOf(starSize));
        p.setProperty("sersicIndex", String.valueOf(sersicIndex));
        p.setProperty("innerCoreRadius", String.valueOf(innerCoreRadius));
        p.setProperty("innerCoreSquish", String.valueOf(innerCoreSquish));
        p.setProperty("innerCoreNoise", String.valueOf(innerCoreNoise));
        p.setProperty("outerCoreRadius", String.valueOf(outerCoreRadius));
        p.setProperty("outerCoreSquish", String.valueOf(outerCoreSquish));
        p.setProperty("outerCoreNoise", String.valueOf(outerCoreNoise));
        p.setProperty("armCount", String.valueOf(armCount));
        p.setProperty("armStart", String.valueOf(armStart));
        p.setProperty("armEnd", String.valueOf(armEnd));
        p.setProperty("armCurve", String.valueOf(armCurve));
        p.setProperty("armInnerSpread", String.valueOf(armInnerSpread));
        p.setProperty("armOuterSpread", String.valueOf(armOuterSpread));
        p.setProperty("armInnerSquish", String.valueOf(armInnerSquish));
        p.setProperty("armOuterSquish", String.valueOf(armOuterSquish));
        p.setProperty("armNoise", String.valueOf(armNoise));
        try (FileOutputStream out = new FileOutputStream(file)) { p.store(out, "Galaxy Preset"); }
    }

    public void loadFromFile(File file) throws IOException {
        Properties p = new Properties();
        try (FileInputStream in = new FileInputStream(file)) { p.load(in); }
        starCount = Integer.parseInt(p.getProperty("starCount", "5000"));
        starSize = Float.parseFloat(p.getProperty("starSize", "3.0"));
        sersicIndex = Float.parseFloat(p.getProperty("sersicIndex", "1.0"));
        innerCoreRadius = Float.parseFloat(p.getProperty("innerCoreRadius", "5.0"));
        innerCoreSquish = Float.parseFloat(p.getProperty("innerCoreSquish", "0.9"));
        innerCoreNoise = Float.parseFloat(p.getProperty("innerCoreNoise", "1.0"));
        outerCoreRadius = Float.parseFloat(p.getProperty("outerCoreRadius", "20.0"));
        outerCoreSquish = Float.parseFloat(p.getProperty("outerCoreSquish", "0.6"));
        outerCoreNoise = Float.parseFloat(p.getProperty("outerCoreNoise", "3.0"));
        armCount = Integer.parseInt(p.getProperty("armCount", "4"));
        armStart = Float.parseFloat(p.getProperty("armStart", "15.0"));
        armEnd = Float.parseFloat(p.getProperty("armEnd", "120.0"));
        armCurve = Float.parseFloat(p.getProperty("armCurve", "2.0"));
        armInnerSpread = Float.parseFloat(p.getProperty("armInnerSpread", "20.0"));
        armOuterSpread = Float.parseFloat(p.getProperty("armOuterSpread", "8.0"));
        armInnerSquish = Float.parseFloat(p.getProperty("armInnerSquish", "0.4"));
        armOuterSquish = Float.parseFloat(p.getProperty("armOuterSquish", "0.1"));
        armNoise = Float.parseFloat(p.getProperty("armNoise", "2.0"));
        needsRegeneration = true;
    }
}