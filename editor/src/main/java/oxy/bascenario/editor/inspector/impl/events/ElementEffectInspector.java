package oxy.bascenario.editor.inspector.impl.events;

import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.utils.ImGuiUtils;

public class ElementEffectInspector {
    public static ElementEffectEvent render(ElementEffectEvent event) {
        ElementEffectEvent.Builder builder = event.toBuilder();
        builder.id(Math.abs(ImGuiUtils.inputInt("Target Track", event.id())));
        Effect effect = Effect.values()[ImGuiUtils.combo("Effect Type", event.effect().ordinal(), Effect.getAlls())];
        builder.effect(effect);
        Object[] values;

        if (event.type() == ElementEffectEvent.Type.ADD) {
            switch (effect) {
                case HOLOGRAM -> {
                    Axis old = Axis.X;
                    if (event.values().length == 1 && event.values()[0] instanceof Axis axis) {
                        old = axis;
                    }

                    values = new Object[] {Axis.values()[ImGuiUtils.combo("Axis", old.ordinal(), Axis.getAlls())]};
                }

                case RAINBOW -> {
                    Axis old = Axis.X;
                    float oldFloat = 1.5f;
                    if (event.values().length == 2 && event.values()[0] instanceof Axis axis && event.values()[1] instanceof Float f) {
                        old = axis;
                        oldFloat = f;
                    }

                    values = new Object[] {
                            Axis.values()[ImGuiUtils.combo("Axis", old.ordinal(), Axis.getAlls())],
                            ImGuiUtils.sliderFloat("Distance", oldFloat, 0.01f, 500)
                    };
                }

                case BLUR -> {
                    int old = 5;
                    if (event.values().length == 1 && event.values()[0] instanceof Integer integer) {
                        old = integer;
                    }

                    values = new Object[] {ImGuiUtils.sliderInt("Radius", old, 0, 100)};
                }

                case OUTLINE -> {
                    int old = 1;
                    if (event.values().length == 2 && event.values()[0] instanceof Integer integer) {
                        old = integer;
                    }

                    values = new Object[] {ImGuiUtils.sliderInt("Radius", old, 0, 100), 1 << 1};
                }
                default -> throw new IllegalStateException("Unexpected value: " + event.effect());
            }
        } else {
            values = new Object[] {};
        }

        builder.values(values);

        return builder.build();
    }
}
