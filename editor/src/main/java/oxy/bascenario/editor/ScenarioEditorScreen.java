package oxy.bascenario.editor;

import imgui.ImGui;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.flag.ImGuiDockNodeFlags;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ImGuiUtils;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario.Builder scenario;

    private int currentTime, maxTime;
    @Override
    public void render(float delta) {
        ImGui.dockSpaceOverViewport(0, new ImGuiViewport(0), ImGuiDockNodeFlags.PassthruCentralNode);
        menuBar();

        ImGui.begin("Timeline");
        currentTime = ImGuiUtils.sliderInt("Time", currentTime, 0, maxTime + 10_000);
        ImGui.end();
    }

    // TODO.
    private void menuBar() {
        ImGui.beginMainMenuBar();

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
