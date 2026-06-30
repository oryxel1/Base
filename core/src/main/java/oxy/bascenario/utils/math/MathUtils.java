package oxy.bascenario.utils.math;

import org.joml.Vector2f;
import org.joml.Vector4f;
import oxy.bascenario.utils.ScreenUtils;

public class MathUtils {
    public static Vector4f findBackgroundRender(Vector2f minSize) {
        float bestRatio = Math.max(ScreenUtils.width() / minSize.x, ScreenUtils.height() / minSize.y);
        float newSizeX = minSize.x * bestRatio, newSizeY = minSize.y * bestRatio;

        int centerX = (int) (ScreenUtils.width() / 2F - (newSizeX / 2));
        int centerY = (int) (ScreenUtils.height() / 2F - (newSizeY / 2));

        return new Vector4f(centerX, centerY, newSizeX, newSizeY);
    }
}
