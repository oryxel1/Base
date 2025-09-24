package com.bascenario.engine.scenario.event.impl.lock;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LockClickEvent extends Event<LockClickEvent> {
    private boolean lock;
    public LockClickEvent(boolean lock) {
        super(0);
        this.lock = lock;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setLockClick(this.lock);
    }

    @Override
    public void renderImGui() {
        this.lock = ImGuiUtil.checkbox("Locked", this.lock);
    }

    @Override
    public LockClickEvent defaultEvent() {
        return new LockClickEvent(false);
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("locked", this.lock);
    }

    @Override
    public LockClickEvent deserialize(JsonObject serialized) {
        return new LockClickEvent(serialized.get("locked").getAsBoolean());
    }

    @Override
    public String type() {
        return "set-lock-click";
    }
}
