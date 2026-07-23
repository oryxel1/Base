package oxy.base.api.render.elements.image;

import lombok.Builder;
import net.lenni0451.commons.color.Color;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AnimatedImage(FileInfo file, long start, boolean loop, Color color, int width, int height) {
}
