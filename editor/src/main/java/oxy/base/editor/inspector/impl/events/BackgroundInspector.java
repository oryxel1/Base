package oxy.base.editor.inspector.impl.events;

import oxy.base.api.event.background.ClearBackgroundEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.editor.miniuis.AssetsUI;
import oxy.base.utils.ImGuiUtils;

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
