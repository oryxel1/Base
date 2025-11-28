package oxy.bascenario.api.event;

import lombok.Getter;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.event.api.Event;

@Getter
public class SetBackgroundEvent extends Event<SetBackgroundEvent> {
    private final Image background;

    public SetBackgroundEvent(Image background) {
        this.background = background;
    }

    @Override
    public String type() {
        return "set-background";
    }

    @Override
    public SetBackgroundEvent empty() {
        return new SetBackgroundEvent(null);
    }
}
