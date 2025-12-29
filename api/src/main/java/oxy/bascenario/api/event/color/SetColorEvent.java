package oxy.bascenario.api.event.color;

import lombok.Builder;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.api.Event;

@Builder(toBuilder = true, builderClassName = "Builder")
public record SetColorEvent(int id, int duration, Color color) implements Event {
}
