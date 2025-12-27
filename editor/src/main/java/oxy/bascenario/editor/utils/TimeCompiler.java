package oxy.bascenario.editor.utils;

import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.AddDialogueEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.text.TextSegment;

public class TimeCompiler {
    public static Object addTime(final Object object, int duration) {
        if (compileTime(object) == Long.MAX_VALUE) {
            return object;
        }

        return switch (object) {
            case LocationInfo info -> {
                LocationInfo.Builder builder = info.toBuilder();
                builder.duration(info.duration() + duration);
                yield builder.build();
            }
            case Emoticon emoticon -> {
                Emoticon.Builder builder = emoticon.toBuilder();
                builder.duration(emoticon.duration() + duration);
                yield builder.build();
            }
            case PositionElementEvent event -> {
                PositionElementEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            case RotateElementEvent event -> {
                RotateElementEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            case PlaySoundEvent event -> {
                PlaySoundEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            case SoundVolumeEvent event -> {
                SoundVolumeEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            case StopSoundEvent event -> {
                StopSoundEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            case ColorOverlayEvent event -> {
                ColorOverlayEvent.Builder builder = event.toBuilder();
                builder.duration(event.getDuration() + duration);
                yield builder.build();
            }
            default -> object;
        };
    }

    public static boolean canResize(final Object object) {
        return compileTime(object) == Long.MAX_VALUE || object instanceof LocationInfo || object instanceof Emoticon ||
                object instanceof PositionElementEvent || object instanceof RotateElementEvent ||
                object instanceof PlaySoundEvent || object instanceof SoundVolumeEvent || object instanceof StopSoundEvent ||
                object instanceof ColorOverlayEvent;
    }

    public static long compileTime(final Object object) {
        return switch (object) {
            case Preview ignored -> 3900;
            case LocationInfo info -> info.duration() + info.fade();
            case Emoticon emoticon -> switch (emoticon.type()) {
                case NOTE -> 2600 + emoticon.duration();
                case RESPOND, ANGRY, SWEAT, CHAT, EXCLAMATION_MARK, HEART, SHY, SURPRISED, QUESTION_MARK, ANXIETY -> 800 + emoticon.duration();
                case TWINKLE -> emoticon.duration();
                case THINKING, HESITATED -> 600L * 3 + (600L / 3);
            };

            case StartDialogueEvent event -> compileTime(event.getDialogues());
            case AddDialogueEvent event -> compileTime(event.getDialogues());

            case Dialogue[] dialogues -> compileTime(dialogues);
            case Dialogue dialogue -> compileTime(dialogue);
            case Event<?> event -> compileTime(event);
            default -> Long.MAX_VALUE;
        };
    }

    private static long compileTime(final Event<?> object) {
        return switch (object) {
            case PositionElementEvent event -> Math.max(100, event.getDuration());
            case RotateElementEvent event -> Math.max(100, event.getDuration());
            case ShowOptionsEvent ignored -> 500L; // eh.
            case PlaySoundEvent event -> Math.max(100, event.getDuration());
            case SoundVolumeEvent event -> Math.max(100, event.getDuration());
            case StopSoundEvent event -> Math.max(100, event.getDuration());
            case ColorOverlayEvent event -> Math.max(100, event.getDuration());

            default -> 100L; // has to be something for it to show up in the editor.
        };
    }

    private static long compileTime(Dialogue[] dialogues) {
        long duration = 0;
        for (Dialogue dialogue : dialogues) {
            duration += compileTime(dialogue);
        }
        return duration;
    }

    private static long compileTime(Dialogue dialogue) {
        final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / dialogue.getPlaySpeed()) * 1);

        long duration = 0;
        for (TextSegment segment : dialogue.getDialogue().segments()) {
            duration += msPerWord * segment.text().replace("\n", "").length();
        }
        return duration;
    }
}
