package oxy.bascenario.editor.miniuis;

import imgui.ImColor;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiHoveredFlags;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.editor.timeline.ObjectOrEvent;
import oxy.bascenario.editor.timeline.Timeline;
import oxy.bascenario.editor.utils.TimeCompiler;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.editor.utils.SoundAsElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ObjectsUI {
    private final BaseScenarioEditorScreen screen;
    private final Timeline timeline;

    public void render() {
        ImGui.begin("Objects");

        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));

        add("Preview",
                "Show up the \"preview\" of the scenario like when you first play the story like in the game.\nWith title and (optional) subtitle and optional background.",
                new Preview("Title", "Subtitle", null));

        add("Emoticon",
                "Popup an emoji that show emotions like in game, eg: Angry, Thinking, etc.",
                new Emoticon(1000, EmoticonType.NOTE, true));

        add("Sprite",
                "A character sprite (spine), in binary format (.skel), that allows for dynamic animations and other niche stuff.",
                new Sprite(null, null));

        add("Image",
                "An image... It's an image.",
                new Image(null, Color.WHITE, 100, 100));

        add("Gif",
                "A GIF... It's a GIF.",
                new AnimatedImage(null, 0, true, Color.WHITE, 100, 100));

        add("Text",
                "A text with defined size with multiple segments, each segments with customizable font, color, etc.",
                new Text(new ArrayList<>(List.of(TextSegment.builder().text("Hello World!").build())), 42));

        add("Circle",
                "round round we go.",
                new Circle(20, Color.WHITE, false));

        add("Rectangle",
                """
                        Why was the rectangle in love with a triangle?
                        She has acute angle.
                        """,
                new Rectangle(100, 100, Color.WHITE, false));

//        add("Triangle", null);
        add("Location Info",
                "Popup a box that show the current location name like in game.",
                new LocationInfo("Location Name", 2500, 500));

        add("Sound",
                "It's a sound, this will play a sound.",
                new SoundAsElement(new Sound(0, null, 1, false), 0, 0, 0, 0));

        ImGui.end();
    }

    private void add(String label, String tooltip, Object element) {
        if (!ImGui.button(label, new ImVec2(ImGui.getWindowSize().x - 20, 50))) {
            if (ImGui.isItemHovered(ImGuiHoveredFlags.DelayNormal)) {
                ImGui.setTooltip(tooltip);
            }
            return;
        }

        timeline.setSelectedObject(null);

        long duration = TimeCompiler.compileTime(element);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        final int track = findNonOccupiedSlot(timeline.getTimestamp(), duration);
        timeline.put(track, timeline.getTimestamp(), duration, element, element instanceof SoundAsElement ? null : RenderLayer.ABOVE_DIALOGUE, true, element instanceof SoundAsElement ? null : new Vec2(0, 0));
    }

    private int findNonOccupiedSlot(long time, long duration) {
        final Map<Integer, List<ObjectOrEvent>> map = new HashMap<>();
        timeline.getObjects().forEach(object -> map.computeIfAbsent(object.track, n -> new ArrayList<>()).add(object));

        int i = 0;
        List<ObjectOrEvent> track;
        while ((track = map.get(i)) != null) {
            boolean occupied = false;
            for (ObjectOrEvent object : track) {
                final long maxTime = object.start + object.duration, minTime = object.start;
                if (maxTime >= time && minTime <= time + duration) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                return i;
            }
            i++;
        }

        return i;
    }
}
