package oxy.bascenario.api.render.elements.image;

import lombok.Builder;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Image(FileInfo file, Color color, int width, int height) {
}
