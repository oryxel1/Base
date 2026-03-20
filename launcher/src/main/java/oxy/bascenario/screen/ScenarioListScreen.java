package oxy.bascenario.screen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import imgui.ImColor;
import imgui.ImGui;
import imgui.flag.ImGuiKey;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.managers.ScenarioManager;
import oxy.bascenario.screen.title.TitleScreen;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ImGuiUtils;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.files.NFDUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.UUID;

public class ScenarioListScreen extends ExtendableScreen {
    private boolean popupNewScenario, popupImportingFailed;
    private String importingFailed;
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
        if (ImGui.menuItem("Import Scenario")) {
            final File file = new File(NFDUtils.pickFile("zip,rar"));
            if (!ZipUtil.containsEntry(file, "scenario.base")) {
                importingFailed = "Import scenario failed, is this the correct format?";
                popupImportingFailed = true;
            } else {
                Scenario scenario = null;
                final byte[] bytes = ZipUtil.unpackEntry(file, "scenario.base");
                try {
                    JsonObject object = JsonParser.parseString(new String(bytes)).getAsJsonObject();
                    scenario = Types.SCENARIO_TYPE.read(object);
                } catch (Exception ignored) {
                }
                if (scenario == null) {
                    importingFailed = "Import scenario failed, failed to read scenario!";
                    popupImportingFailed = true;
                } else {
                    File path = new File(ScenarioManager.SAVE_DIR, scenario.getName());
                    if (path.exists()) {
                        importingFailed = "File/Scenario Folder with this name already exist, please delete/rename it first";
                        popupImportingFailed = true;
                    } else {
                        path.mkdirs();

                        ZipUtil.unpack(file, path);
                        Base.instance().scenarioManager().put(scenario.getName(), scenario);
                    }
                }
            }
        }
        ImGui.endMainMenuBar();

        ImGui.setNextWindowPos(0, 23);
        ImGui.setNextWindowSize(ImGui.getIO().getDisplaySize());
        ImGui.begin("Main", ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoResize);

        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));

        if (ImGui.button("Open Saved Location##" + ImGuiUtils.COUNTER++)) {
            try {
                Desktop.getDesktop().open(ScenarioManager.SAVE_DIR);
            } catch (IOException ignored) {
            }
        }

        if (ImGui.beginTable("1ways", 1, ImGuiTableFlags.BordersV | ImGuiTableFlags.BordersOuterH |ImGuiTableFlags.RowBg | ImGuiTableFlags.NoBordersInBody)) {
            ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.NoHide);
            ImGui.tableHeadersRow();

            final Iterator<Scenario> iterator = Base.instance().scenarioManager().getScenarios().values().iterator();
            while (iterator.hasNext()) {
                Scenario scenario = iterator.next();

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
                ImGui.sameLine();

                // TODO: do me!
//                if (ImGui.smallButton("Rename##" + ImGuiUtils.COUNTER++)) {
//                }

                if (ImGui.smallButton("Export##" + ImGuiUtils.COUNTER++)) {
                    final File folder = new File(NFDUtils.pickFolder());
                    final File path = new File(folder, scenario.getName() + ".zip");
                    ZipUtil.pack(new File(ScenarioManager.SAVE_DIR, scenario.getName()), path);
                    try {
                        Desktop.getDesktop().open(folder);
                    } catch (IOException ignored) {
                    }
                }

                ImGui.sameLine();
                if (ImGui.smallButton("Delete##" + ImGuiUtils.COUNTER++)) {
                    final File path = new File(ScenarioManager.SAVE_DIR, scenario.getName());
                    try {
                        FileUtils.deleteDirectory(path);
                        iterator.remove();
                    } catch (IOException ignored) {
                    }
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

        if (popupImportingFailed) {
            popupImportingFailed = false;
            ImGui.openPopup("Import Failed");
        }

        if (ImGui.beginPopupModal("Import Failed", ImGuiWindowFlags.AlwaysAutoResize)) {
            ImGui.text(importingFailed);
            if (ImGui.button("Ok")) {
                ImGui.closeCurrentPopup();
                importingFailed = "";
            }
            ImGui.endPopup();
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
            if (ImGui.button("Ok nevermind..")) {
                ImGui.closeCurrentPopup();
            }

            ImGui.endPopup();
        }
    }
}
