package oxy.bascenario.editor;

import oxy.bascenario.api.event.ColorOverlayEvent;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.event.element.values.RotateElementEvent;
import oxy.bascenario.api.event.sound.PlaySoundEvent;
import oxy.bascenario.api.event.sound.SoundVolumeEvent;
import oxy.bascenario.api.event.sound.StopSoundEvent;
import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.text.TextSegment;

public class TimeCompiler {
    public static long compileTime(final Event<?> object) {
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
            default -> Long.MAX_VALUE;
        };
    }

    public static long compileTime(Dialogue[] dialogues) {
        long duration = 0;
        for (Dialogue dialogue : dialogues) {
            duration += compileTime(dialogue);
        }
        return duration;
    }

    public static long compileTime(Dialogue dialogue) {
        final long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / dialogue.getPlaySpeed()) * 1);

        long duration = 0;
        for (TextSegment segment : dialogue.getDialogue().segments()) {
            duration += msPerWord * segment.text().length();
        }
        return duration;
    }
}
