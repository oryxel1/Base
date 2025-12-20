package oxy.bascenario.editor.element;

import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.editor.ScenarioEditorScreen;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class EventAdder {
    private final ScenarioEditorScreen screen;
    private final Timeline timeline;

    // TODO...
    public void render() {
        ImGui.begin("Actions");

        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));

        ImGui.separatorText("Animations");

        add("Play Animation", new PlayAnimationEvent(0, "bascenarioengine:default-shake", false));
        add("Play Sprite Animation", new SpriteAnimationEvent(0, 0.2f, "Idle_01", 1));
        add("Stop Animation", new StopAnimationEvent(0, "bascenarioengine:default-shake"));

        ImGui.separatorText("Dialogues");

        add("Close Dialogue", new CloseDialogueEvent());
        {
            final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Yes", 0);
            map.put("No", 0);
            add("Show Options", new ShowOptionsEvent(map));
        }

        ImGui.separatorText("Objects");

        add("Move Object", null);
        add("Offset Object", null);
        add("Scale Object", null);
        add("Pivot Object", null);
        add("Rotate Object", null);
        add("Apply Effect", null);

        ImGui.separatorText("Sounds");

        add("Play Sound", null);
        add("Stop Sound", null);
        add("Pause Sound", null);
        add("Resume Sound", null);

        ImGui.separatorText("Others");

        add("Color Overlay", null);
        add("Change background", null);

        ImGui.end();
    }

    private void add(String label, Object element) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            return;
        }

//        long duration = TimeCompiler.timeFromElement(element);
//        if (duration == Long.MAX_VALUE) {
//            duration = 1000L;
//        }

//        final Track track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
//        track.put(timeline.getTimestamp(), new Pair<>(new Track.Cache(element, null, null), duration));
    }
}
