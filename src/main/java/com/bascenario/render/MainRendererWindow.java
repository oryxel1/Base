package com.bascenario.render;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.app.Application;
import imgui.app.Configuration;

public class MainRendererWindow extends Application {
    @Override
    protected void configure(Configuration config) {
        config.setTitle("I love potato!");
    }

    @Override
    public void process() {
        ImGui.getForegroundDrawList().addCircleFilled(new ImVec2(100, 100), 100, -1);
    }
}
