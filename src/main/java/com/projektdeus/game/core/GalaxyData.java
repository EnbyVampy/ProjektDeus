package com.projektdeus.game.core;

public class GalaxyData {
    public float[] starPositions;
    public float[] starColors;
    public String[] starNames; // Stores procedural IDs like "XRU-019"
    public int currentStarCount;

    public void regenerate(GalaxySettings settings) {
        this.currentStarCount = settings.starCount;
        this.starPositions = new float[currentStarCount * 3];
        this.starColors = new float[currentStarCount * 3];
        this.starNames = new String[currentStarCount];

        GalaxyGenerator.generate(this, settings);
    }
}