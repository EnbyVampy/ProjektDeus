package com.projektdeus.game.core;

import java.util.Random;

public class GalaxyGenerator {
    private static final Random rand = new Random();

    public static void generate(GalaxyData data, GalaxySettings s) {
        int innerCount = (int) (s.starCount * (s.innerCorePercent / 100.0f));
        int outerCount = (int) (s.starCount * (s.outerCorePercent / 100.0f));
        int armsCount = s.starCount - innerCount - outerCount;

        int ptr = 0;

        // 1. Inner Core
        for (int i = 0; i < innerCount; i++) {
            ptr = addStar(data, ptr, generateUniformSphere(s.innerCoreRadius, s.innerCoreSquish, s.innerCoreNoise, s.sersicIndex),
                    new float[]{1.0f, 1.0f, 0.8f});
        }

        // 2. Outer Core
        for (int i = 0; i < outerCount; i++) {
            ptr = addStar(data, ptr, generateUniformSphere(s.outerCoreRadius, s.outerCoreSquish, s.outerCoreNoise, s.sersicIndex),
                    new float[]{1.0f, 0.8f, 0.5f});
        }

        // 3. Arms
        // We generate a random phase for the whole galaxy to prevent axis-alignment
        float globalRotation = rand.nextFloat() * (float)Math.PI * 2;
        for (int i = 0; i < armsCount; i++) {
            float[] pos = generateSpiralStar(s, globalRotation);
            ptr = addStar(data, ptr, pos, getArmColor(pos));
        }
    }

    private static int addStar(GalaxyData data, int p, float[] pos, float[] col) {
        data.starPositions[p * 3] = pos[0];
        data.starPositions[p * 3 + 1] = pos[1];
        data.starPositions[p * 3 + 2] = pos[2];
        data.starColors[p * 3] = col[0];
        data.starColors[p * 3 + 1] = col[1]; // Corrected indexing from i to p
        data.starColors[p * 3 + 1] = col[1];
        data.starColors[p * 3 + 2] = col[2];
        return p + 1;
    }

    /**
     * Fixes the "Line/Needle" look in the core.
     * Uses Inverse Transform Sampling to ensure stars are uniform across the sphere.
     */
    private static float[] generateUniformSphere(float radius, float squish, float noise, float sersic) {
        float p = (float) Math.pow(rand.nextFloat(), sersic);
        float r = p * radius;

        // Uniform spherical coordinates
        double theta = rand.nextDouble() * 2.0 * Math.PI;
        double cosPhi = rand.nextDouble() * 2.0 - 1.0; // Uniformly distributed cosine
        double sinPhi = Math.sqrt(1.0 - cosPhi * cosPhi);

        float x = (float) (r * sinPhi * Math.cos(theta));
        float z = (float) (r * sinPhi * Math.sin(theta));
        float y = (float) (r * cosPhi * squish); // cosPhi represents the vertical axis here

        // Add jitter that is also spherical to prevent cube-aliasing
        float jitterR = rand.nextFloat() * noise;
        double jTheta = rand.nextDouble() * 2.0 * Math.PI;
        x += (float)Math.cos(jTheta) * jitterR;
        z += (float)Math.sin(jTheta) * jitterR;
        y += (rand.nextFloat() - 0.5f) * noise * squish;

        return new float[]{x, y, z};
    }

    private static float[] generateSpiralStar(GalaxySettings s, float globalRotation) {
        float t = (float) Math.pow(rand.nextFloat(), s.sersicIndex);
        float dist = s.armStart + t * (s.armEnd - s.armStart);

        // Arm selection + global rotation to break grid alignment
        float armOffset = (rand.nextInt(s.armCount) * (float)(Math.PI * 2.0 / s.armCount)) + globalRotation;

        // Add a tiny bit of random jitter to the 't' progress to blur the "line"
        float blurredT = t + (rand.nextFloat() - 0.5f) * 0.01f;
        float angle = blurredT * (s.armCurve * (float)Math.PI * 2.0f) + armOffset;

        float spread = s.armInnerSpread + t * (s.armOuterSpread - s.armInnerSpread);
        float squish = s.armInnerSquish + t * (s.armOuterSquish - s.armInnerSquish);

        // Use a disk-sampling approach for the arm cross-section
        double internalAngle = rand.nextDouble() * 2.0 * Math.PI;
        float internalDist = rand.nextFloat() * spread;

        float offsetX = (float)Math.cos(internalAngle) * internalDist;
        float offsetZ = (float)Math.sin(internalAngle) * internalDist;
        float offsetY = (rand.nextFloat() - 0.5f) * spread * squish;

        // Final position with noise
        float x = (float) (Math.cos(angle) * dist) + offsetX;
        float z = (float) (Math.sin(angle) * dist) + offsetZ;
        float y = offsetY;

        // Apply a small 3D jitter to break up any remaining mathematical patterns
        float noiseVal = s.armNoise * (1.0f - t * 0.5f); // Noise reduces slightly at edges
        x += (rand.nextFloat() - 0.5f) * noiseVal;
        z += (rand.nextFloat() - 0.5f) * noiseVal;
        y += (rand.nextFloat() - 0.5f) * noiseVal * squish;

        return new float[]{x, y, z};
    }

    private static float[] getArmColor(float[] pos) {
        float r = rand.nextFloat();
        if (r < 0.4f) return new float[]{0.6f, 0.8f, 1.0f};
        if (r < 0.7f) return new float[]{1.0f, 1.0f, 1.0f};
        return new float[]{1.0f, 0.7f, 0.4f};
    }
}