package oxy.bascenario.screen;

import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.screen.title.TitleScreen;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;

public class ScenarioListScreen extends ExtendableScreen {
    @Override
    public void render(float delta) {
        ImGui.beginMainMenuBar();
        if (ImGui.menuItem("Exit")) {
            Launcher.WINDOW.setScreen(TitleScreen.INSTANCE);
        }
        if (ImGui.menuItem("New Scenario")) {
        }
        ImGui.endMainMenuBar();

        ImGui.setNextWindowPos(0, 23);
        ImGui.setNextWindowSize(ImGui.getIO().getDisplaySize());
        ImGui.begin("Main", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoResize);

        if (ImGui.beginTable("1ways", 1, ImGuiTableFlags.BordersV | ImGuiTableFlags.BordersOuterH |ImGuiTableFlags.RowBg | ImGuiTableFlags.NoBordersInBody)) {
            ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.NoHide);
            ImGui.tableHeadersRow();

            for (Scenario scenario : Base.instance().scenarioManager().scenarios()) {
                ImGui.tableNextRow();
                ImGui.tableNextColumn();
                ImGui.text(scenario.getName());
                ImGui.sameLine();
                if (ImGui.smallButton("Edit")) {
                    Launcher.WINDOW.setScreen(new ScenarioEditorScreen(scenario, scenario.toBuilder()));
                }
                ImGui.sameLine();
                if (ImGui.smallButton("Play")) {
                    Launcher.WINDOW.setScreen(new ScenarioScreen(scenario));
                }
            }


            ImGui.endTable();
        }

        ImGui.end();
    }
}
