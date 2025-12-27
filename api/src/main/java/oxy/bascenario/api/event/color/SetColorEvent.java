package oxy.bascenario.api.event.color;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.api.Event;

@SuppressWarnings("ALL")
@Builder(toBuilder = true, builderClassName = "Builder")
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class SetColorEvent extends Event<SetColorEvent> {
    private final int id;
    private final int duration;
    private final Color color;

    @Override
    public String type() {
        return "set-overlay";
    }

    @Override
    public SetColorEvent empty() {
        return new SetColorEvent(0, -1, Color.WHITE);
    }
}
