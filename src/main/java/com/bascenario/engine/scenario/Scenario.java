package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@Getter
@Builder
public class Scenario {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    private final String name;

    private final Background previewBackground;

    private final Sound previewSound;

    @Getter
    private final List<Next> alls = new ArrayList<>();

    public void add(Object object) {
        this.add(false, object);
    }

    public void add(boolean waitForDialogue, Object object) {
        this.alls.add(new Next(waitForDialogue, object));
    }

    public record Next(boolean waitForDialogue, Object object) {}

    @Builder
    public record Timestamp(long time, List<Event> events) {
    }

    @Builder
    public record Sprite(String skeleton, String atlas, String defaultAnimation, long start, long end, boolean fadeIn) {
    }

    @Builder
    public record Background(String path, boolean fadeIn, boolean fadeOut) {}

    @Builder
    public record Sound(String path, long time, long fadeOut) {}

    @Builder
    public record DialogueOptions(long time, Map<String, Integer> options) {}

    @Builder
    public record Dialogue(long time, String dialogue, String name, String association, double playSpeed, float textScale, FontType fontType, boolean cutscene) {
        public static long MS_PER_WORD = 38L;

        public long getMaxDuration() {
            long msPerWord = (long) (Dialogue.MS_PER_WORD * (1 / playSpeed()));
            return msPerWord * 10 + (msPerWord * (dialogue.length() - 1)) + (msPerWord * 10);
        }
    }
    public enum FontType {
        REGULAR, BOLD
    }

    public JsonObject toJson() {
        return JsonParser.parseString(GSON.toJson(this)).getAsJsonObject();
    }
}
