package oxy.bascenario.editor.inspector.impl.events;

import imgui.ImGui;
import imgui.type.ImBoolean;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.*;

public class OptionsInspector {
    public static ShowQuestionSelectionEvent render(ShowQuestionSelectionEvent event) {
        FontType type = FontType.values()[ImGuiUtils.combo("Font Type", event.type().ordinal(), FontType.getAlls())];
        String question = ImGuiUtils.inputText("Question", event.question());

        boolean add = ImGui.button("New Answer!");

        final List<ShowQuestionSelectionEvent.Answer> answers = new ArrayList<>();
        for (ShowQuestionSelectionEvent.Answer answer : event.answers()) {
            final ImBoolean imBoolean = new ImBoolean(true);
            String option = answer.answer();
            int redirect = answer.dialogueIndex();
            boolean grayedOut = answer.grayedOut();
            if (ImGui.collapsingHeader("Option##" + ImGuiUtils.COUNTER++, imBoolean)) {
                option = ImGuiUtils.inputText("Option", option);
                redirect = ImGuiUtils.inputInt("Redirect To", redirect);
                grayedOut = ImGuiUtils.checkbox("Grayed Out", grayedOut);
            }
            if (imBoolean.get()) {
                answers.add(new ShowQuestionSelectionEvent.Answer(redirect, option, grayedOut));
            }
        }

        if (add) {
            answers.add(new ShowQuestionSelectionEvent.Answer(0, "", false));
        }

        return new ShowQuestionSelectionEvent(type, question, answers);
    }

    public static ShowOptionsEvent render(ShowOptionsEvent event) {
        final Map<String, Integer> options = new LinkedHashMap<>();

        FontType type = FontType.values()[ImGuiUtils.combo("Font Type", event.type().ordinal(), FontType.getAlls())];
        boolean add = ImGui.button("New Option!");

        for (Map.Entry<String, Integer> entry : event.options().entrySet()) {
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

        return new ShowOptionsEvent(type, options);
    }
}
