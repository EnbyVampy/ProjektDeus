package com.projektdeus.game.core;

import java.util.Random;

public class GalaxyGenerator {
    private static final Random rand = new Random();

    public static void generate(GalaxyData data, GalaxySettings s) {
        for (int i = 0; i < s.starCount; i++) {
            float[] pos;
            float[] col;

            float roll = rand.nextFloat();
            if (roll < 0.15f) { // Inner Core
                pos = generateCore(s.innerCoreRadius, s.innerCoreSquish, s.innerCoreNoise, s.sersicIndex);
                col = new float[]{1.0f, 1.0f, 0.8f};
            } else if (roll < 0.40f) { // Outer Core
                pos = generateCore(s.outerCoreRadius, s.outerCoreSquish, s.outerCoreNoise, s.sersicIndex);
                col = new float[]{1.0f, 0.8f, 0.5f};
            } else { // Arms
                pos = generateSpiralStar(s);
                col = getArmColor(pos);
            }

            data.starPositions[i * 3] = pos[0];
            data.starPositions[i * 3 + 1] = pos[1];
            data.starPositions[i * 3 + 2] = pos[2];
            data.starColors[i * 3] = col[0];
            data.starColors[i * 3 + 1] = col[1];
            data.starColors[i * 3 + 2] = col[2];
        }
    }

    private static float[] generateCore(float radius, float squish, float noise, float sersic) {
        float p = (float) Math.pow(rand.nextFloat(), sersic);
        float r = p * radius;
        double theta = rand.nextDouble() * Math.PI * 2;
        double phi = rand.nextDouble() * Math.PI;

        float x = (float) (r * Math.sin(phi) * Math.cos(theta));
        float z = (float) (r * Math.cos(phi));
        float y = (float) (r * Math.sin(phi) * Math.sin(theta) * squish);

        // Circular Noise to avoid "Cubed" look
        float[] n = getCircularNoise(noise);
        return new float[]{x + n[0], y + n[1] * squish, z + n[2]};
    }

    private static float[] generateSpiralStar(GalaxySettings s) {
        float t = (float) Math.pow(rand.nextFloat(), s.sersicIndex);
        float dist = s.armStart + t * (s.armEnd - s.armStart);

        float armOffset = rand.nextInt(s.armCount) * (float)(Math.PI * 2.0 / s.armCount);
        float angle = t * (s.armCurve * (float)Math.PI * 2.0f) + armOffset;

        float currentSpread = s.armInnerSpread + t * (s.armOuterSpread - s.armInnerSpread);
        float currentSquish = s.armInnerSquish + t * (s.armOuterSquish - s.armInnerSquish);

        // Circular internal arm spread
        float angleInt = rand.nextFloat() * (float)Math.PI * 2;
        float rInt = rand.nextFloat() * currentSpread;

        float offsetX = (float)Math.cos(angleInt) * rInt;
        float offsetZ = (float)Math.sin(angleInt) * rInt;
        float offsetY = (rand.nextFloat() - 0.5f) * currentSpread * currentSquish;

        float[] n = getCircularNoise(s.armNoise);
        return new float[]{
                (float) (Math.cos(angle) * dist) + offsetX + n[0],
                offsetY + n[1] * currentSquish,
                (float) (Math.sin(angle) * dist) + offsetZ + n[2]
        };
    }

    private static float[] getCircularNoise(float max) {
        double theta = rand.nextDouble() * Math.PI * 2;
        float r = rand.nextFloat() * max;
        return new float[]{(float)Math.cos(theta)*r, (rand.nextFloat()-0.5f)*max, (float)Math.sin(theta)*r};
    }

    private static float[] getArmColor(float[] pos) {
        float r = rand.nextFloat();
        if (r < 0.4f) return new float[]{0.6f, 0.8f, 1.0f}; // Blue
        if (r < 0.7f) return new float[]{1.0f, 1.0f, 1.0f}; // White
        return new float[]{1.0f, 0.7f, 0.4f}; // Orange
    }
}