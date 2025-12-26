package oxy.bascenario.editor.inspector.impl.events;

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
            String option = entry.getKey();
            int redirect = entry.getValue();
            if (ImGui.collapsingHeader("Option##" + ImGuiUtils.COUNTER++, imBoolean)) {
                option = ImGuiUtils.inputText("Option", entry.getKey());
                redirect = ImGuiUtils.inputInt("Redirect to", entry.getValue());
            }
            if (imBoolean.get()) {
                options.put(option, redirect);
            }
        }

        if (add) {
            options.put("", 0);
        }

        return new ShowOptionsEvent(options);
    }
}
