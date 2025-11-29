package oxy.bascenario.api.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class SetBackgroundEvent extends Event<SetBackgroundEvent> {
    private final Image background;

    @Override
    public String type() {
        return "set-background";
    }

    @Override
    public SetBackgroundEvent empty() {
        return new SetBackgroundEvent(null);
    }
}
