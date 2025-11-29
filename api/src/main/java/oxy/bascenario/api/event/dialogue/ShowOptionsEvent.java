package oxy.bascenario.api.event.dialogue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ShowOptionsEvent extends Event<ShowOptionsEvent> {
    private final Map<String, Integer> options;

    @Override
    public String type() {
        return "show-options";
    }

    @Override
    public ShowOptionsEvent empty() {
        return new ShowOptionsEvent(new LinkedHashMap<>());
    }
}
