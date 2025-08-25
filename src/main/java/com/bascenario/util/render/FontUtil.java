package com.bascenario.util.render;

import com.bascenario.util.RenderUtil;
import imgui.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;

public class FontUtil {
    private static final Map<String, List<ImFont>> NAME_TO_FONTS = new HashMap<>();

    public static ImFont getFont(String name, int size) {
        return NAME_TO_FONTS.get(name).get(size - 1);
    }

    public static void loadFonts() {
        final ImGuiIO io = ImGui.getIO();

        final ImFontConfig config = new ImFontConfig();
        loadFont(config, "NotoSansRegular", "/assets/base/fonts/NotoSans-Regular.ttf");

        config.destroy();
        io.getFonts().build();
    }

    private static void loadFont(ImFontConfig config, String name, String font) {
        final List<ImFont> fonts = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            config.setName(name + " " + i + "px");
            try {
                fonts.add(ImGui.getIO().getFonts().addFontFromMemoryTTF(IOUtils.toByteArray(Objects.requireNonNull(RenderUtil.class.getResourceAsStream(font))), i, config));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        NAME_TO_FONTS.put(name, fonts);
    }
}
