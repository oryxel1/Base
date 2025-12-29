package oxy.bascenario.editor.inspector.impl.events;

import imgui.ImGui;
import imgui.type.ImBoolean;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.editor.inspector.impl.objects.TextInspector;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.ArrayList;
import java.util.List;

public class DialogueInspector {
    public static AddDialogueEvent render(Scenario.Builder scenario, AddDialogueEvent event) {
        AddDialogueEvent.Builder builder = event.toBuilder();

        builder.index(ImGuiUtils.inputInt("Dialogue Index", event.index()));
        final List<Dialogue> list = new ArrayList<>();
        if (ImGui.button("New dialogue!##" + ImGuiUtils.COUNTER++)) {
            list.add(Dialogue.builder().build());
        }

        for (Dialogue dialogue : event.dialogues()) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Dialogue##" + ImGuiUtils.COUNTER++, imBoolean)) {
                dialogue = render(scenario, dialogue);
            }

            if (imBoolean.get()) {
                list.add(dialogue);
            }
        }
        builder.dialogues(list.toArray(new Dialogue[0]));

        return builder.build();
    }

    public static StartDialogueEvent render(Scenario.Builder scenario, StartDialogueEvent event) {
        StartDialogueEvent.Builder builder = event.toBuilder();
        builder.index(ImGuiUtils.inputInt("Dialogue Index", event.index()));
        builder.name(ImGuiUtils.inputText("Name", event.name()));
        builder.association(ImGuiUtils.inputText("Association", event.association()));
        builder.background(ImGuiUtils.checkbox("Background", event.background()));

        final List<Dialogue> list = new ArrayList<>();
        boolean add = ImGui.button("New dialogue!##" + ImGuiUtils.COUNTER++);

        for (Dialogue dialogue : event.dialogues()) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Dialogue##" + ImGuiUtils.COUNTER++, imBoolean)) {
                dialogue = render(scenario, dialogue);
            }

            if (imBoolean.get()) {
                list.add(dialogue);
            }
        }
        if (add) {
            list.add(Dialogue.builder().add("This is a dialogue text!").build());
        }
        builder.dialogues(list.toArray(new Dialogue[0]));

        return builder.build();
    }

    public static Dialogue render(Scenario.Builder scenario, Dialogue dialogue) {
        Dialogue.Builder builder = dialogue.toBuilder();
        builder.playSpeed(ImGuiUtils.sliderFloat("Play Speed", builder.playSpeed(), 0.01f, 50));
        builder.dialogue(TextInspector.render(scenario, dialogue.getDialogue()));
        return builder.build();
    }
}
