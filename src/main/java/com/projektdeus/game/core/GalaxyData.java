package com.projektdeus.game.core;

public class GalaxyData {
    public float[] starPositions;
    public float[] starColors;
    public float[] starSizes; // New: unique size for every star
    public int currentStarCount;

    public void regenerate(GalaxySettings settings) {
        this.currentStarCount = settings.starCount;
        this.starPositions = new float[currentStarCount * 3];
        this.starColors = new float[currentStarCount * 3];
        this.starSizes = new float[currentStarCount]; // One float per star

        GalaxyGenerator.generate(this, settings);
    }
}