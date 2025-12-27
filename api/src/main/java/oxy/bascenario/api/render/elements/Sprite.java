package oxy.bascenario.api.render.elements;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Sprite(FileInfo skeleton, FileInfo atlas) {
}