package com.projektdeus.game.core;

import java.io.*;
import java.util.Properties;

public class GalaxySettings {
    public volatile boolean needsRegeneration = true;

    // We make these public for easy access, but initialized with defaults
    public int starCount = 80000;
    public float sersicIndex = 1.0f;
    public float innerCoreRadius = 5.0f;
    public float innerCoreSquish = 0.9f;
    public float innerCoreNoise = 1.0f;
    public float outerCoreRadius = 20.0f;
    public float outerCoreSquish = 0.6f;
    public float outerCoreNoise = 3.0f;
    public int armCount = 4;
    public float armStart = 15.0f;
    public float armEnd = 120.0f;
    public float armCurve = 2.0f;
    public float armInnerSpread = 20.0f;
    public float armOuterSpread = 8.0f;
    public float armInnerSquish = 0.4f;
    public float armOuterSquish = 0.1f;
    public float armNoise = 2.0f;

    public void saveToFile(File file) throws IOException {
        Properties props = new Properties();
        props.setProperty("starCount", String.valueOf(starCount));
        props.setProperty("sersicIndex", String.valueOf(sersicIndex));
        props.setProperty("innerCoreRadius", String.valueOf(innerCoreRadius));
        props.setProperty("innerCoreSquish", String.valueOf(innerCoreSquish));
        props.setProperty("innerCoreNoise", String.valueOf(innerCoreNoise));
        props.setProperty("outerCoreRadius", String.valueOf(outerCoreRadius));
        props.setProperty("outerCoreSquish", String.valueOf(outerCoreSquish));
        props.setProperty("outerCoreNoise", String.valueOf(outerCoreNoise));
        props.setProperty("armCount", String.valueOf(armCount));
        props.setProperty("armStart", String.valueOf(armStart));
        props.setProperty("armEnd", String.valueOf(armEnd));
        props.setProperty("armCurve", String.valueOf(armCurve));
        props.setProperty("armInnerSpread", String.valueOf(armInnerSpread));
        props.setProperty("armOuterSpread", String.valueOf(armOuterSpread));
        props.setProperty("armInnerSquish", String.valueOf(armInnerSquish));
        props.setProperty("armOuterSquish", String.valueOf(armOuterSquish));
        props.setProperty("armNoise", String.valueOf(armNoise));

        try (FileOutputStream out = new FileOutputStream(file)) {
            props.store(out, "Galaxy Preset");
        }
    }

    public void loadFromFile(File file) throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(file)) {
            props.load(in);
        }
        starCount = Integer.parseInt(props.getProperty("starCount", "80000"));
        sersicIndex = Float.parseFloat(props.getProperty("sersicIndex", "1.0"));
        innerCoreRadius = Float.parseFloat(props.getProperty("innerCoreRadius", "5.0"));
        innerCoreSquish = Float.parseFloat(props.getProperty("innerCoreSquish", "0.9"));
        innerCoreNoise = Float.parseFloat(props.getProperty("innerCoreNoise", "1.0"));
        outerCoreRadius = Float.parseFloat(props.getProperty("outerCoreRadius", "20.0"));
        outerCoreSquish = Float.parseFloat(props.getProperty("outerCoreSquish", "0.6"));
        outerCoreNoise = Float.parseFloat(props.getProperty("outerCoreNoise", "3.0"));
        armCount = Integer.parseInt(props.getProperty("armCount", "4"));
        armStart = Float.parseFloat(props.getProperty("armStart", "15.0"));
        armEnd = Float.parseFloat(props.getProperty("armEnd", "120.0"));
        armCurve = Float.parseFloat(props.getProperty("armCurve", "2.0"));
        armInnerSpread = Float.parseFloat(props.getProperty("armInnerSpread", "20.0"));
        armOuterSpread = Float.parseFloat(props.getProperty("armOuterSpread", "8.0"));
        armInnerSquish = Float.parseFloat(props.getProperty("armInnerSquish", "0.4"));
        armOuterSquish = Float.parseFloat(props.getProperty("armOuterSquish", "0.1"));
        armNoise = Float.parseFloat(props.getProperty("armNoise", "2.0"));
        needsRegeneration = true;
    }
}