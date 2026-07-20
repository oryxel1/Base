package oxy.bascenario.utils.font;

import java.util.*;

import net.raphimc.thingl.resource.font.face.impl.FreeTypeFontFace;
import net.raphimc.thingl.resource.font.instance.FontInstance;
import net.raphimc.thingl.resource.font.instance.impl.FreeTypeFontInstance;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;

public class FontUtils {
    public static FontInstance DEFAULT, SEMI_BOLD;
    private static final Map<String, FontInstance> NAME_TO_FONTS = new HashMap<>();

    public static FontInstance font(FontStyle style, FontType type) {
        return NAME_TO_FONTS.get(type.toName(style));
    }

    public static FontInstance font(String name) {
        return NAME_TO_FONTS.get(name);
    }

    public static FontInstance toFont(Scenario scenario, TextSegment segment) {
        FontInstance font;
        if (segment.font().file() != null) {
            font = NAME_TO_FONTS.get(String.valueOf(segment.font().file().hashCode(scenario.getName())));
            if (font == null) {
                font = FontUtils.loadSpecificFont(scenario, segment.font().file());
                NAME_TO_FONTS.put(String.valueOf(segment.font().file().hashCode(scenario.getName())), font);
            }
        } else {
            font = NAME_TO_FONTS.get(segment.font().type().toName(segment.font().style()));
        }
        return font;
    }

    public static void loadFonts() {
        // UI (Rivet)
        loadFont("SFUIRegular", "/assets/base/fonts/rivet/SFUIText-Regular.ttf", 16);

        // Global
        loadFont("NotoSansRegular", "/assets/base/fonts/global/NotoSans-Regular.ttf");
        loadFont("NotoSansSemiBold", "/assets/base/fonts/global/NotoSans-SemiBold.ttf");
        loadFont("NotoSansBold", "/assets/base/fonts/global/NotoSans-Bold.ttf");

        // Korea
        loadFont("GyeonggiRegular", "/assets/base/fonts/korea/Gyeonggi_Medium.ttf");
        loadFont("GyeonggiSemiBold", "/assets/base/fonts/korea/Gyeonggi_Medium.ttf");
        loadFont("GyeonggiBold", "/assets/base/fonts/korea/Gyeonggi_Bold.ttf");

        // Japan
        loadFont("ShinMaruGoRegular", "/assets/base/fonts/japan/U-OTF-ShinMGoUpr-Medium.otf");
        loadFont("ShinMaruGoSemiBold", "/assets/base/fonts/japan/A-OTF Shin Maru Go Pro DB.otf");
        loadFont("ShinMaruGoBold", "/assets/base/fonts/japan/A-OTF Shin Maru Go Pro DB.otf");

        // Simplified Chinese
        loadFont("ChillRoundRegular", "/assets/base/fonts/chinese/simplified/ChillRoundGothic_Regular.otf");
        loadFont("ChillRoundSemiBold", "/assets/base/fonts/chinese/simplified/ChillRoundGothic_Medium.otf");
        loadFont("ChillRoundBold", "/assets/base/fonts/chinese/simplified/ChillRoundGothic_Bold.otf");

        // Traditional Chinese
        loadFont("NotoSansTCRegular", "/assets/base/fonts/chinese/traditional/NotoSansTC-Regular.ttf");
        loadFont("NotoSansTCSemiBold", "/assets/base/fonts/chinese/traditional/NotoSansTC-SemiBold.ttf");
        loadFont("NotoSansTCBold", "/assets/base/fonts/chinese/traditional/NotoSansTC-Bold.ttf");

        DEFAULT = NAME_TO_FONTS.get("ChillRoundRegular");
        SEMI_BOLD = NAME_TO_FONTS.get("ChillRoundSemiBold");
    }

    public static FontInstance loadSpecificFont(Scenario scenario, FileInfo font) {
        final byte[] fontData = (byte[]) Base.instance().assetsManager().assets(scenario.getName(), font).asset();
        return new FreeTypeFontInstance(new FreeTypeFontFace(fontData), 65);
    }

    private static void loadFont(String name, String font) {
        loadFont(name, font, 65);
    }

    private static void loadFont(String name, String font, int size) {
        try {
            final byte[] fontData = FontUtils.class.getResourceAsStream(font).readAllBytes();
            FontInstance instance = new FreeTypeFontInstance(new FreeTypeFontFace(fontData), 60);
            if (size != 60) {
                instance = instance.getScaledInstance(size);
            }

            NAME_TO_FONTS.put(name, instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}