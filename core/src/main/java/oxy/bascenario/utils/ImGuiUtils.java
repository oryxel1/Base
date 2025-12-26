package oxy.bascenario.utils;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.utils.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

// Just so the code can look cleaner!
public class ImGuiUtils {
    public static int COUNTER = 0;

    public static void pick(Consumer<FileInfo> consumer, Scenario.Builder scenario, final String name, boolean skip, String filter) {
        ImGui.text(name + ": ");
        ImGui.sameLine();
        if (!ImGui.button("Select file##" + COUNTER++) && !skip) {
            return;
        }

        final String pick = NFDUtils.pickFile(filter);
        if (pick.isEmpty()) {
            return;
        }

        final FileInfo fileInfo = new FileInfo(new File(pick).getName(), false, false);
        final String path = Base.instance().scenarioManager().path(scenario.name(), fileInfo);

        try {
            Files.write(Path.of(path), Files.readAllBytes(Path.of(pick)));
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
        final ImString imString = new ImString(value);
        ImGui.inputText(name + "##" + COUNTER++, imString, ImGuiInputTextFlags.NoHorizontalScroll | ImGuiInputTextFlags.CallbackResize);
        return imString.get();
    }

    public static String inputMultiLineText(String name, String value) {
        final ImString imString = new ImString(value);
        ImGui.inputTextMultiline(name + "##" + COUNTER++, imString, ImGuiInputTextFlags.CallbackResize);
        return imString.get();
    }

    public static boolean checkbox(String name, boolean value) {
        return ImGui.checkbox(name + "##" + COUNTER++, value) != value;
    }

    public static float sliderFloat(String name, float value, float min, float max) {
        float[] newValue = new float[] {value};
        ImGui.sliderFloat(name + "##" + COUNTER++, newValue, min, max);
        return newValue[0];
    }

    public static int sliderInt(String name, int value, int min, int max) {
        int[] newValue = new int[] {value};
        ImGui.sliderInt(name + "##" + COUNTER++, newValue, min, max);
        return newValue[0];
    }
}