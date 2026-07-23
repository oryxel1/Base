package oxy.base.api.render.elements;

import lombok.Builder;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Preview(FontType type, String title, String subtitle, FileInfo background) {
}
