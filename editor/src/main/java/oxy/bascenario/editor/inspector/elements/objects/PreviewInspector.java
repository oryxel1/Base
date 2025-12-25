package oxy.bascenario.editor.inspector.elements.objects;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.utils.ImGuiUtils;

public class PreviewInspector {
    public static Preview render(Scenario.Builder scenario, Preview preview) {
        final Preview.PreviewBuilder builder = preview.toBuilder();
        builder.title(ImGuiUtils.inputText("Title", preview.title()));
        builder.subtitle(ImGuiUtils.inputText("Subtitle", preview.subtitle()));

        ImGuiUtils.pick(fi -> builder.background(new Image(fi)), scenario, "Background", "png", "jpg");

        if (preview.background() != null) {
            ImGui.sameLine();
            if (ImGui.button("Remove background!")) {
                builder.background(null);
            }
        }

        return builder.build();
    }
}
