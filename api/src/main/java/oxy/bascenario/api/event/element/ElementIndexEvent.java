package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.api.Event;

@Getter
public class ElementIndexEvent extends Event<ElementIndexEvent> {
    private final int mainIndex, newIndex;
    private final Integer subIndex;
    private final boolean swap;

    public ElementIndexEvent(int mainIndex, int index, int newIndex, boolean swap) {
        this.mainIndex = mainIndex;
        this.subIndex = index;
        this.newIndex = newIndex;
        this.swap = swap;
    }

    public ElementIndexEvent(int index, int newIndex, boolean swap) {
        this.mainIndex = index;
        this.subIndex = null;
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
