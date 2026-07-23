package oxy.base.utils;

import net.lenni0451.commons.color.Color;

public class ColorUtils {
    public static String toHex(Color color) {
        return String.format("#%02x%02x%02x%02x", color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color fromHex(String hex) {
        try {
            java.awt.Color color = java.awt.Color.decode(hex);
            return Color.fromRGB(color.getRGB());
        } catch (Exception ignored) {
        }

        int i = Integer.decode(hex);
        return Color.fromRGBA((i >> 24) & 0xFF, (i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);

    }
}
