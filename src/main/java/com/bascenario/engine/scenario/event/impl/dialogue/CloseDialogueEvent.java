package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CloseDialogueEvent extends Event<CloseDialogueEvent> {
    public CloseDialogueEvent() {
        super(0);
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogue(null);
    }

    @Override
    public CloseDialogueEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new CloseDialogueEvent();
    }

    @Override
    public JsonElement serialize(CloseDialogueEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonObject();
    }
}
