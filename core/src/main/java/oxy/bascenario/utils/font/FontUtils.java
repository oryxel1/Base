package oxy.bascenario.utils.font;

import java.util.*;

import imgui.*;
import lombok.SneakyThrows;
import net.raphimc.thingl.resource.font.Font;
import net.raphimc.thingl.resource.font.impl.FreeTypeFont;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.text.FontType;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.FileInfo;

public class FontUtils {
    public static Font DEFAULT, BOLD, SEMI_BOLD;
    private static final Map<String, Font> NAME_TO_FONTS = new HashMap<>();

    public static ImFont IM_FONT_SEMI_BOLD_20, IM_FONT_SEMI_BOLD_30, IM_FONT_REGULAR_35;

    public static Font toFont(Scenario scenario, TextSegment segment) {
        Font font;
        if (segment.font().isPresent()) {
            font = NAME_TO_FONTS.get(String.valueOf(segment.font().get().hashCode(scenario.getName())));
            if (font == null) {
                font = FontUtils.loadSpecificFont(scenario, segment.font().get());
                NAME_TO_FONTS.put(String.valueOf(segment.font().get().hashCode(scenario.getName())), font);
            }
        } else {
            font = NAME_TO_FONTS.get(FontType.toName(segment.type()));
        }
        return font;
    }

    public static void loadFonts() {
        // Cache these font so I can use them dynamically later.
        loadFont("NotoSansRegular", "/assets/base/fonts/global/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/global/NotoSans-SemiBold.ttf");
        loadFont("NotoSansBold", "/assets/base/fonts/global/NotoSans-Bold.ttf");

        ImGui.getIO().setFontDefault(loadImFont("/assets/base/fonts/global/NotoSans-Regular.ttf", 17));

        DEFAULT = NAME_TO_FONTS.get("NotoSansRegular");
        BOLD = NAME_TO_FONTS.get("NotoSansBold");
        SEMI_BOLD = NAME_TO_FONTS.get("NotoSansSemiBold");

        IM_FONT_SEMI_BOLD_20 = loadImFont("/assets/base/fonts/global/NotoSans-SemiBold.ttf", 20);
        IM_FONT_SEMI_BOLD_30 = loadImFont("/assets/base/fonts/global/NotoSans-SemiBold.ttf", 30);
        IM_FONT_REGULAR_35 = loadImFont("/assets/base/fonts/global/NotoSans-Regular.ttf", 35);
    }

    public static Font loadSpecificFont(Scenario scenario, FileInfo font) {
        final byte[] fontData = (byte[]) Base.instance().assetsManager().assets(scenario.getName(), font).asset();
        return new FreeTypeFont(fontData, 65);
    }

    private static void loadFont(String name, String font) {
        try {
            final byte[] fontData = FontUtils.class.getResourceAsStream(font).readAllBytes();
            NAME_TO_FONTS.put(name, new FreeTypeFont(fontData, 65));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private static ImFont loadImFont(String font, int size) {
        final byte[] fontData = FontUtils.class.getResourceAsStream(font).readAllBytes();

        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(ImGui.getIO().getFonts().getGlyphRangesDefault());

        final ImGuiIO data = ImGui.getIO();
        return data.getFonts().addFontFromMemoryTTF(fontData, size, new ImFontConfig(), rangesBuilder.buildRanges());
    }
}