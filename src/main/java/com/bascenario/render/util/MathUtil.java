package com.bascenario.render.util;

import imgui.ImVec2;

public class MathUtil {
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
}
