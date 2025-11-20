package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.elements.Sprite;
import oxy.bascenario.api.event.impl.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.SpriteRenderer;
import oxy.bascenario.screens.renderer.base.ElementRenderer;
import oxy.bascenario.serializers.utils.GsonUtils;

public class FunctionAddElement extends EventFunction<AddElementEvent> {
    public FunctionAddElement(AddElementEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        // Yes this is hardcoded, there won't be much element, but it's probably a better idea to add a proper
        // implement down later the line....
        final ElementRenderer<?> renderer;
        if (event.getElement() instanceof Sprite sprite) {
            renderer = new SpriteRenderer(sprite, event.getLayer());
        } else {
            throw new RuntimeException("Can't find the rendering for the element class type: " + event.getElement().getClass());
        }

        renderer.resize(0, 0); // TODO: Properly do this?
        screen.getElements().put(event.getId(), renderer);
        System.out.println(screen.getElements());
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id", event.getId());
        serialized.add("object", GsonUtils.toJson(event.getElement()));
        serialized.addProperty("render-layer", event.getLayer().name());
    }

    @Override
    public AddElementEvent deserialize(JsonObject serialized) {
        return new AddElementEvent(serialized.get("id").getAsInt(), GsonUtils.getGson().fromJson(serialized.get("object"), Object.class), RenderLayer.valueOf(serialized.get("render-layer").getAsString()));
    }
}
