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
    public void renderImGui() {
    }

    @Override
    public CloseDialogueEvent defaultEvent() {
        return new CloseDialogueEvent();
    }

    @Override
    public CloseDialogueEvent deserialize(JsonObject serialized) {
        return new CloseDialogueEvent();
    }

    @Override
    public void serialize(JsonObject serialized) {}

    @Override
    public String type() {
        return "close-dialogue";
    }
}
