package oxy.bascenario.api.render.elements.image;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record AnimatedImage(FileInfo file, long start, boolean loop) {
}
