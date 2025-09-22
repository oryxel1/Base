package com.bascenario.engine.scenario.event.impl.background;

import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.FileUtil;
import com.bascenario.util.GsonUtil;
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

//    @Override
//    public void renderImGui() {
////        if (this.background == null) {
////            background = new Background("", false, false);
////        }
////
////        ImGui.text("Background path: " + background.path());
////        ImGui.sameLine();
////        if (ImGui.button("Browse")) {
////            final String path = FileUtil.pickFile("jpg", "png");
////            if (!path.isBlank()) {
////                background = new Background(path, !background.fadeIn(), background.fadeOut());
////            }
////        }
////        if (ImGui.checkbox("Fade in", background.fadeIn())) {
////            background = new Background(background.path(), !background.fadeIn(), background.fadeOut());
////        }
////        if (ImGui.checkbox("Fade out", background.fadeOut())) {
////            background = new Background(background.path(), background.fadeIn(), !background.fadeOut());
////        }
//    }

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
