package oxy.bascenario.editor.inspector.impl.objects;

import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.utils.ImGuiUtils;

public class LocationInfoInspector {
    public static LocationInfo render(LocationInfo info) {
        final LocationInfo.Builder builder = info.toBuilder();
        builder.location(ImGuiUtils.inputText("Location Name", info.location()));
        builder.fade(ImGuiUtils.sliderInt("Fade Duration", info.fade(), 0, 5000));
        return builder.build();
    }
}
