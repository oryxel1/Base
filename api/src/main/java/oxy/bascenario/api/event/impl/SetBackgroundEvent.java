package oxy.bascenario.api.event.impl;

import lombok.Getter;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.event.Event;

@Getter
public class SetBackgroundEvent extends Event<SetBackgroundEvent> {
    private final Image background;
    public SetBackgroundEvent(Image background) {
        super(0);
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
