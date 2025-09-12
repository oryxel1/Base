package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.Dialogue;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.DialogueRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PlayDialogueEvent extends Event<PlayDialogueEvent> {
    private final Dialogue dialogue;
    public PlayDialogueEvent(Dialogue dialogue) {
        super(0);
        this.dialogue = dialogue;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (screen.getDialogueIndex() != dialogue.index()) {
            return;
        }

        screen.setDialogue(new DialogueRender(this.dialogue));
    }

    @Override
    public JsonElement serialize(PlayDialogueEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return GsonUtil.toJson(src.dialogue);
    }

    @Override
    public PlayDialogueEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new PlayDialogueEvent(GsonUtil.getGson().fromJson(json, Dialogue.class));
    }
}
