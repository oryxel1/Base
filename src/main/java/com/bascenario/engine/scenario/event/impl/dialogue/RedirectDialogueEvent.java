package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class RedirectDialogueEvent extends Event<RedirectDialogueEvent> {
    private int index;
    public RedirectDialogueEvent(int index) {
        super(1);
        this.index = index;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueIndex(this.index);
    }

    @Override
    public void renderImGui() {
        this.index = ImGuiUtil.inputInt("Dialogue index", this.index);
    }

    @Override
    public RedirectDialogueEvent defaultEvent() {
        return new RedirectDialogueEvent(0);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("dialogue-index", this.index);
    }

    @Override
    public RedirectDialogueEvent deserialize(JsonObject serialized) {
        return new RedirectDialogueEvent(serialized.get("dialogue-index").getAsInt());
    }

    @Override
    public String type() {
        return "set-dialogue-index";
    }
}
