package oxy.bascenario.api.event.element;

import lombok.Getter;
import oxy.bascenario.api.event.api.Event;

@Getter
public class ElementIndexEvent extends Event<ElementIndexEvent> {
    private final int index, newIndex;
    private final boolean swap;
    public ElementIndexEvent(int index, int newIndex, boolean swap) {
        super(0);
        this.index = index;
        this.newIndex = newIndex;
        this.swap = swap;
    }

    @Override
    public String type() {
        return "element-index";
    }

    @Override
    public ElementIndexEvent empty() {
        return new ElementIndexEvent(0, 1, true);
    }
}
