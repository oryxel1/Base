package oxy.bascenario.api.render.elements.emoticon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmoticonType {
    NOTE("Note", "SFX_Emoticon_Motion_Twinkle.wav"),
    RESPOND("Respond", "SFX_Emoticon_Motion_Respond.wav"),
    ANGRY("Angry", "SFX_Emoticon_Motion_Angry.wav"),
    SWEAT("Sweat", "SFX_Emoticon_Motion_Sweat.wav"),
    THINKING("Thinking", "SFX_Emoticon_Motion_Dot.wav"),
    HESITATED("Hesitated", "SFX_Emoticon_Motion_Dot.wav"),
    CHAT("Chat", "SFX_Emoticon_Motion_Chat.wav"),
    EXCLAMATION_MARK("Exclamation Mark", "SFX_Emoticon_Motion_Exclaim.wav"),
    HEART("Heart", "SFX_Emoticon_Motion_Heart.wav"),
    SURPRISED("Suprised", "SFX_Emoticon_Motion_Surprise.wav"),
    QUESTION_MARK("Question Mark", "SFX_Emoticon_Motion_Question.wav"),
    SHY("Shy", "SFX_Emoticon_Motion_Shy.wav"),
    ANXIETY("Anxiety", "SFX_Emoticon_Motion_Upset.wav"),
    TWINKLE("Twinkle", "SFX_Emoticon_Motion_Twinkle.wav"),
    IDEA("Idea", "SFX_Emoticon_Motion_Bulb.wav"),
    SIGH("Sigh", "SFX_Emoticon_Motion_Sigh.wav")
    ;

    private final String name, sfx;

    public static String[] getAlls() {
        String[] strings = new String[EmoticonType.values().length];
        for (int i = 0; i < EmoticonType.values().length; i++) {
            strings[i] = EmoticonType.values()[i].name;
        }
        return strings;
    }
}