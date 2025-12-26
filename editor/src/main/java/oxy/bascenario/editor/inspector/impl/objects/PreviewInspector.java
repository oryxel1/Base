package oxy.bascenario.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.utils.ImGuiUtils;

public class PreviewInspector {
    public static Preview render(Scenario.Builder scenario, Preview preview) {
        final Preview.Builder builder = preview.toBuilder();
        builder.title(ImGuiUtils.inputText("Title", preview.title()));
        builder.subtitle(ImGuiUtils.inputText("Subtitle", preview.subtitle()));

        ImGuiUtils.pick(builder::background, scenario, "Background", false, "png, jpg");

        if (preview.background() != null) {
            ImGui.sameLine();
            if (ImGui.button("Remove background!")) {
                builder.background(null);
            }
        }

        return builder.build();
    }
}
