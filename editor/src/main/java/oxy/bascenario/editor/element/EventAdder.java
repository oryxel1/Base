package oxy.bascenario.editor.element;

import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiHoveredFlags;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.api.utils.math.Vec3;
import oxy.bascenario.editor.utils.TimeCompiler;
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

        add("Play Animation",
                "Play an defined animation, default or custom with an element on the selected track.",
                new PlayAnimationEvent(0, "bascenarioengine:default-shake", false));

        add("Play Sprite Animation",
                "Play an animation that is defined in the spine (.skel) file you imported to a sprite character on the selected track.",
                new SpriteAnimationEvent(0, 0.2f, "Idle_01", 1));

        add("Stop Animation",
                "Stop any defined animation, (note) not sprite animation.",
                new StopAnimationEvent(0, "bascenarioengine:default-shake"));


        ImGui.separatorText("Dialogues");

        add("Start Dialogue",
                "Start a dialogue like in game, with values like name, association or should this render background, and of course dialogues.",
                new StartDialogueEvent(0, "", "", true, DUMMY_DIALOGUE));

        add("Add Dialogue",
                "Add a new dialogue (text) onto any existing playing dialogue, allows for playing a certain dialogue text a bit late.",
                new AddDialogueEvent(0, DUMMY_DIALOGUE));

        add("Close Dialogue", "Close any currently present dialogue", new CloseDialogueEvent());
        {
            final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Yes", 0);
            map.put("No", 0);
            add("Show Options",
                    "Show options that the user could choose, result in a dialogue index that determine which dialogue to play.",
                    new ShowOptionsEvent(map));
        }

        ImGui.separatorText("Objects");

        add("Move Object",
                "This move an object.",
                new PositionElementEvent(0, 1000, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.POSITION));

        add("Offset Object",
                "Offset an object by a certain value, so that no matter where the object is the position will always be offset by that value.",
                new PositionElementEvent(0, 1000, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.OFFSET));

        add("Scale Object",
                "This scale an object.",
                new PositionElementEvent(0, 1000, new Vec2(1, 1), Easing.LINEAR, PositionElementEvent.Type.SCALE));

        add("Pivot Object",
                "Define a \"pivot\" point, this will be the point used when rotating and the object will be rotate around this point.",
                new PositionElementEvent(0, 1000, new Vec2(0, 0), Easing.LINEAR, PositionElementEvent.Type.PIVOT));

        add("Rotate Object",
                "This rotate an object.",
                new RotateElementEvent(0, 1000, new Vec3(0, 0, 0), Easing.LINEAR));

        add("Apply Effect",
                "Apply an effect on top of any element on the selected track, eg: hologram, blur, rainbow.",
                new ElementEffectEvent(0, Effect.HOLOGRAM, Axis.Y));

        add("Remove Effect",
                "Remove an effect that being applied on top of an element on the selected track.",
                new ElementEffectEvent(0, Effect.HOLOGRAM, ElementEffectEvent.Type.REMOVE));

        add("Color Overlay",
                "Apply a color overlay, this will put a color overlay on top of the object.",
                new ColorOverlayEvent(0, 500, Color.WHITE));

        add("Set Color",
                "Like color overlay, but instead of overlaying color it change the object color itself.",
                new SetColorEvent(0, 500, Color.WHITE));

        ImGui.separatorText("Sounds");

//        add("Play Sound", new PlaySoundEvent(new Sound(0, null, 1, true), -1, 0));
//        add("Stop Sound", new StopSoundEvent(0, 100));
        add("Sound Volume",
                "Change a playing sound volume.",
                new SoundVolumeEvent(0, 1000, 1, Easing.LINEAR));
//        add("Pause Sound", new SoundEvent(0, SoundEvent.Event.PAUSE));
//        add("Resume Sound", new SoundEvent(0, SoundEvent.Event.RESUME));

        ImGui.separatorText("Others");

        add("Screen Color",
                "This will cover the entire screen with a color you choose on a render layer that you also choose.",
                new ColorOverlayEvent(RenderLayer.TOP, 500, Color.WHITE));
//        add("Change background", null);

        ImGui.end();
    }

    private void add(String label, String tooltip, Event event) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            if (ImGui.isItemHovered(ImGuiHoveredFlags.DelayNormal)) {
                ImGui.setTooltip(tooltip);
            }
            return;
        }
        timeline.setSelectedElement(null);

        long duration = TimeCompiler.compileTime(event);
        final Track track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
        track.put(timeline.getTimestamp(), new Pair<>(new Track.ObjectOrEvent(event, null, null, false), duration));
    }

    private Track findNonOccupiedSlot(long time, long duration) {
        int i = 0;
        Track track;
        while ((track = timeline.getTrack(i)) != null) {
            if (track.isNotOccupied(time, duration, null)) {
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
