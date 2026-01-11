package oxy.bascenario.api.render.elements;

import lombok.Builder;
import oxy.bascenario.api.render.elements.text.font.FontType;

@Builder(toBuilder = true, builderClassName = "Builder")
public record LocationInfo(FontType font, String location, int duration, int fade) {
}
