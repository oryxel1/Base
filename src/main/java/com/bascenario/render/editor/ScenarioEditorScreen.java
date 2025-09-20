package com.bascenario.render.editor;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.api.Screen;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends Screen {
    private final Scenario.Builder scenario;

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        super.render(positionMatrix, window, mouseX, mouseY);

        // Scenario details.
        ImGui.setNextWindowPos(new ImVec2(10, 10), ImGuiCond.Always, new ImVec2(0, 0));
        ImGui.setNextWindowBgAlpha(0.35F);

        AtomicInteger totalEventSize = new AtomicInteger();
        this.scenario.timestamps().forEach(t -> totalEventSize.addAndGet(t.events().size()));

        ImGui.begin("Scenario Name", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings | ImGuiWindowFlags.NoFocusOnAppearing | ImGuiWindowFlags.NoNav);
        ImGui.text(this.scenario.name());
        ImGui.text("Preview background: " + this.scenario.previewBackground());
        ImGui.text("Preview sound: " + this.scenario.previewSound());
        ImGui.text("Total timestamps: " + this.scenario.timestamps().size() + " (total events: " + totalEventSize.get() + ")");
        ImGui.end();


    }
}
