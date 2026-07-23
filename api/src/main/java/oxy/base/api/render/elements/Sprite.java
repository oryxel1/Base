package oxy.base.api.render.elements;

import lombok.Builder;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Sprite(FileInfo skeleton, FileInfo atlas) {
}