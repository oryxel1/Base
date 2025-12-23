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

        pair.left().requireWait(ImGuiUtils.checkbox("Wait For Dialogue", pair.left().requireWait()));

        final Object old = pair.left().object();
        switch (pair.left().object()) {
            case Preview preview -> pair.left().object(PreviewInspector.render(preview));
            case Emoticon emoticon -> pair.left().object(EmoticonInspector.render(emoticon));
            case LocationInfo info -> pair.left().object(LocationInfoInspector.render(info));
            case Text text -> pair.left().object(TextInspector.render(text));
            case StartDialogueEvent event -> pair.left().object(DialogueInspector.render(event));
            case AddDialogueEvent event -> pair.left().object(DialogueInspector.render(event));
            default -> {}
        }
        if (!old.equals(pair.left().object())) {
//            screen.update(); not worth it.
        }

        ImGui.end();
    }
}
