package oxy.bascenario.api.render.elements;

import lombok.Builder;

@Builder(toBuilder = true)
public record LocationInfo(String location, int duration, int fade) {
}
