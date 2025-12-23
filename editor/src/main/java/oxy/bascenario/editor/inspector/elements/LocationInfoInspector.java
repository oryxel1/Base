package oxy.bascenario.editor.inspector.elements;

import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.utils.ImGuiUtils;

public class LocationInfoInspector {
    public static LocationInfo render(LocationInfo info) {
        final LocationInfo.LocationInfoBuilder builder = info.toBuilder();
        builder.location(ImGuiUtils.inputText("Location Name", info.location()));
        builder.fade(ImGuiUtils.sliderInt("Fade Duration", info.fade(), 0, 5000));
        return builder.build();
    }
}
