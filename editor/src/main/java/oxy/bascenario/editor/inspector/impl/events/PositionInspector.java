package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.utils.ImGuiUtils;

public class PositionInspector {
    public static RotateElementEvent render(RotateElementEvent event) {
        RotateElementEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId())));
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Duration (ms)", (int) event.getDuration(), 0, 50000)));

        float x = ImGuiUtils.sliderFloat("X", (int) event.getRotation().x(), 0, 360);
        float y = ImGuiUtils.sliderFloat("Y", (int) event.getRotation().y(), 0, 360);
        float z = ImGuiUtils.sliderFloat("Z", (int) event.getRotation().z(), 0, 360);

        builder.rotation(new Vec3(x, y, z));
        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.getEasing().ordinal(), Easing.getAlls())]);
        return builder.build();
    }

    public static PositionElementEvent render(PositionElementEvent event) {
        PositionElementEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.getId())));
        builder.duration(Math.abs(ImGuiUtils.sliderInt("Duration (ms)", (int) event.getDuration(), 0, 50000)));

        float MIN_X = event.getType() == PositionElementEvent.Type.SCALE ? 0 : -1920;
        float MAX_X = event.getType() == PositionElementEvent.Type.SCALE ? 200 : 1920;
        float MIN_Y = event.getType() == PositionElementEvent.Type.SCALE ? 0 : -1080;
        float MAX_Y = event.getType() == PositionElementEvent.Type.SCALE ? 200 : 1080;

        float x = ImGuiUtils.sliderFloat("X", (int) event.getVec().x(), MIN_X, MAX_X);
        float y = ImGuiUtils.sliderFloat("Y", (int) event.getVec().y(), MIN_Y, MAX_Y);
        builder.vec(new Vec2(x, y));

        builder.easing(Easing.values()[ImGuiUtils.combo("Easing Mode", event.getEasing().ordinal(), Easing.getAlls())]);

        return builder.build();
    }
}
