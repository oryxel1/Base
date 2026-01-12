package oxy.bascenario.editor.miniuis;

import imgui.ImColor;
import imgui.ImGui;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.editor.timeline.Timeline;
import oxy.bascenario.managers.ScenarioManager;
import oxy.bascenario.utils.ImGuiUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

        ImGuiUtils.pick(f -> {}, scenario, "Load File", filter != null ? filter : "*");
        ImGui.sameLine();
        ImGuiUtils.pickFolder(f -> {}, scenario, "Load Folder");

        final List<FileInfo> noFolders = new ArrayList<>();
        final Map<String, List<FileInfo>> folders = new HashMap<>();

        final Path path = new File(new File(ScenarioManager.SAVE_DIR, scenario.name()), "files").toPath();
        try (final Stream<Path> stream = Files.walk(path)) {
            List<File> files = stream.filter(Files::isRegularFile).map(Path::toFile).toList();

            for (File file : files) {
                FileInfo info = new FileInfo(file.getAbsolutePath().replace(path.toFile().getAbsolutePath() + "\\", ""), false, false);
                if (filter != null && !filter.equals("*")) {
                    boolean valid = false;
                    for (String ext : filter.replace(" ", "").split(",")) {
                        if (info.path().toLowerCase(Locale.ROOT).endsWith("." + ext)) {
                            valid = true;
                            break;
                        }
                    }

                    if (!valid) {
                        continue;
                    }
                }

                if (!info.path().contains("\\")) {
                    noFolders.add(info);
                    continue;
                }

                String[] split = info.path().split("\\\\");
                if (split.length != 2) {
                    noFolders.add(info);
                    continue; // We don't support stacked folder, if they do that, their fault.
                }

                folders.computeIfAbsent(split[0], p -> new ArrayList<>()).add(info);
            }
        }  catch (IOException ignored) {
        }

        if (ImGui.beginTable("1ways", 1, ImGuiTableFlags.BordersV | ImGuiTableFlags.BordersOuterH | ImGuiTableFlags.Resizable | ImGuiTableFlags.RowBg | ImGuiTableFlags.NoBordersInBody)) {
            ImGui.tableSetupColumn("Name", ImGuiTableColumnFlags.NoHide);
            ImGui.tableHeadersRow();

            for (Map.Entry<String, List<FileInfo>> folder : folders.entrySet()) {
                ImGui.tableNextRow();
                ImGui.tableNextColumn();
                if (ImGui.treeNodeEx(folder.getKey(), ImGuiTreeNodeFlags.SpanAllColumns | ImGuiTreeNodeFlags.DefaultOpen)) {
                    renderAssets(timeline, folder.getValue(), consumer, true);
                    ImGui.treePop();
                }
            }

            renderAssets(timeline, noFolders, consumer, false);

            ImGui.endTable();
        }
    }

    private static void renderAssets(Timeline timeline, List<FileInfo> files, Consumer<FileInfo> consumer, boolean split) {
        for (FileInfo file : files) {
            ImGui.tableNextRow();
            ImGui.tableNextColumn();
            if (ImGui.selectable(split ? file.path().split("\\\\")[1] : file.path())) {
                if (consumer != null) {
                    if (object == timeline.getSelectedObject()) {
                        consumer.accept(file);
                    }
                    AssetsUI.consumer = null;
                }

                ImGui.closeCurrentPopup();
                filter = null;
            }
        }
    }
}
