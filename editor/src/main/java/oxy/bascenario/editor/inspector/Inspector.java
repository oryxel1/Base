package oxy.bascenario.editor.inspector;

import imgui.ImColor;
import imgui.ImGui;
import lombok.RequiredArgsConstructor;
import oxy.bascenario.api.event.color.ColorOverlayEvent;
import oxy.bascenario.api.event.animation.PlayAnimationEvent;
import oxy.bascenario.api.event.animation.SpriteAnimationEvent;
import oxy.bascenario.api.event.animation.StopAnimationEvent;
import oxy.bascenario.api.event.color.SetColorEvent;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
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
import oxy.bascenario.editor.utils.TimeCompiler;
import oxy.bascenario.editor.inspector.impl.events.*;
import oxy.bascenario.editor.element.Timeline;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.editor.inspector.impl.objects.*;
import oxy.bascenario.editor.screen.BaseScenarioEditorScreen;
import oxy.bascenario.editor.utils.AudioUtils;
import oxy.bascenario.editor.utils.SoundAsElement;
import oxy.bascenario.utils.ImGuiUtils;
import oxy.bascenario.utils.Pair;

@RequiredArgsConstructor
public class Inspector {
    private final BaseScenarioEditorScreen screen;
    private final Timeline timeline;

    @SuppressWarnings("ALL")
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
        RenderLayer layer = null;
        if (pair.left().layer() != null) {
            layer = RenderLayer.values()[ImGuiUtils.combo("Render Layer", pair.left().layer().ordinal(), RenderLayer.getAlls())];
        }

        final Object old = pair.left().object();
        pair.left().object(switch (pair.left().object()) {
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
            default -> old;
        });

        if (!old.equals(pair.left().object()) || requireWait != pair.left().requireWait() || pair.left().layer() != layer) {
            pair.left().requireWait(requireWait);
            pair.left().layer(layer);

            long duration = pair.left().object() instanceof SoundAsElement sound ? AudioUtils.toDuration(screen.getScenario().name(), sound) : TimeCompiler.compileTime(pair.left().object());
            long oldDuration = old instanceof SoundAsElement sound ? AudioUtils.toDuration(screen.getScenario().name(), sound) : TimeCompiler.compileTime(old);
            if (duration == Long.MAX_VALUE) {
                duration = 0;
            }
            if (oldDuration == Long.MAX_VALUE) {
                oldDuration = 0;
            }
            duration += Math.max(0, pair.right() - oldDuration);
            pair.right(duration);

            final Track track = findNonOccupiedSlot(renderer.getTrack().getIndex(), renderer.getStartTime(), pair.right(), renderer.getTrack().getOccupies().get(renderer.getStartTime()));
            if (track == renderer.getTrack()) {
                Pair<Long, Long> occupy = renderer.getTrack().getOccupies().get(renderer.getStartTime());
                if (occupy != null) {
                    occupy.right(occupy.left() + duration);
                }
            } else {
                renderer.getTrack().remove(renderer.getStartTime());
                track.put(renderer.getStartTime(), pair);

                timeline.setSelectedElement(track.getRenderers().get(renderer.getStartTime()));
            }

            timeline.updateScenario(true);
        } else {
            pair.left().requireWait(requireWait);
            pair.left().layer(layer);
        }

        ImGui.end();
    }

    private Track findNonOccupiedSlot(int initId, long time, long duration, Pair<Long, Long> ignore) {
        int i = initId;
        Track track;
        while ((track = timeline.getTrack(i)) != null) {
            if (!track.isOccupied(time, duration, ignore)) {
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
