package com.projektdeus.game.core;

import java.util.Random;

public class GalaxyGenerator {
    private static final Random rand = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void generate(GalaxyData data, GalaxySettings s) {
        int innerCount = (int) (s.starCount * (s.innerCorePercent / 100.0f));
        int outerCount = (int) (s.starCount * (s.outerCorePercent / 100.0f));
        int armsCount = s.starCount - innerCount - outerCount;

        int ptr = 0;
        float globalRotation = rand.nextFloat() * (float)Math.PI * 2;

        // 1. Inner Core
        for (int i = 0; i < innerCount; i++) {
            float[] pos = generateUniformSphere(s.innerCoreRadius, s.innerCoreSquish, s.innerCoreNoise, s.sersicIndex);
            ptr = addStarWithTilt(data, ptr, pos, new float[]{1.0f, 1.0f, 0.8f}, s);
        }

        // 2. Outer Core
        for (int i = 0; i < outerCount; i++) {
            float[] pos = generateUniformSphere(s.outerCoreRadius, s.outerCoreSquish, s.outerCoreNoise, s.sersicIndex);
            ptr = addStarWithTilt(data, ptr, pos, new float[]{1.0f, 0.8f, 0.5f}, s);
        }

        // 3. Arms
        for (int i = 0; i < armsCount; i++) {
            float[] pos = generateSpiralStar(s, globalRotation);
            ptr = addStarWithTilt(data, ptr, pos, getArmColor(pos), s);
        }
    }

    private static int addStarWithTilt(GalaxyData data, int p, float[] pos, float[] col, GalaxySettings s) {
        float rx = (float) Math.toRadians(s.galaxyTiltX);
        float ry = (float) Math.toRadians(s.galaxyTiltY);

        float x = pos[0];
        float y = pos[1];
        float z = pos[2];

        float y1 = (float)(y * Math.cos(rx) - z * Math.sin(rx));
        float z1 = (float)(y * Math.sin(rx) + z * Math.cos(rx));
        float x2 = (float)(x * Math.cos(ry) + z1 * Math.sin(ry));
        float z2 = (float)(-x * Math.sin(ry) + z1 * Math.cos(ry));

        data.starPositions[p * 3] = x2;
        data.starPositions[p * 3 + 1] = y1;
        data.starPositions[p * 3 + 2] = z2;

        data.starColors[p * 3] = col[0];
        data.starColors[p * 3 + 1] = col[1];
        data.starColors[p * 3 + 2] = col[2];

        // Generate the unique designation for this star
        data.starNames[p] = generateProceduralName();

        return p + 1;
    }

    private static String generateProceduralName() {
        StringBuilder sb = new StringBuilder();
        // Generate 3 random letters
        for (int i = 0; i < 3; i++) {
            sb.append(ALPHABET.charAt(rand.nextInt(ALPHABET.length())));
        }
        sb.append("-");
        // Generate 3 random numbers (000-999)
        sb.append(String.format("%03d", rand.nextInt(1000)));
        return sb.toString();
    }

    private static float[] generateUniformSphere(float radius, float squish, float noise, float sersic) {
        float p = (float) Math.pow(rand.nextFloat(), sersic);
        float r = p * radius;
        double theta = rand.nextDouble() * 2.0 * Math.PI;
        double cosPhi = rand.nextDouble() * 2.0 - 1.0;
        double sinPhi = Math.sqrt(1.0 - cosPhi * cosPhi);
        float x = (float) (r * sinPhi * Math.cos(theta));
        float z = (float) (r * sinPhi * Math.sin(theta));
        float y = (float) (r * cosPhi * squish);
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
        float armOffset = (rand.nextInt(s.armCount) * (float)(Math.PI * 2.0 / s.armCount)) + globalRotation;
        float blurredT = t + (rand.nextFloat() - 0.5f) * 0.01f;
        float angle = blurredT * (s.armCurve * (float)Math.PI * 2.0f) + armOffset;
        float spread = s.armInnerSpread + t * (s.armOuterSpread - s.armInnerSpread);
        float squish = s.armInnerSquish + t * (s.armOuterSquish - s.armInnerSquish);
        double internalAngle = rand.nextDouble() * 2.0 * Math.PI;
        float internalDist = rand.nextFloat() * spread;
        float offsetX = (float)Math.cos(internalAngle) * internalDist;
        float offsetZ = (float)Math.sin(internalAngle) * internalDist;
        float x = (float) (Math.cos(angle) * dist) + offsetX;
        float z = (float) (Math.sin(angle) * dist) + offsetZ;
        float y = (rand.nextFloat() - 0.5f) * spread * squish;
        float n = s.armNoise;
        x += (rand.nextFloat() - 0.5f) * n;
        z += (rand.nextFloat() - 0.5f) * n;
        y += (rand.nextFloat() - 0.5f) * n * squish;
        return new float[]{x, y, z};
    }

    private static float[] getArmColor(float[] pos) {
        float r = rand.nextFloat();
        if (r < 0.4f) return new float[]{0.6f, 0.8f, 1.0f};
        if (r < 0.7f) return new float[]{1.0f, 1.0f, 1.0f};
        return new float[]{1.0f, 0.7f, 0.4f};
    }
}