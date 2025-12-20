package oxy.bascenario.api.event.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.render.RenderLayer;

@RequiredArgsConstructor
@ToString
@Getter
public class AddElementEvent extends Event<AddElementEvent> {
    private final int id;
    private final Object element;
    private final RenderLayer layer;

    @Override
    public String type() {
        return "add-element";
    }

    @Override
    public AddElementEvent empty() {
        return new AddElementEvent(0, null, RenderLayer.BEHIND_DIALOGUE);
    }
}
