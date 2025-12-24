package oxy.bascenario.editor.inspector.elements.objects;

import imgui.ImGui;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.editor.inspector.elements.other.TextSegmentInspector;
import oxy.bascenario.utils.ImGuiUtils;

public class TextInspector {
    public static Text render(Text text) {
        if (ImGui.button("New segment!")) {
            text.segments().add(new TextSegment());
        }

        Text.TextBuilder builder = text.toBuilder();
        builder.size(ImGuiUtils.sliderInt("Font Size", text.size(), 1, 150));
        builder.segments(TextSegmentInspector.render(text.segments()));
        return builder.build();
    }
}
