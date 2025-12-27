package oxy.bascenario.api.render.elements.shape;

import lombok.Builder;
import net.lenni0451.commons.color.Color;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Rectangle(int width, int height, Color color, boolean outlineOnly) {
}
