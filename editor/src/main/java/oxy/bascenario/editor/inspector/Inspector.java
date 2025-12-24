package oxy.bascenario.editor.inspector;

import imgui.ImColor;
import imgui.ImGui;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.editor.inspector.elements.*;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.utils.ImGuiUtils;
import oxy.bascenario.utils.Pair;

@RequiredArgsConstructor
public class Inspector {
    private final BaseScenarioEditorScreen screen;
    private final Timeline timeline;

    public void render() {
        ImGui.begin("Inspector");
        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));
        final Track.ElementRenderer renderer = timeline.getSelectedElement();
        if (renderer == null) {
            ImGui.end();
            return;
        }

        Pair<Track.Cache, Long> pair = renderer.getPair();

        boolean requireWait = ImGuiUtils.checkbox("Wait For Dialogue", pair.left().requireWait());

        final Object old = pair.left().object();
        pair.left().object(switch (pair.left().object()) {
            case Preview preview -> PreviewInspector.render(preview);
            case Emoticon emoticon -> EmoticonInspector.render(emoticon);
            case LocationInfo info -> LocationInfoInspector.render(info);
            case Text text -> TextInspector.render(text);
            case StartDialogueEvent event -> DialogueInspector.render(event);
            case AddDialogueEvent event -> DialogueInspector.render(event);
            default -> old;
        });
        if (!old.equals(pair.left().object()) || requireWait != pair.left().requireWait()) {
            if (screen.getTimeline().getTimestamp() >= renderer.getStartTime()) {
                screen.getTimeline().updateScenario(true);
            }
        }
        pair.left().requireWait(requireWait);

        ImGui.end();
    }
}
