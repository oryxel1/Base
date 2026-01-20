package oxy.bascenario.editor.inspector;

import imgui.ImColor;
import imgui.ImGui;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.api.event.background.ClearBackgroundEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.RedirectDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.image.AnimatedImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.editor.inspector.impl.events.*;
import oxy.bascenario.editor.inspector.impl.objects.*;
import oxy.bascenario.editor.timeline.ObjectOrEvent;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.editor.timeline.Timeline;
import oxy.bascenario.editor.utils.AudioUtils;
import oxy.bascenario.editor.utils.NameUtils;
import oxy.bascenario.editor.utils.SoundAsElement;
import oxy.bascenario.editor.utils.TimeCompiler;
import oxy.bascenario.utils.ImGuiUtils;

import java.util.*;

@RequiredArgsConstructor
public class Inspector {
    private final BaseScenarioEditorScreen screen;
    private final Timeline timeline;

    @SuppressWarnings("ALL")
    public void render() {
        ImGui.begin("Inspector");
        ImGui.getWindowDrawList().addRectFilled(ImGui.getWindowPos(), ImGui.getWindowPos().plus(ImGui.getWindowSize()), ImColor.rgb(25, 25, 25));
        final ObjectOrEvent object = timeline.getSelectedObject();
        if (object == null) {
            ImGui.end();
            return;
        }

        ImGui.text(NameUtils.name(object.object));

        // I seriously can't think of a proper way to implement this in the editor, so we going with the always wait approch.
        boolean requireWait = true; // ImGuiUtils.checkbox("Wait For Dialogue", object.requireWait);
        RenderLayer layer = null;
        Vec2 vec2 = null;
        if (object.layer != null) {
            layer = RenderLayer.values()[ImGuiUtils.combo("Render Layer", object.layer.ordinal(), RenderLayer.getAlls())];

            if (object.vec2 != null) {
                float x = ImGuiUtils.sliderFloat("X", object.vec2.x(), -1920, 1920);
                float y = ImGuiUtils.sliderFloat("Y", object.vec2.y(), -1080, 1080);
                vec2 = new Vec2(x, y);
            }
        }

        ImGui.separator();

        final Object old = object.object;
        object.object = switch (object.object) {
            case Preview preview -> PreviewInspector.render(preview);
            case Emoticon emoticon -> EmoticonInspector.render(emoticon);
            case LocationInfo info -> LocationInfoInspector.render(info);
            case Text text -> TextInspector.render(screen.getScenario(), text);
            case AnimatedImage image -> ImageInspector.render(image);
            case Image image -> ImageInspector.render(image);
            case Circle circle -> ShapeInspector.render(circle);
            case Rectangle rectangle -> ShapeInspector.render(rectangle);
            case Sprite sprite -> SpriteInspector.render(sprite);

            case StartDialogueEvent event -> DialogueInspector.render(screen.getScenario(), event);
            case AddDialogueEvent event -> DialogueInspector.render(screen.getScenario(), event);
            case RedirectDialogueEvent event -> DialogueInspector.render(event);

            case ShowOptionsEvent event -> OptionsInspector.render(event);

            case ColorOverlayEvent event -> ColorInspector.render(event);
            case SetColorEvent event -> ColorInspector.render(event);

            case PlayAnimationEvent event -> AnimationInspector.render(event);
            case StopAnimationEvent event -> AnimationInspector.render(event);
            case SpriteAnimationEvent event -> AnimationInspector.render(event);

            case ElementEffectEvent event -> ElementEffectInspector.render(event);

//            case PlaySoundEvent event -> SoundInspector.render(screen.getScenario(), event);
            case SoundAsElement sound -> SoundInspector.render(screen.getScenario(), sound);
            case SoundVolumeEvent event -> SoundInspector.render(event);
//            case StopSoundEvent event -> SoundInspector.render(event);

            case PositionElementEvent event -> PositionInspector.render(event);
            case RotateElementEvent event -> PositionInspector.render(event);

            case SetBackgroundEvent event -> BackgroundInspector.render(event);
            case ClearBackgroundEvent event -> BackgroundInspector.render(event);

            case ShowButtonsEvent event -> new ShowButtonsEvent(ImGuiUtils.checkbox("Show", event.show()));
            default -> old;
        };

        boolean objectEquals = !old.equals(object.object);
        if (objectEquals || requireWait != object.requireWait || object.layer != layer || !Objects.equals(vec2, object.vec2)) {
            object.requireWait = requireWait;
            object.layer = layer;
            object.vec2 = vec2;

            if (objectEquals) {
                long duration = object.object instanceof SoundAsElement sound ? AudioUtils.toDuration(screen.getScenario().name(), sound) : TimeCompiler.compileTime(object.object);
                long oldDuration = old instanceof SoundAsElement sound ? AudioUtils.toDuration(screen.getScenario().name(), sound) : TimeCompiler.compileTime(old);
                if (duration == Long.MAX_VALUE) {
                    duration = 0;
                }
                if (oldDuration == Long.MAX_VALUE) {
                    oldDuration = 0;
                }
                duration += Math.max(0, object.duration - oldDuration);
                object.duration = duration;

                object.track = findNonOccupiedSlot(object.track, object.start, object.duration, object);
            }

            timeline.queueUpdate = true;
        }

        ImGui.end();
    }

    private int findNonOccupiedSlot(int initId, long time, long duration, ObjectOrEvent object1) {
        final Map<Integer, List<ObjectOrEvent>> map = new HashMap<>();
        timeline.getObjects().forEach(object -> map.computeIfAbsent(object.track, n -> new ArrayList<>()).add(object));

        int i = initId;
        List<ObjectOrEvent> track;
        while ((track = map.get(i)) != null) {
            boolean occupied = false;
            for (ObjectOrEvent object : track) {
                final long maxTime = object.start + object.duration, minTime = object.start;
                if (maxTime >= time && minTime <= time + duration && object1 != object) {
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
