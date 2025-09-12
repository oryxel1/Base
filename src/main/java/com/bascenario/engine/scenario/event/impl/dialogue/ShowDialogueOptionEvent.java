package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.DialogueOptions;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.others.DialogueOptionsRender;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ShowDialogueOptionEvent extends Event<ShowDialogueOptionEvent> {
    private final DialogueOptions options;
    public ShowDialogueOptionEvent(DialogueOptions options) {
        super(0);
        this.options = options;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueOptions(new DialogueOptionsRender(screen, this.options));
    }

    @Override
    public JsonElement serialize(ShowDialogueOptionEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return GsonUtil.toJson(src.options);
    }

    @Override
    public ShowDialogueOptionEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new ShowDialogueOptionEvent(GsonUtil.getGson().fromJson(json, DialogueOptions.class));
    }
}
