package com.bascenario.util.render;

import java.io.IOException;
import java.util.*;
import java.util.List;

import imgui.*;
import net.raphimc.thingl.text.font.Font;
import org.apache.commons.io.IOUtils;

public class FontUtil {
    private static final Map<String, List<Font>> NAME_TO_FONTS = new HashMap<>();

    public static Font getFont(String name, int size) {
        return NAME_TO_FONTS.get(name).get(Math.max(0, Math.min(size - 1, 150)));
    }

    public static void loadFonts() {
        final ImGuiIO data = ImGui.getIO();

        // Cache these font so I can use them dynamically later.
        loadFont("NotoSansRegular", "/assets/base/fonts/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/NotoSans-SemiBold.ttf");
        loadFont("NotoSansBold", "/assets/base/fonts/NotoSans-Bold.ttf");

        // Now set the default font since ImGui default font is pretty ugly :)
        {
            final ImFontAtlas fonts = data.getFonts();
            final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();

            rangesBuilder.addRanges(data.getFonts().getGlyphRangesDefault());
            rangesBuilder.addRanges(data.getFonts().getGlyphRangesCyrillic());
            rangesBuilder.addRanges(data.getFonts().getGlyphRangesJapanese());

            final short[] glyphRanges = rangesBuilder.buildRanges();

            try {
                data.setFontDefault(fonts.addFontFromMemoryTTF(IOUtils.toByteArray(Objects.requireNonNull(FontUtil.class.getResourceAsStream("/assets/base/fonts/NotoSans-Regular.ttf"))), 17.5F, new ImFontConfig(), glyphRanges));
            } catch (Exception ignored) {}
        }
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
