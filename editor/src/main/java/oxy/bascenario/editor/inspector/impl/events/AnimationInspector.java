package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.Base;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.utils.ImGuiUtils;
import oxy.bascenario.utils.Pair;

public class AnimationInspector {
    public static PlayAnimationEvent render(PlayAnimationEvent event) {
        PlayAnimationEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId())));

        final Pair<Integer, String[]> alls = Base.instance().getAnimationManager().getAllAnimations(event.getName());
        builder.name(alls.right()[ImGuiUtils.combo("Animation Name", alls.left(), alls.right())]);

        builder.loop(ImGuiUtils.checkbox("Loop", event.isLoop()));
        return builder.build();
    }

    public static StopAnimationEvent render(StopAnimationEvent event) {
        StopAnimationEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId())));

        final Pair<Integer, String[]> alls = Base.instance().getAnimationManager().getAllAnimations(event.getName());
        builder.name(alls.right()[ImGuiUtils.combo("Animation Name", alls.left(), alls.right())]);
        return builder.build();
    }

    public static SpriteAnimationEvent render(SpriteAnimationEvent event) {
        SpriteAnimationEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId())));
        builder.trackIndex(Math.abs(ImGuiUtils.inputInt("Layer", event.getTrackIndex())));
        builder.mixTime(Math.abs(ImGuiUtils.sliderFloat("Mix/Lerp Time", event.getMixTime(), 0, 20)));
        builder.animationName(ImGuiUtils.inputText("Animation Name", event.getAnimationName()));
        builder.loop(ImGuiUtils.checkbox("Loop", event.isLoop()));
        return builder.build();
    }
}
