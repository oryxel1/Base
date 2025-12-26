package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.element.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

public class PreviewInspector {
    private static FileInfo last;

    public static Preview render(Preview preview) {
        final Preview.Builder builder = preview.toBuilder();
        builder.title(ImGuiUtils.inputText("Title", preview.title()));
        builder.subtitle(ImGuiUtils.inputText("Subtitle", preview.subtitle()));

        AssetsUI.pick("Pick Image!", file -> last = file, "png, jpg");

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
