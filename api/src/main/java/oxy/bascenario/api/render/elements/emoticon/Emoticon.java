package oxy.bascenario.api.render.elements.emoticon;

import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Emoticon(int duration, EmoticonType type, boolean sound) {
}
