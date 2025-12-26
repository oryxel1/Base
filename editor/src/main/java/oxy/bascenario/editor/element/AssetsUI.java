package oxy.bascenario.editor.element;

import imgui.ImColor;
import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public class AssetsUI {
    public static boolean popup;
    public static Consumer<FileInfo> consumer;
    public static String filter;

    private static Object object;
    public static void pick(String name, Consumer<FileInfo> consumer, String filter) {
        if (ImGui.button(name + "##" + ImGuiUtils.COUNTER++)) {
            AssetsUI.filter = filter;
            AssetsUI.consumer = consumer;
            AssetsUI.popup = true;
            object = null;
        }
    }

    public static void render(Timeline timeline, Scenario.Builder scenario) {
        ImGui.begin("Assets");
        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));
        render(timeline, scenario, null);
        ImGui.end();

        if (popup) {
            ImGui.openPopup("Select asset");
            popup = false;
        }

        if (ImGui.beginPopupModal("Select asset")) {
            render(timeline, scenario, consumer);
            if (ImGui.button("Nevermine!")) {
                ImGui.closeCurrentPopup();
                filter = null;
                consumer = null;
            }
            ImGui.endPopup();
        }
    }

    public static void render(Timeline timeline, Scenario.Builder scenario, Consumer<FileInfo> consumer) {
        if (object == null) {
            object = timeline.getSelectedElement();
        }

        ImGuiUtils.pick(file -> {
            String lowercase = file.path().toLowerCase(Locale.ROOT);
            if (lowercase.endsWith(".mp3") || lowercase.endsWith(".ogg") || lowercase.endsWith(".wav")) {
                new Thread(() -> Base.instance().assetsManager().load(scenario.name(), file)).start();
            } else {
                Base.instance().assetsManager().load(scenario.name(), file);
            }
        }, scenario, "Load File", false, filter != null ? filter : "*");

        if (ImGui.beginTable("3ways", 2, ImGuiTableFlags.BordersV | ImGuiTableFlags.BordersOuterH | ImGuiTableFlags.Resizable | ImGuiTableFlags.RowBg | ImGuiTableFlags.NoBordersInBody)) {
            ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.NoHide);
            ImGui.tableSetupColumn("Type");
            ImGui.tableHeadersRow();

            for (Asset<?> asset : Base.instance().assetsManager().assets()) {
                if (asset.file().internal() || !Objects.equals(asset.scenario(), scenario.name())) {
                    continue;
                }
                if (filter != null) {
                    boolean valid = filter.equals("*");
                    if (!valid) {
                        for (String ext : filter.replace(" ", "").split(",")) {
                            if (asset.file().path().toLowerCase(Locale.ROOT).endsWith("." + ext)) {
                                valid = true;
                                break;
                            }
                        }
                    }

                    if (!valid) {
                        continue;
                    }
                }

                ImGui.tableNextRow();
                ImGui.tableNextColumn();
                if (ImGui.selectable(asset.file().path()) && consumer != null) {
                    if (object == timeline.getSelectedElement()) {
                        consumer.accept(asset.file());
                    }

                    ImGui.closeCurrentPopup();

                    filter = null;
                    consumer = null;
                }
                ImGui.tableNextColumn();
                ImGui.text(asset.asset().getClass().getSimpleName());
                ImGui.sameLine();
            }

            ImGui.endTable();
        }
    }
}
