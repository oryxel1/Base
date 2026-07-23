package oxy.base.editor.inspector.impl.events;

import oxy.base.api.effects.TransitionType;
import oxy.base.api.event.ScreenTransitionEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.editor.miniuis.AssetsUI;
import oxy.base.utils.ImGuiUtils;

public class TransitionInspector {
    private static FileInfo last;

    public static ScreenTransitionEvent render(ScreenTransitionEvent event) {
        ScreenTransitionEvent.Builder builder = event.toBuilder();

        AssetsUI.pick("Switch Background To", file -> last = file,  "png,jpg");

        if (last != null) {
            builder.background(last);
            last = null;
        }

        TransitionType type = TransitionType.values()[ImGuiUtils.combo("Transition Type", event.type().ordinal(), TransitionType.getAlls())];
        builder.type(type);

        builder.waitDuration(Math.abs(ImGuiUtils.sliderInt("Wait Duration", event.waitDuration(), 0, 10000)));
        builder.inDuration(Math.abs(ImGuiUtils.sliderInt("In Duration", event.inDuration(), 0, 10000)));
        builder.outDuration(Math.abs(ImGuiUtils.sliderInt("Out Duration", event.outDuration(), 0, 10000)));

        return builder.build();
    }
}
