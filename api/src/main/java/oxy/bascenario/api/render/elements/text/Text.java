package oxy.bascenario.api.render.elements.text;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Text(List<TextSegment> segments, int size) {
}
