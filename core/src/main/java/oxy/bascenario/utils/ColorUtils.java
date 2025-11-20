package oxy.bascenario.utils;

import net.lenni0451.commons.color.Color;

public class ColorUtils {
    public static Color fromAwt(java.awt.Color color) {
        return Color.fromRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
}
