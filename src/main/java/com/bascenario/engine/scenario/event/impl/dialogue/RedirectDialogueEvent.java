package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RedirectDialogueEvent extends Event<RedirectDialogueEvent> {
    private final int index;
    public RedirectDialogueEvent(int index) {
        super(1);
        this.index = index;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueIndex(this.index);
    }

    @Override
    public JsonElement serialize(RedirectDialogueEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.index);
    }

    @Override
    public RedirectDialogueEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new RedirectDialogueEvent(json.getAsJsonPrimitive().getAsInt());
    }
}
