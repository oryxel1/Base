package oxy.bascenario.utils;

import java.util.*;
import java.util.List;

import imgui.*;
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
    private static final Map<String, List<ImFont>> NAME_TO_IM_FONTS = new HashMap<>();

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

    public static ImFont getImFont(String name, int size) {
        return NAME_TO_IM_FONTS.get(name).get(Math.max(0, Math.min(size - 1, 50)));
    }

    public static void loadFonts() {
        // Cache these font so I can use them dynamically later.
        loadFont("NotoSansRegular", "/assets/base/fonts/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/NotoSans-SemiBold.ttf");
        loadFont("NotoSansBold", "/assets/base/fonts/NotoSans-Bold.ttf");

        ImGui.getIO().setFontDefault(getImFont("NotoSansRegular", 17));
    }

    public static Font loadSpecificFont(Scenario scenario, FileInfo font, int scale) {
        final byte[] fontData = (byte[]) Base.instance().assetsManager().assets(scenario.getName(), font).asset();
        return new FreeTypeFont(fontData, scale);
    }

    private static void loadFont(String name, String font) {
        final List<Font> fonts = new ArrayList<>();
        final List<ImFont> imFonts = new ArrayList<>();

        final ImGuiIO data = ImGui.getIO();
        final ImFontAtlas atlas = data.getFonts();

        try {
            final byte[] fontData = FontUtils.class.getResourceAsStream(font).readAllBytes();
            for (int i = 1; i <= 150; i++) {
                fonts.add(new FreeTypeFont(fontData, i));

                if (i <= 50) { // Loading im gui font is rather slow, and 50 is enough.
                    imFonts.add(atlas.addFontFromMemoryTTF(fontData, i, new ImFontConfig(), data.getFonts().getGlyphRangesDefault()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        NAME_TO_FONTS.put(name, fonts);
        NAME_TO_IM_FONTS.put(name, imFonts);
    }
}