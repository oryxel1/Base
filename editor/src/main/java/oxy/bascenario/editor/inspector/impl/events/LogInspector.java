package oxy.bascenario.editor.inspector.impl.events;

import imgui.ImGui;
import imgui.type.ImBoolean;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.RedirectDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.dialogue.enums.OffsetType;
import oxy.bascenario.api.event.dialogue.enums.TextOffset;
import oxy.bascenario.api.event.log.AddLogEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.editor.inspector.impl.objects.TextInspector;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.ArrayList;
import java.util.List;

public class LogInspector {
    public static AddLogEvent render(Scenario.Builder scenario, AddLogEvent event) {
        AddLogEvent.Builder builder = event.toBuilder();

        AddLogEvent.Type type = AddLogEvent.Type.values()[ImGuiUtils.combo("Action", event.type().ordinal(), AddLogEvent.Type.getAlls())];
        ImGui.separatorText("");
        if (type == AddLogEvent.Type.CLEAR) {
            OffsetType offsetType = OffsetType.values()[ImGuiUtils.combo("Text Offset", event.offset().type().ordinal(), OffsetType.getAlls())];
            float offset = event.offset().offset();
            if (offsetType == OffsetType.Custom) {
                offset = ImGuiUtils.sliderFloat("Offset X", offset, 0, 1920);
            }
            builder.offset(new TextOffset(offsetType, offset));
            ImGui.separatorText("");
        }
        builder.type(type);

        builder.index(ImGuiUtils.inputInt("Dialogue Index", event.index()));

        final List<Dialogue> list = new ArrayList<>();
        if (ImGui.button("New log!##" + ImGuiUtils.COUNTER++)) {
            list.add(Dialogue.builder().build());
        }

        for (Dialogue dialogue : event.dialogues()) {
            final ImBoolean imBoolean = new ImBoolean(true);
            if (ImGui.collapsingHeader("Log##" + ImGuiUtils.COUNTER++, imBoolean)) {
                dialogue = DialogueInspector.render(scenario, dialogue);
            }

            if (imBoolean.get()) {
                list.add(dialogue);
            }
        }
        builder.dialogues(list.toArray(new Dialogue[0]));

        return builder.build();
    }
}
