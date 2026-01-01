package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.event.ClearBackgroundEvent;
import oxy.bascenario.api.event.SetBackgroundEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

public class BackgroundInspector {
    private static FileInfo last;

    public static SetBackgroundEvent render(SetBackgroundEvent event) {
        SetBackgroundEvent.Builder builder = event.toBuilder();
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Fade Duration", event.duration(), 0, 10000)));
        AssetsUI.pick("Pick Background!", file -> last = file,  "png,jpg");

        if (last != null) {
            builder.background(last);
            last = null;
        }

        return builder.build();
    }

    public static ClearBackgroundEvent render(ClearBackgroundEvent event) {
        ClearBackgroundEvent.Builder builder = event.toBuilder();
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Fade Duration", event.duration(), 0, 10000)));
        return builder.build();
    }
}
