package oxy.bascenario.api.render.elements;

import lombok.Builder;
import net.lenni0451.commons.color.Color;

@Builder(toBuilder = true, builderClassName = "Builder")
public record RendererImage<T>(T image, Color color, int width, int height) {
}
