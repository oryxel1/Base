package oxy.bascenario.editor.screens.element;

import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.editor.TimeCompiler;
import oxy.bascenario.editor.screens.ScenarioEditorScreen;
import oxy.bascenario.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ElementAdder {
    private static final Dialogue DUMMY_DIALOGUE = Dialogue.builder().add("Hello World!").build();

    private final ScenarioEditorScreen screen;
    private final Timeline timeline;

    public void render() {
        ImGui.begin("Objects");
        
        addDialogue("Start Dialogue", new StartDialogueEvent(0, "", "", true, DUMMY_DIALOGUE));
        addDialogue("Add Dialogue", new AddDialogueEvent(0, DUMMY_DIALOGUE));

        ImGui.separatorText("");

        add("Preview", new Preview("Title", "Subtitle", null));
        add("Emoticon", new Emoticon(1000L, EmoticonType.NOTE, true));
        add("Sprite", new Sprite(null, null));
        add("Image", new RendererImage(new Image(null), Color.WHITE, 100, 100));
        add("Gif", new RendererImage(new AnimatedImage(null, true), Color.WHITE, 100, 100));
        add("Text", new Text(new ArrayList<>(), 42));
        add("Circle", new Circle(20, Color.WHITE, false));
        add("Rectangle", new Rectangle(100, 100, Color.WHITE, false));
//        add("Triangle", null);
        add("Location Info", new LocationInfo("Location Name", 2500, 500));

        ImGui.end();
    }

    private void add(String label, Object element) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            return;
        }

        long duration = TimeCompiler.timeFromElement(element);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        final Track track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
        track.put(timeline.getTimestamp(), new Pair<>(new Track.Cache(element, null, null), duration));
    }

    private void addDialogue(String label, Event<?> e) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            return;
        }

        long duration = TimeCompiler.compileTime(e instanceof StartDialogueEvent event ? event.getDialogues() : e instanceof AddDialogueEvent event1 ? event1.getDialogues() : new Dialogue[] {});
        final Track track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
        track.put(timeline.getTimestamp(), new Pair<>(new Track.Cache(e, null, null), duration));
    }

    private Track findNonOccupiedSlot(long time, long duration) {
        int i = 0;
        Track track;
        while ((track = timeline.getTracks().get(i)) != null) {
            if (!track.isOccupied(time, duration, null)) {
                break;
            }
            i++;
        }
        if (track == null) {
            track = new Track(timeline, i);
            timeline.getTracks().put(i, track);
        }

        return track;
    }
}
