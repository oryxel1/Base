package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.element.MoveElementEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.screens.renderer.base.ElementRenderer;

public class FunctionMoveElement extends EventFunction<MoveElementEvent> {
    public FunctionMoveElement(MoveElementEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        final ElementRenderer<?> renderer = screen.getElements().get(event.getId());
        if (renderer == null) {
            return;
        }

        MoveElementEvent.Type type = event.getType();
        switch (type) {
            case X_ONLY -> renderer.moveX(event.getX(), event.getDuration());
            case Y_ONLY -> renderer.moveY(event.getY(), event.getDuration());
            case BOTH -> renderer.move(event.getX(), event.getY(), event.getDuration());
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("duration", event.getDuration());
        serialized.addProperty("id", event.getId());
        if (event.getType() == MoveElementEvent.Type.X_ONLY || event.getType() == MoveElementEvent.Type.BOTH) {
            serialized.addProperty("x", event.getX());
        }
        if (event.getType() == MoveElementEvent.Type.Y_ONLY || event.getType() == MoveElementEvent.Type.BOTH) {
            serialized.addProperty("y", event.getY());
        }
        serialized.addProperty("move-type", event.getType().name());
    }

    @Override
    public MoveElementEvent deserialize(JsonObject serialized) {
        final MoveElementEvent.Type type = MoveElementEvent.Type.valueOf(serialized.get("move-type").getAsString());
        float x = 0, y = 0;
        if (type == MoveElementEvent.Type.X_ONLY || type == MoveElementEvent.Type.BOTH) {
            x = serialized.get("x").getAsFloat();
        }
        if (type == MoveElementEvent.Type.Y_ONLY || type == MoveElementEvent.Type.BOTH) {
            y = serialized.get("y").getAsFloat();
        }

        return new MoveElementEvent(serialized.get("duration").getAsInt(), serialized.get("id").getAsInt(), x, y, type);
    }
}
