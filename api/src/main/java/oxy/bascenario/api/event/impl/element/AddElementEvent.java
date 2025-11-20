package oxy.bascenario.api.event.impl.element;

import lombok.Getter;
import oxy.bascenario.api.event.Event;
import oxy.bascenario.api.render.RenderLayer;

@Getter
public class AddElementEvent extends Event<AddElementEvent> {
    private final int id;
    private final Object element;
    private final RenderLayer layer;

    public AddElementEvent(int id, Object element, RenderLayer layer) {
        super(0);
        this.id = id;
        this.element = element;
        this.layer = layer;
    }

    @Override
    public String type() {
        return "add-element";
    }

    @Override
    public AddElementEvent empty() {
        return new AddElementEvent(0, null, RenderLayer.BEHIND_DIALOGUE);
    }
}
