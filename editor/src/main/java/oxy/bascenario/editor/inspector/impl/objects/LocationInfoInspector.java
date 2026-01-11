package oxy.bascenario.editor.inspector.impl.objects;

import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.utils.ImGuiUtils;

public class LocationInfoInspector {
    public static LocationInfo render(LocationInfo info) {
        final LocationInfo.Builder builder = info.toBuilder();
        builder.font(FontType.values()[ImGuiUtils.combo("Font Type", info.font().ordinal(), FontType.getAlls())]);
        builder.location(ImGuiUtils.inputText("Location Name", info.location()));
        builder.fade(ImGuiUtils.sliderInt("Fade Duration", info.fade(), 0, 5000));
        return builder.build();
    }
}
