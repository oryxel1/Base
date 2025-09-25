package com.bascenario.util.render;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

// Just so the code can look cleaner!
public class ImGuiUtil {
    public static int COUNTER = 0;

    public static int inputInt(String name, int value) {
        final ImInt imInt = new ImInt(value);
        ImGui.inputInt(name + "##" + COUNTER++, imInt);
        return imInt.get();
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
