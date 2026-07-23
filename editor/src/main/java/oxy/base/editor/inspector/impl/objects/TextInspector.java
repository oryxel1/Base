package oxy.base.editor.inspector.impl.objects;

import imgui.ImGui;
import oxy.base.api.Scenario;
import oxy.base.api.render.elements.text.AnimatedText;
import oxy.base.api.render.elements.text.Text;
import oxy.base.api.render.elements.text.TextSegment;
import oxy.base.editor.inspector.impl.other.TextSegmentInspector;
import oxy.base.utils.ImGuiUtils;

public class TextInspector {
    public static Text render(Scenario.Builder scenario, Text text) {
        if (ImGui.button("New segment!")) {
            text.segments().add(new TextSegment());
        }

        Text.Builder builder = text.toBuilder();
        builder.size(ImGuiUtils.sliderInt("Font Size", text.size(), 1, 150));
        builder.segments(TextSegmentInspector.render(scenario, text.segments()));
        return builder.build();
    }

    public static AnimatedText render(Scenario.Builder scenario, AnimatedText text) {
        if (ImGui.button("New segment!")) {
            text.segments().add(new TextSegment());
        }

        AnimatedText.Builder builder = text.toBuilder();
        builder.playSpeed(ImGuiUtils.sliderFloat("Play Speed", text.playSpeed(), 0.01f, 50));
        builder.size(ImGuiUtils.sliderInt("Font Size", text.size(), 1, 150));
        builder.segments(TextSegmentInspector.render(scenario, text.segments()));
        return builder.build();
    }
}
