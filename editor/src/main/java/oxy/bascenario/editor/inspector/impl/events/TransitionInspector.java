package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.effects.TransitionType;
import oxy.bascenario.api.event.ScreenTransitionEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.miniuis.AssetsUI;
import oxy.bascenario.utils.ImGuiUtils;

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
