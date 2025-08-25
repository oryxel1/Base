package com.bascenario.util.render;

import imgui.ImVec2;
import imgui.ImVec4;

public class MathUtil {
    public static ImVec4 findBackgroundRender(ImVec2 windowSize, ImVec2 minSize) {
        float bestRatio = Math.max(windowSize.x / minSize.x, windowSize.y / minSize.y);
        float newSizeX = minSize.x * bestRatio, newSizeY = minSize.y * bestRatio;

        int centerX = (int) (windowSize.x / 2F - (newSizeX / 2));
        int centerY = (int) (windowSize.y / 2F - (newSizeY / 2));

        return new ImVec4(centerX, centerY, newSizeX + centerX, newSizeY + centerY);
    }

    public static ImVec2 findBestSize(ImVec2 size, ImVec2 minSize) {
        float ratioX = size.x / minSize.x, ratioY = size.y / minSize.y;
        if (ratioX * minSize.x > size.x) {
            ratioX -= ((ratioX * minSize.x) - size.x) / minSize.x;
        }
        if (ratioY * minSize.y > size.y) {
            ratioY -= ((ratioY * minSize.y) - size.y) / minSize.y;
        }
        float bestRatio = Math.min(ratioX, ratioY);

        return new ImVec2(minSize.x * bestRatio, minSize.y * bestRatio);
    }

    public static int ceil(float value) {
        int i = (int)value;
        return value > i ? i + 1 : i;
    }
}
