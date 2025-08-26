package com.bascenario.util.render;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class MathUtil {
    public static Vector4f findBackgroundRender(Vector2f windowSize, Vector2f minSize) {
        float bestRatio = Math.max(windowSize.x / minSize.x, windowSize.y / minSize.y);
        float newSizeX = minSize.x * bestRatio, newSizeY = minSize.y * bestRatio;

        int centerX = (int) (windowSize.x / 2F - (newSizeX / 2));
        int centerY = (int) (windowSize.y / 2F - (newSizeY / 2));

        return new Vector4f(centerX, centerY, newSizeX + centerX, newSizeY + centerY);
    }
}
