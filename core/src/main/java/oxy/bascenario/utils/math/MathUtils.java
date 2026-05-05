package oxy.bascenario.utils.math;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class MathUtils {
    public static Vector4f findBackgroundRender(Vector2f minSize) {
        float bestRatio = Math.max(1920f / minSize.x, 1080f / minSize.y);
        float newSizeX = minSize.x * bestRatio, newSizeY = minSize.y * bestRatio;

        int centerX = (int) (1920f / 2F - (newSizeX / 2));
        int centerY = (int) (1080f / 2F - (newSizeY / 2));

        return new Vector4f(centerX, centerY, newSizeX, newSizeY);
    }
}
