package oxy.bascenario.api.event.dialogue;

import lombok.Getter;
import oxy.bascenario.api.event.api.Event;

import java.util.Map;

@Getter
public class ShowOptionsEvent extends Event<ShowOptionsEvent> {
    private final Map<String, Integer> options;

    public ShowOptionsEvent(Map<String, Integer> options) {
        super(0);
        this.options = options;
    }

    @Override
    public String type() {
        return "show-options";
    }

    @Override
    public ShowOptionsEvent empty() {
        return null;
    }
}
