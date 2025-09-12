package com.bascenario.engine.scenario.event.impl.lock;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LockClickEvent extends Event<LockClickEvent> {
    private final boolean lock;
    public LockClickEvent(boolean lock) {
        super(0);
        this.lock = lock;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setLockClick(this.lock);
    }


    @Override
    public LockClickEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new LockClickEvent(json.getAsBoolean());
    }

    @Override
    public JsonElement serialize(LockClickEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.lock);
    }
}
