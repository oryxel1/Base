package oxy.bascenario.api.elements.effect;

import lombok.Getter;
import oxy.bascenario.api.utils.Axis;

@Getter
public final class OverlayEffect {
    private final OverlayEffectType type;
    private final Axis axis;

    private OverlayEffect(OverlayEffectType type, Axis axis) {
        this.type = type;
        this.axis = axis;
    }

    public static final OverlayEffect RAINBOW_X = new OverlayEffect(OverlayEffectType.RAINBOW, Axis.X);
    public static final OverlayEffect RAINBOW_Y = new OverlayEffect(OverlayEffectType.RAINBOW, Axis.Y);

    public static final OverlayEffect HOLOGRAM_X = new OverlayEffect(OverlayEffectType.HOLOGRAM, Axis.X);
    public static final OverlayEffect HOLOGRAM_Y = new OverlayEffect(OverlayEffectType.HOLOGRAM, Axis.Y);

}
