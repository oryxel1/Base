package oxy.base.editor.inspector.impl.events;

import oxy.base.api.effects.Easing;
import oxy.base.api.event.element.values.PositionElementEvent;
import oxy.base.api.event.element.values.RotateElementEvent;
import oxy.base.api.utils.math.Vec2;
import oxy.base.api.utils.math.Vec3;
import oxy.base.utils.ImGuiUtils;

public class PositionInspector {
    public static RotateElementEvent render(RotateElementEvent event) {
        RotateElementEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.id())));
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Duration (ms)", (int) event.duration(), 0, 50000)));

        float x = ImGuiUtils.sliderFloat("X", event.rotation().x(), 0, 360);
        float y = ImGuiUtils.sliderFloat("Y", event.rotation().y(), 0, 360);
        float z = ImGuiUtils.sliderFloat("Z", event.rotation().z(), 0, 360);

        builder.rotation(new Vec3(x, y, z));
        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.easing().ordinal(), Easing.getAlls())]);
        return builder.build();
    }

    public static PositionElementEvent render(PositionElementEvent event) {
        PositionElementEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.id())));
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Duration (ms)", event.duration(), 0, 50000)));

        float MIN_X = event.type() == PositionElementEvent.Type.SCALE ? 0 : -1920;
        float MAX_X = event.type() == PositionElementEvent.Type.SCALE ? 200 : 1920;
        float MIN_Y = event.type() == PositionElementEvent.Type.SCALE ? 0 : -1080;
        float MAX_Y = event.type() == PositionElementEvent.Type.SCALE ? 200 : 1080;

        float x = ImGuiUtils.sliderFloat("X", event.vec().x(), MIN_X, MAX_X);
        float y = ImGuiUtils.sliderFloat("Y", event.vec().y(), MIN_Y, MAX_Y);
        builder.vec(new Vec2(x, y));

        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.easing().ordinal(), Easing.getAlls())]);

        return builder.build();
    }
}
