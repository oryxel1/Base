package oxy.bascenario.editor.inspector;

import imgui.ImColor;
import imgui.ImGui;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.utils.ImGuiUtils;

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

        renderer.getPair().left().requireWait(ImGuiUtils.checkbox("Wait For Dialogue", renderer.getPair().left().requireWait()));
        ImGui.end();
    }
}
