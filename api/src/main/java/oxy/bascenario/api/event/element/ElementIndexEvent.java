package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@RequiredArgsConstructor
@Getter
public class ElementIndexEvent extends Event<ElementIndexEvent> {
    private final int index, newIndex;
    private final boolean swap;

    @Override
    public String type() {
        return "element-index";
    }

    @Override
    public ElementIndexEvent empty() {
        return new ElementIndexEvent(0, 1, true);
    }
}
