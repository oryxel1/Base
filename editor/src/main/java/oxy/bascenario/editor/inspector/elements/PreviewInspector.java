package oxy.bascenario.editor.inspector.elements;

import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.utils.ImGuiUtils;

public class PreviewInspector {
    public static Preview render(Preview preview) {
        final Preview.PreviewBuilder builder = preview.toBuilder();
        builder.title(ImGuiUtils.inputText("Title", preview.title()));
        builder.subtitle(ImGuiUtils.inputText("Subtitle", preview.subtitle()));

        return builder.build();
    }
}
