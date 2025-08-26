package com.bascenario.util.render;

import java.io.IOException;
import java.util.*;
import java.util.List;

import net.raphimc.thingl.text.font.Font;

public class FontUtil {
    private static final Map<String, List<Font>> NAME_TO_FONTS = new HashMap<>();

    public static Font getFont(String name, int size) {
        return NAME_TO_FONTS.get(name).get(Math.max(0, Math.min(size - 1, 150)));
    }

    public static void loadFonts() {
        loadFont("NotoSansRegular", "/assets/base/fonts/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/NotoSans-SemiBold.ttf");
    }

    private static void loadFont(String name, String font) {
        final List<Font> fonts = new ArrayList<>();

        try {
            final byte[] fontData = FontUtil.class.getResourceAsStream(font).readAllBytes();
            for (int i = 1; i <= 150; i++) {
                fonts.add(new Font(fontData, i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NAME_TO_FONTS.put(name, fonts);
    }
}
