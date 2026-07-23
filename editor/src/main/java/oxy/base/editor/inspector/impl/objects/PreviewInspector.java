package oxy.base.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.base.api.render.elements.Preview;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.editor.miniuis.AssetsUI;
import oxy.base.utils.ImGuiUtils;

public class PreviewInspector {
    private static FileInfo last;

    public static Preview render(Preview preview) {
        final Preview.Builder builder = preview.toBuilder();
        builder.type(FontType.values()[ImGuiUtils.combo("Font Type", preview.type().ordinal(), FontType.getAlls())]);
        builder.title(ImGuiUtils.inputText("Title", preview.title()));
        builder.subtitle(ImGuiUtils.inputText("Subtitle", preview.subtitle()));

        AssetsUI.pick("Pick Image!", file -> last = file, "png,jpg");

        if (preview.background() != null) {
            ImGui.sameLine();
            if (ImGui.button("Remove background!")) {
                builder.background(null);
            }
        }

        if (last != null) {
            builder.background(last);
            last = null;
        }

        return builder.build();
    }
}
