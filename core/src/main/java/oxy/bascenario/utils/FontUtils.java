package oxy.bascenario.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

import net.raphimc.thingl.resource.font.Font;
import net.raphimc.thingl.resource.font.impl.FreeTypeFont;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.FileInfo;

public class FontUtils {
    private static final Map<String, List<Font>> NAME_TO_FONTS = new HashMap<>();

    public static Font toFont(Scenario scenario, TextSegment segment, Text text) {
        Font font;
        if (segment.font().isPresent()) {
            font = FontUtils.loadSpecificFont(scenario, segment.font().get(), text.size());
        } else {
            font = FontUtils.getFont(FontType.toName(segment.type()), text.size());
        }
        return font;
    }

    public static Font getFont(String name, int size) {
        return NAME_TO_FONTS.get(name).get(Math.max(0, Math.min(size - 1, 150)));
    }

    public static void loadFonts() {
        // Cache these font so I can use them dynamically later.
        loadFont("NotoSansRegular", "/assets/base/fonts/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/NotoSans-SemiBold.ttf");
        loadFont("NotoSansBold", "/assets/base/fonts/NotoSans-Bold.ttf");

        // Now set the default font since ImGui default font is pretty ugly :)
//        final ImGuiIO data = ImGui.getIO();
//        {
//            final ImFontAtlas fonts = data.getFonts();
//            final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
//
//            rangesBuilder.addRanges(data.getFonts().getGlyphRangesDefault());
//            rangesBuilder.addRanges(data.getFonts().getGlyphRangesCyrillic());
//            rangesBuilder.addRanges(data.getFonts().getGlyphRangesJapanese());
//
//            final short[] glyphRanges = rangesBuilder.buildRanges();
//
//            try {
//                data.setFontDefault(fonts.addFontFromMemoryTTF(IOUtils.toByteArray(Objects.requireNonNull(FontUtils.class.getResourceAsStream("/assets/base/fonts/NotoSans-Regular.ttf"))), 17.5F, new ImFontConfig(), glyphRanges));
//            } catch (Exception ignored) {}
//        }
    }

    public static Font loadSpecificFont(Scenario scenario, FileInfo font, int scale) {
        try {
            final byte[] fontData;
            if (font.internal()) {
                fontData = FontUtils.class.getResourceAsStream("/" + font.path()).readAllBytes();
            } else {
                File other = new File(Base.instance().getScenarioManager().path(scenario, font));
                fontData = Files.readAllBytes(other.toPath());
            }

            return new FreeTypeFont(fontData, scale);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadFont(String name, String font) {
        final List<Font> fonts = new ArrayList<>();

        try {
            final byte[] fontData = FontUtils.class.getResourceAsStream(font).readAllBytes();
            for (int i = 1; i <= 150; i++) {
                fonts.add(new FreeTypeFont(fontData, i));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NAME_TO_FONTS.put(name, fonts);
    }
}