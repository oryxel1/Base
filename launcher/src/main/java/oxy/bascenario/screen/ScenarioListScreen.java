package oxy.bascenario.screen;

import imgui.ImGui;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.managers.ScenarioManager;
import oxy.bascenario.screen.title.TitleScreen;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ImGuiUtils;
import oxy.bascenario.utils.Launcher;

import java.io.File;

public class ScenarioListScreen extends ExtendableScreen {
    private boolean popupNewScenario;
    private String cacheName;

    @Override
    public void render(float delta) {
        ImGui.beginMainMenuBar();
        if (ImGui.menuItem("Exit")) {
            Launcher.WINDOW.setScreen(TitleScreen.INSTANCE);
        }
        if (ImGui.menuItem("New Scenario")) {
            popupNewScenario = true;
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
                if (ImGui.smallButton("Edit##" + ImGuiUtils.COUNTER++)) {
                    Launcher.WINDOW.setScreen(new ScenarioEditorScreen(this, scenario, scenario.toBuilder()));
                }
                ImGui.sameLine();
                if (ImGui.smallButton("Play##" + ImGuiUtils.COUNTER++)) {
                    Launcher.WINDOW.setScreen(new ScenarioScreen(scenario) {
                        @Override
                        public void render(float delta) {
                            if (ImGui.isKeyReleased(ImGuiKey.Escape)) {
                                Launcher.WINDOW.setScreen(TitleScreen.INSTANCE);
                            }
                            super.render(delta);
                        }
                    });
                }
            }

            ImGui.endTable();
        }

        ImGui.end();

        if (popupNewScenario) {
            popupNewScenario = false;
            cacheName = "";
            ImGui.openPopup("New Scenario");
        }

        if (ImGui.beginPopupModal("New Scenario")) {
            cacheName = ImGuiUtils.inputText("Name", cacheName);
            final File path = new File(ScenarioManager.SAVE_DIR, cacheName);

            if (path.isDirectory() || cacheName.isEmpty()) {
                ImGui.text(cacheName.isEmpty() ? "Name can't be empty!" : "A directory with this name already exist!");
            }
            if (ImGui.button("Create##" + ImGuiUtils.COUNTER++) && !path.isDirectory() && !cacheName.isEmpty()) {
                Base.instance().scenarioManager().put(cacheName, Scenario.builder().name(cacheName).build());
                ImGui.closeCurrentPopup();
            }
            ImGui.sameLine();
            if (ImGui.button("Ok nevermine..")) {
                ImGui.closeCurrentPopup();
            }

            ImGui.endPopup();
        }
    }
}
