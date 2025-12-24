package oxy.bascenario.editor.inspector.elements.events;

import imgui.ImGui;
import imgui.type.ImBoolean;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class OptionsInspector {
    public static ShowOptionsEvent render(ShowOptionsEvent event) {
        final Map<String, Integer> options = new LinkedHashMap<>();

        boolean add = ImGui.button("New option!");

        for (Map.Entry<String, Integer> entry : event.getOptions().entrySet()) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Option##" + ImGuiUtils.COUNTER++, imBoolean) && imBoolean.get()) {
                String option = ImGuiUtils.inputText("Option", entry.getKey());
                int redirect = ImGuiUtils.inputInt("Redirect to", entry.getValue());
                options.put(option, redirect);
            }
        }

        if (add) {
            options.put("", 0);
        }

        return new ShowOptionsEvent(options);
    }
}
