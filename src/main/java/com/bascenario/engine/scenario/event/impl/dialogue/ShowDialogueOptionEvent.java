package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.DialogueOptionsRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import imgui.ImGui;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShowDialogueOptionEvent extends Event<ShowDialogueOptionEvent> {
    private static final Type TYPE = new TypeToken<Map<String, Integer>>() {}.getType();

    private final Map<String, Integer> options;
    public ShowDialogueOptionEvent(Map<String, Integer> options) {
        super(0);
        this.options = options;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueOptions(new DialogueOptionsRender(screen, this.options));
    }

    @Override
    public void renderImGui() {
        // TODO: Implement this.
    }

    @Override
    public ShowDialogueOptionEvent defaultEvent() {
        final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("ABC", 0);
        return new ShowDialogueOptionEvent(map);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("options", GsonUtil.toJson(this.options));
    }

    @Override
    public ShowDialogueOptionEvent deserialize(JsonObject serialized) {
        return new ShowDialogueOptionEvent(GsonUtil.getGson().fromJson(serialized.get("options"), TYPE));
    }

    @Override
    public String type() {
        return "show-dialogue-options";
    }
}
