package oxy.bascenario.editor.inspector.elements;

import imgui.ImGui;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class OptionsInspector {
    public static ShowOptionsEvent render(ShowOptionsEvent event) {
        final Map<String, Integer> options = new LinkedHashMap<>();

        boolean add = ImGui.button("New option!");

        for (Map.Entry<String, Integer> entry : event.getOptions().entrySet()) {
            String option = ImGuiUtils.inputText("", entry.getKey());
            ImGui.sameLine();
            ImGui.text("->");
            ImGui.sameLine();
            int redirect = ImGuiUtils.inputInt("", entry.getValue());
            ImGui.sameLine();
            ImGui.button("-##" + ImGuiUtils.COUNTER++);

            options.put(option, redirect);
        }

        if (add) {
            options.put("", 0);
        }

        return new ShowOptionsEvent(options);
    }
}
