package oxy.bascenario.editor.element;

import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.SoundEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.editor.TimeCompiler;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.utils.Pair;

import java.util.LinkedHashMap;

@RequiredArgsConstructor
public class EventAdder {
    private static final Dialogue DUMMY_DIALOGUE = Dialogue.builder().add("Hello World!").build();

    private final BaseScenarioEditorScreen screen;
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

        add("Start Dialogue", new StartDialogueEvent(0, "", "", true, DUMMY_DIALOGUE));
        add("Add Dialogue", new AddDialogueEvent(0, DUMMY_DIALOGUE));

        add("Close Dialogue", new CloseDialogueEvent());
        {
            final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Yes", 0);
            map.put("No", 0);
            add("Show Options", new ShowOptionsEvent(map));
        }

        ImGui.separatorText("Objects");

        add("Move Object", new PositionElementEvent(0, 1000L, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.POSITION));
        add("Offset Object", new PositionElementEvent(0, 1000L, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.OFFSET));
        add("Scale Object", new PositionElementEvent(0, 1000L, new Vec2(1, 1), Easing.LINEAR, PositionElementEvent.Type.SCALE));
        add("Pivot Object", new PositionElementEvent(0, 1000L, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.PIVOT));
        add("Rotate Object", new RotateElementEvent(0, 1000L, new Vec3(0, 0, 0), Easing.LINEAR));
        add("Apply Effect", new ElementEffectEvent(0, Effect.HOLOGRAM, Axis.Y));
        add("Remove Effect", new ElementEffectEvent(0, Effect.HOLOGRAM, ElementEffectEvent.Type.REMOVE));
        add("Color Overlay", new ColorOverlayEvent(0, 500, Color.WHITE));

        ImGui.separatorText("Sounds");

        add("Play Sound", new PlaySoundEvent(new Sound(0, null, 1, true), -1, 0));
        add("Sound Volume", new SoundVolumeEvent(0, 1000L, 1, Easing.LINEAR));
        add("Stop Sound", new StopSoundEvent(0, 100));
        add("Pause Sound", new SoundEvent(0, SoundEvent.Event.PAUSE));
        add("Resume Sound", new SoundEvent(0, SoundEvent.Event.RESUME));

        ImGui.separatorText("Others");

        add("Screen Color", new ColorOverlayEvent(RenderLayer.TOP, 500, Color.WHITE));
//        add("Change background", null);

        ImGui.end();
    }

    private void add(String label, Event<?> event) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            return;
        }
        timeline.setSelectedElement(null);

        long duration = TimeCompiler.compileTime(event);
        final Track track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
        track.put(timeline.getTimestamp(), new Pair<>(new Track.Cache(event, null, null, false), duration));
    }

    private Track findNonOccupiedSlot(long time, long duration) {
        int i = 0;
        Track track;
        while ((track = timeline.getTrack(i)) != null) {
            if (!track.isOccupied(time, duration, null)) {
                break;
            }
            i++;
        }
        if (track == null) {
            track = new Track(timeline, i);
            timeline.putTrack(i, track);
        }

        return track;
    }
}
