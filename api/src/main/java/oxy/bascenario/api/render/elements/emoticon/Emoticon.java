package oxy.bascenario.api.render.elements.emoticon;

import lombok.Builder;

@Builder(toBuilder = true)
public record Emoticon(long duration, EmoticonType type, boolean sound) {
}
