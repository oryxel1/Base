package com.bascenario.engine.scenario.event.impl.background;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.FileUtil;
import com.bascenario.util.GsonUtil;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;

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
    public void renderImGui() {
        ImGui.text("Background path: " + this.background.path());
        ImGui.sameLine();
        if (ImGui.button("Browse##" + ImGuiUtil.COUNTER++)) {
            final String path = FileUtil.pickFile("jpg", "png");
            if (!path.isBlank()) {
                this.background.path(path);
            }
        }

        this.background.fadeIn(ImGuiUtil.checkbox("Fade In", this.background.fadeIn()));
        this.background.fadeOut(ImGuiUtil.checkbox("Fade Out", this.background.fadeOut()));
    }

    @Override
    public SetBackgroundEvent defaultEvent() {
        return new SetBackgroundEvent(new Background("", false, false));
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
