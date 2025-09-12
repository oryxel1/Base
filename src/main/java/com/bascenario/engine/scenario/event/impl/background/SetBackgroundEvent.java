package com.bascenario.engine.scenario.event.impl.background;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

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
    public SetBackgroundEvent deserialize(JsonObject serialized) {
        return new SetBackgroundEvent(GsonUtil.getGson().fromJson(serialized.get("background"), Background.class));
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("background", GsonUtil.toJson(this.background));
    }

    @Override
    public String type() {
        return "set-background";
    }
}
