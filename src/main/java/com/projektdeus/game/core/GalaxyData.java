package com.projektdeus.game.core;

public class GalaxyData {
    public float[] starPositions;
    public float[] starColors;
    public int currentStarCount;

    public void regenerate(GalaxySettings settings) {
        this.currentStarCount = settings.starCount;
        // 3 floats per star (x, y, z)
        this.starPositions = new float[currentStarCount * 3];
        // 3 floats per star (r, g, b)
        this.starColors = new float[currentStarCount * 3];

        GalaxyGenerator.generate(this, settings);
    }
}