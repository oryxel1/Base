package oxy.bascenario.editor.miniuis;

import imgui.ImColor;
import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.managers.other.Asset;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.timeline.Timeline;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.*;
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
            object = timeline.getSelectedObject();
        }

        final Consumer<FileInfo> PICK_CONSUMER = file -> {
            String lowercase = file.path().toLowerCase(Locale.ROOT);
            if (lowercase.endsWith(".mp3") || lowercase.endsWith(".ogg") || lowercase.endsWith(".wav")) {
                new Thread(() -> Base.instance().assetsManager().load(scenario.name(), file)).start();
            } else {
                Base.instance().assetsManager().load(scenario.name(), file);
            }
        };
        ImGuiUtils.pick(PICK_CONSUMER, scenario, "Load File", filter != null ? filter : "*");
        ImGui.sameLine();
        ImGuiUtils.pickFolder(PICK_CONSUMER, scenario, "Load Folder");

        final List<Asset<?>> noFolders = new ArrayList<>();

        final Map<String, List<Asset<?>>> folders = new HashMap<>();
        for (Asset<?> asset : Base.instance().assetsManager().assets()) {
            if (asset.file().internal() || !Objects.equals(asset.scenario(), scenario.name())) {
                continue;
            }

            if (filter != null && !filter.equals("*")) {
                boolean valid = false;
                for (String ext : filter.replace(" ", "").split(",")) {
                    if (asset.file().path().toLowerCase(Locale.ROOT).endsWith("." + ext)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    continue;
                }
            }

            if (!asset.file().path().contains("\\")) {
                noFolders.add(asset);
                continue;
            }

            String[] split = asset.file().path().split("\\\\");
            if (split.length != 2) {
                noFolders.add(asset);
                continue; // We don't support stacked folder, if they do that, their fault.
            }

            folders.computeIfAbsent(split[0], p -> new ArrayList<>()).add(asset);
        }

        if (ImGui.beginTable("2ways", 2, ImGuiTableFlags.BordersV | ImGuiTableFlags.BordersOuterH | ImGuiTableFlags.Resizable | ImGuiTableFlags.RowBg | ImGuiTableFlags.NoBordersInBody)) {
            ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.NoHide);
            ImGui.tableSetupColumn("Type");
            ImGui.tableHeadersRow();

            for (Map.Entry<String, List<Asset<?>>> folder : folders.entrySet()) {
                ImGui.tableNextRow();
                ImGui.tableNextColumn();
                if (ImGui.treeNodeEx(folder.getKey(), ImGuiTreeNodeFlags.SpanAllColumns | ImGuiTreeNodeFlags.DefaultOpen)) {
                    renderAssets(timeline, scenario.name(), folder.getValue(), consumer, true);
                    ImGui.treePop();
                }
            }

            renderAssets(timeline, scenario.name(), noFolders, consumer, false);

            ImGui.endTable();
        }
    }

    private static void renderAssets(Timeline timeline, String scenario, List<Asset<?>> assets, Consumer<FileInfo> consumer, boolean split) {
        for (Asset<?> asset : assets) {
            if (asset.file().internal() || !Objects.equals(asset.scenario(), scenario)) {
                continue;
            }

            ImGui.tableNextRow();
            ImGui.tableNextColumn();
            if (ImGui.selectable(split ? asset.file().path().split("\\\\")[1] : asset.file().path())) {
                if (consumer != null) {
                    if (object == timeline.getSelectedObject()) {
                        consumer.accept(asset.file());
                    }
                    AssetsUI.consumer = null;
                }

                ImGui.closeCurrentPopup();
                filter = null;
            }
            ImGui.tableNextColumn();
            ImGui.text(asset.asset().getClass().getSimpleName());
        }
    }
}
