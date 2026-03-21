package oxy.bascenario.utils;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.utils.files.NFDUtils;
import oxy.bascenario.utils.font.FontUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

// Just so the code can look cleaner!
public class ImGuiUtils {
    public static int COUNTER = 0;

    public static void pickFolder(Consumer<FileInfo> consumer, Scenario.Builder scenario, final String name) {
        if (!ImGui.button(name + "##" + COUNTER++)) {
            return;
        }

        final String pick = NFDUtils.pickFolder();
        if (pick.isEmpty()) {
            return;
        }

        File folder = new File(pick);
        try (final Stream<Path> stream = Files.walk(folder.toPath())) {
            List<File> files = stream.filter(Files::isRegularFile).map(Path::toFile).toList();

            new Thread(() -> {
                try {
                    for (final File file : files) {
                        String strip = file.getAbsolutePath().replace(folder.getAbsolutePath() + "\\", "");
                        FileInfo info = new FileInfo(folder.getName() + "\\" + strip, false, false);

                        File save = Base.instance().scenarioManager().file(scenario.name(), info);
                        if (save.isDirectory() && !save.delete()) {
                            continue;
                        }

                        Files.write(save.toPath(), Files.readAllBytes(file.toPath()));
                        consumer.accept(info);
                    }
                } catch (Exception ignored) {
                }
            }).start();
        } catch (Exception ignored) {
        }
    }

    public static void pick(Consumer<FileInfo> consumer, Scenario.Builder scenario, final String name, String filter) {
        if (!ImGui.button(name + "##" + COUNTER++)) {
            return;
        }

        final String pick = NFDUtils.pickFile(filter);
        if (pick.isEmpty()) {
            return;
        }

        final FileInfo fileInfo = new FileInfo(new File(pick).getName(), false, false);
        final File file = Base.instance().scenarioManager().file(scenario.name(), fileInfo);

        try {
            if (file.isDirectory() && !file.delete()) {
                return;
            }

            Files.write(file.toPath(), Files.readAllBytes(Path.of(pick)));
        } catch (IOException e) {
            return;
        }

        consumer.accept(fileInfo);
    }

    public static Color color(final String name, Color color) {
        final float[] rgba = new float[] { color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF() };
        ImGui.colorPicker4(name + "##" + COUNTER++, rgba);
        return Color.fromRGBAF(rgba);
    }

    public static int combo(final String label, int index, final String[] items) {
        final ImInt currentItem = new ImInt(index);
        ImGui.combo(label + "##" + COUNTER++, currentItem, items);
        return currentItem.get();
    }

    public static int inputInt(String name, int value) {
        final ImInt imInt = new ImInt(value);
        ImGui.inputInt(name + "##" + COUNTER++, imInt);
        return imInt.get();
    }

    public static float inputFloat(String name, float value) {
        final ImFloat imFloat = new ImFloat(value);
        ImGui.inputFloat(name + "##" + COUNTER++, imFloat);
        return imFloat.get();
    }

    public static void inputInt4(String name, int[] value) {
        ImGui.inputInt4(name + "##" + COUNTER++, value);
    }

    public static String inputText(String name, String value) {
        ImGui.pushFont(FontUtils.CHILLGOTHIC_17);
        final ImString imString = new ImString(value);
        ImGui.inputText(name + "##" + COUNTER++, imString, ImGuiInputTextFlags.NoHorizontalScroll | ImGuiInputTextFlags.CallbackResize);
        ImGui.popFont();
        return imString.get();
    }

    public static String inputMultiLineText(String name, String value) {
        ImGui.pushFont(FontUtils.CHILLGOTHIC_17);
        final ImString imString = new ImString(value);
        ImGui.inputTextMultiline(name + "##" + COUNTER++, imString, ImGuiInputTextFlags.CallbackResize);
        ImGui.popFont();
        return imString.get();
    }

    public static boolean checkbox(String name, boolean value) {
        return ImGui.checkbox(name + "##" + COUNTER++, value) != value;
    }

    public static float sliderFloat(String name, float value, float min, float max) {
        float[] newValue = new float[] {value};
        ImGui.sliderFloat(name + "##" + COUNTER++, newValue, min, max);
        ImGui.sameLine();
        return inputFloat("", newValue[0]);
    }

