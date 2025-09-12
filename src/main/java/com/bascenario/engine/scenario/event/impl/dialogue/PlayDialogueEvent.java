package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.Dialogue;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.DialogueRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

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
    public PlayDialogueEvent deserialize(JsonObject serialized) {
        return new PlayDialogueEvent(GsonUtil.getGson().fromJson(serialized.get("dialogue"), Dialogue.class));
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("dialogue", GsonUtil.toJson(this.dialogue));
    }

    @Override
    public String type() {
        return "play-dialogue";
    }
}
