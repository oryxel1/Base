package com.bascenario.engine.scenario.event.impl.background;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class SetBackgroundEvent extends Event<SetBackgroundEvent> {
    private final Background background;
    public SetBackgroundEvent(Background background) {
        super(0);
        this.background = background;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setBackground(this.background, false);
    }

    @Override
    public JsonElement serialize(SetBackgroundEvent src, Type typeOfSrc, JsonSerializationContext context) {
        return GsonUtil.toJson(src.background);
    }

    @Override
    public SetBackgroundEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new SetBackgroundEvent(GsonUtil.getGson().fromJson(json, Background.class));
    }
}
