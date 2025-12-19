package oxy.bascenario.editor;

import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiDockNodeFlags;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.utils.ExtendableScreen;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario.Builder scenario;
    private final Timeline timeline = new Timeline();

    @Override
    public void show() {
        this.timeline.init();
    }

    // Ideally we mostly render thing on our own, but ImGui is used here since its rendering system and docking system is quite nice.
    @Override
    public void render(float delta) {
        ImGui.dockSpaceOverViewport(0, new ImGuiViewport(0), ImGuiDockNodeFlags.PassthruCentralNode);

        renderMenuBar();
        timeline.render();
    }

    // TODO.
    private void renderMenuBar() {
        ImGui.beginMainMenuBar();

        if (ImGui.beginMenu("Timeline")) {
            if (ImGui.menuItem("Play")) {
                timeline.setPlaying(true);
                timeline.setTimestamp(0);
            }
            if (ImGui.menuItem("Resume", false, timeline.isPlaying())) {
                timeline.setPlaying(false);
            }

            if (ImGui.menuItem("Stop", false, timeline.isPlaying())) {
                timeline.setPlaying(false);
                timeline.setTimestamp(0);
            }
            ImGui.endMenu();
        }

        if (ImGui.beginMenu("Edit")) {
            if (ImGui.menuItem("Undo", "Ctrl+Z")) {}
            if (ImGui.menuItem("Redo", "Ctrl+Y", false, false)) {} // Disabled item
            ImGui.separator();
            if (ImGui.menuItem("Cut", "Ctrl+X")) {}
            if (ImGui.menuItem("Copy", "Ctrl+C")) {}
            if (ImGui.menuItem("Paste", "Ctrl+V")) {}
            ImGui.endMenu();
        }

        ImGui.endMainMenuBar();
    }
}
