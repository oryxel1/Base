package com.bascenario.render.editor;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.Timestamp;
import com.bascenario.managers.AudioManager;
import com.bascenario.render.api.Screen;
import com.bascenario.render.scenario.ScenarioPreviewScreen;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends Screen {
    private final Scenario.Builder scenario;
    private ScenarioPreviewScreen preview;

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        this.renderImGui();

        if (this.preview != null) {
            this.preview.render(positionMatrix, window, mouseX, mouseY);
            if (this.preview.isDone()) {
                this.stopPreviewingPreview();
            }
        }
    }

    private void renderImGui() {
        // Scenario details.
        ImGui.setNextWindowPos(new ImVec2(10, 10), ImGuiCond.Always, new ImVec2(0, 0));
        ImGui.setNextWindowBgAlpha(0.35F);

        final AtomicInteger totalEventSize = new AtomicInteger();
        this.scenario.timestamps().forEach(t -> totalEventSize.addAndGet(t.events().size()));

        ImGui.begin("Scenario Name", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings | ImGuiWindowFlags.NoFocusOnAppearing | ImGuiWindowFlags.NoNav);
        ImGui.text(this.scenario.name());
        ImGui.text("Preview background: " + this.scenario.previewBackground());
        ImGui.text("Preview sound: " + this.scenario.previewSound());
        ImGui.text("Total timestamps: " + this.scenario.timestamps().size() + " (total events: " + totalEventSize.get() + ")");
        ImGui.end();

        // Timestamps and event.
        // Still unsure how I should do this.
//        ImGui.begin("Timestamps and events.");
//        if (ImGui.collapsingHeader("Preview")) {
//            if (ImGui.button("Play")) {
//                if (this.preview != null) {
//                    this.stopPreviewingPreview();
//                }
//
//                this.preview = new ScenarioPreviewScreen(scenario.build());
//                this.preview.setEditing(true);
//            }
//
//        }
//
//        ImGui.separatorText("Main Scenario");
//
//        int index = -1;
//        List<Timestamp> timestamps = new ArrayList<>();
//        boolean changed = false;
//        for (Timestamp timestamp : this.scenario.timestamps()) {
//            index++;
//            if (ImGui.collapsingHeader("Timestamp##" + index)) {
//                if (ImGui.checkbox("Wait For Dialogue##" + index, timestamp.waitForDialogue())) {
//                    timestamp = new Timestamp(!timestamp.waitForDialogue(), timestamp.time(), timestamp.events());
//                    changed = true;
//                }
//
//                final ImInt time = new ImInt((int) timestamp.time());
//                ImGui.inputInt("Time Till Next", time);
//                if (time.get() != timestamp.time()) {
//                    timestamp = new Timestamp(timestamp.waitForDialogue(), time.get(), timestamp.events());
//                    changed = true;
//                }
//            }
//
//            timestamps.add(timestamp);
//        }
//
//        if (changed) {
//            this.scenario.timestamps().clear();
//            this.scenario.timestamps().addAll(timestamps);
//        }
//
//        ImGui.end();
    }

    private void stopPreviewingPreview() {
        if (this.preview.getScenario().getPreviewSound() != null) {
            AudioManager.getInstance().stop(this.preview.getScenario().getPreviewSound().soundId());
        }
        this.preview = null;
    }
}
