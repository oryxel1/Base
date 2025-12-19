package oxy.bascenario.editor;

import oxy.bascenario.api.render.elements.*;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.text.TextSegment;

public class TimeCompiler {
    public static long timeFromElement(final Object object) {
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