    public static int sliderInt(String name, int value, int min, int max) {
        int[] newValue = new int[] {value};
        ImGui.sliderInt(name + "##" + COUNTER++, newValue, min, max);
        ImGui.sameLine();
        return inputInt("", newValue[0]);
    }
    
    // https://github.com/ocornut/imgui/issues/707#issuecomment-2732535348
    public static void setupTheme() {
        ImGuiStyle style = ImGui.getStyle();

        // Base colors for a pleasant and modern dark theme with dark accents
        style.setColor(ImGuiCol.Text, 0.92f, 0.93f, 0.94f, 1.00f);                  // Light grey text for readability
        style.setColor(ImGuiCol.TextDisabled, 0.50f, 0.52f, 0.54f, 1.00f);          // Subtle grey for disabled text
        style.setColor(ImGuiCol.WindowBg, 0.14f, 0.14f, 0.16f, 1.00f);              // Dark background with a hint of blue
        style.setColor(ImGuiCol.ChildBg, 0.16f, 0.16f, 0.18f, 1.00f);               // Slightly lighter for child elements
        style.setColor(ImGuiCol.PopupBg, 0.18f, 0.18f, 0.20f, 1.00f);               // Popup background
        style.setColor(ImGuiCol.Border, 0.28f, 0.29f, 0.30f, 0.60f);                // Soft border color
        style.setColor(ImGuiCol.BorderShadow, 0.00f, 0.00f, 0.00f, 0.00f);          // No border shadow
        style.setColor(ImGuiCol.FrameBg, 0.20f, 0.22f, 0.24f, 1.00f);               // Frame background
        style.setColor(ImGuiCol.FrameBgHovered, 0.22f, 0.24f, 0.26f, 1.00f);        // Frame hover effect
        style.setColor(ImGuiCol.FrameBgActive, 0.24f, 0.26f, 0.28f, 1.00f);         // Active frame background
        style.setColor(ImGuiCol.TitleBg, 0.14f, 0.14f, 0.16f, 1.00f);               // Title background
        style.setColor(ImGuiCol.TitleBgActive, 0.16f, 0.16f, 0.18f, 1.00f);         // Active title background
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.14f, 0.14f, 0.16f, 1.00f);      // Collapsed title background
        style.setColor(ImGuiCol.MenuBarBg, 0.20f, 0.20f, 0.22f, 1.00f);             // Menu bar background
        style.setColor(ImGuiCol.ScrollbarBg, 0.16f, 0.16f, 0.18f, 1.00f);           // Scrollbar background
        style.setColor(ImGuiCol.ScrollbarGrab, 0.24f, 0.26f, 0.28f, 1.00f);         // Dark accent for scrollbar grab
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.28f, 0.30f, 0.32f, 1.00f);  // Scrollbar grab hover
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.32f, 0.34f, 0.36f, 1.00f);   // Scrollbar grab active
        style.setColor(ImGuiCol.CheckMark, 0.46f, 0.56f, 0.66f, 1.00f);             // Dark blue checkmark
        style.setColor(ImGuiCol.SliderGrab, 0.36f, 0.46f, 0.56f, 1.00f);            // Dark blue slider grab
        style.setColor(ImGuiCol.SliderGrabActive, 0.40f, 0.50f, 0.60f, 1.00f);      // Active slider grab
        style.setColor(ImGuiCol.Button, 0.24f, 0.34f, 0.44f, 1.00f);                // Dark blue button
        style.setColor(ImGuiCol.ButtonHovered, 0.28f, 0.38f, 0.48f, 1.00f);         // Button hover effect
        style.setColor(ImGuiCol.ButtonActive, 0.32f, 0.42f, 0.52f, 1.00f);          // Active button
        style.setColor(ImGuiCol.Header, 0.24f, 0.34f, 0.44f, 1.00f);                // Header color similar to button
        style.setColor(ImGuiCol.HeaderHovered, 0.28f, 0.38f, 0.48f, 1.00f);         // Header hover effect
        style.setColor(ImGuiCol.HeaderActive, 0.32f, 0.42f, 0.52f, 1.00f);          // Active header
        style.setColor(ImGuiCol.Separator, 0.28f, 0.29f, 0.30f, 1.00f);             // Separator color
        style.setColor(ImGuiCol.SeparatorHovered, 0.46f, 0.56f, 0.66f, 1.00f);      // Hover effect for separator
        style.setColor(ImGuiCol.SeparatorActive, 0.46f, 0.56f, 0.66f, 1.00f);       // Active separator
        style.setColor(ImGuiCol.ResizeGrip, 0.36f, 0.46f, 0.56f, 1.00f);            // Resize grip
        style.setColor(ImGuiCol.ResizeGripHovered, 0.40f, 0.50f, 0.60f, 1.00f);     // Hover effect for resize grip
        style.setColor(ImGuiCol.ResizeGripActive, 0.44f, 0.54f, 0.64f, 1.00f);      // Active resize grip
        style.setColor(ImGuiCol.Tab, 0.20f, 0.22f, 0.24f, 1.00f);                   // Inactive tab
        style.setColor(ImGuiCol.TabHovered, 0.28f, 0.38f, 0.48f, 1.00f);            // Hover effect for tab
        style.setColor(ImGuiCol.TabActive, 0.24f, 0.34f, 0.44f, 1.00f);             // Active tab color
        style.setColor(ImGuiCol.TabUnfocused, 0.20f, 0.22f, 0.24f, 1.00f);          // Unfocused tab
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.24f, 0.34f, 0.44f, 1.00f);    // Active but unfocused tab
        style.setColor(ImGuiCol.PlotLines, 0.46f, 0.56f, 0.66f, 1.00f);             // Plot lines
        style.setColor(ImGuiCol.PlotLinesHovered, 0.46f, 0.56f, 0.66f, 1.00f);      // Hover effect for plot lines
        style.setColor(ImGuiCol.PlotHistogram, 0.36f, 0.46f, 0.56f, 1.00f);         // Histogram color
        style.setColor(ImGuiCol.PlotHistogramHovered, 0.40f, 0.50f, 0.60f, 1.00f);  // Hover effect for histogram
        style.setColor(ImGuiCol.TableHeaderBg, 0.20f, 0.22f, 0.24f, 1.00f);         // Table header background
        style.setColor(ImGuiCol.TableBorderStrong, 0.28f, 0.29f, 0.30f, 1.00f);     // Strong border for tables
        style.setColor(ImGuiCol.TableBorderLight, 0.24f, 0.25f, 0.26f, 1.00f);      // Light border for tables
        style.setColor(ImGuiCol.TableRowBg, 0.20f, 0.22f, 0.24f, 1.00f);            // Table row background
        style.setColor(ImGuiCol.TableRowBgAlt, 0.22f, 0.24f, 0.26f, 1.00f);         // Alternate row background
        style.setColor(ImGuiCol.TextSelectedBg, 0.24f, 0.34f, 0.44f, 0.35f);        // Selected text background
        style.setColor(ImGuiCol.DragDropTarget, 0.46f, 0.56f, 0.66f, 0.90f);        // Drag and drop target
        style.setColor(ImGuiCol.NavHighlight, 0.46f, 0.56f, 0.66f, 1.00f);          // Navigation highlight
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f); // Windowing highlight
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.80f, 0.80f, 0.80f, 0.20f);     // Dim background for windowing
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.80f, 0.80f, 0.80f, 0.35f);      // Dim background for modal windows

        // Style adjustments
        style.setWindowPadding(8.00f, 8.00f);
        style.setFramePadding(5.00f, 2.00f);
        style.setCellPadding(6.00f, 6.00f);
        style.setItemSpacing(6.00f, 6.00f);
        style.setItemInnerSpacing(6.00f, 6.00f);
        style.setTouchExtraPadding(0.00f, 0.00f);
        style.setIndentSpacing(25);
        style.setScrollbarSize(11);
        style.setGrabMinSize(10);
        style.setChildBorderSize(1);
        style.setPopupBorderSize(1);
        style.setFrameBorderSize(1);
        style.setTabBorderSize(1);
        style.setWindowRounding(7);
        style.setChildRounding(4);
        style.setFrameRounding(3);
        style.setPopupRounding(4);
        style.setScrollbarRounding(8);
        style.setGrabRounding(3);
        style.setLogSliderDeadzone(4);
        style.setTabRounding(4);
    }
}