package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.sprite.Sprite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Builder
public class Scenario {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    /**
     *  The title that going to save and show at the start (preview) of the story.
     */
    private final String name;

    /**
     *  The preview background, shown along with the title, if this is disabled, this will display a gray background instead.
     */
    private final Background previewBackground;

    private final List<Timestamp> timestamps = new ArrayList<>();
    private final List<DialogueOptions> dialogueOptions = new ArrayList<>();
    private final Map<Integer, List<Dialogue>> dialogues = new HashMap<>();
    private final List<Sound> sounds = new ArrayList<>();
    private final List<Sprite> sprites = new ArrayList<>();
    private final List<Background> backgrounds = new ArrayList<>();

    /**
     *  Timestamp, consist of time (in ms since start of scenario) and the events going to get play at time.
     *  The event can still play after this "time" value and each event can have it own duration, depending on the user.
     *  Note: normally the event is related to the sprite, controlling it, ....
     */
    @Builder
    public record Timestamp(long time, List<Event> events) {
    }

    /**
     *  Consist of location name background file location, when to start showing background and when to end.
     *  If this background play from the start value to the end of the scenario, then the end value can be any negative value.
     *  If another background in the list have start time larger than the end time of this one, then this one is overridden by that one.
     *  And there is also fade, which is pretty self-explanatory...
     */
    @Builder
    public record Background(String path, long start, long end, boolean fadeIn, boolean fadeOut) {}

    /**
     *  Sound, consist of path (the location where the audio is stored in OGG format) and the time when the audio starting to play and stop playing.
     *  The user can also customize this to fade in or fade out, depending on the context, situation.
     *  If we want the sound to play during the preview, start is going to be a negative value.
     *  If this sound should be looped, then "end" can be any negative value, if end is zero, then it play till the audio end.
     */
    @Builder
    public record Sound(String path, long start, long end, boolean fadeIn, boolean fadeOut) {}

    /**
     *  Tricky one, I want dialogue option to actually affect the next dialogue that we want to play.
     *  So the best way to do this first we play the option at the timestamp "time" value then we show the option.
     *  Now for each option, it will correspond to an id and that id is tied to which dialogue to play.
     *  Assuming that if only like 2 dialogue after is different, and everything is the same, we can make it so that
     *  the 0 ID is the main dialogue flow and the user can add an "event" to turn dialogue from id 1 -> 0 for example.
     * <p>
     *  NOTE: This should pause EVERYTHING.
     */
    @Builder
    public record DialogueOptions(long time, Map<String, Integer> options) {}

    /**
     *  Dialogue, literally what it's, contain dialogue, timestamp (in ms since start of scenario), the text play speed,
     *  The name that is going to show up, and the role (blue text). The text value can be customized to change the text size or font type (bold/regular),
     *  If we want to use the default text, then set text size to any negative value and fontType to REGULAR.
     *  If the cutscene value is set, both name and role value is ignored and the black gradient background won't show up,
     *  like how it's in the cutscene.
     */
    @Builder
    public record Dialogue(long time, String dialogue, String name, String role, long playSpeed, int textSize, FontType fontType, boolean cutscene) {}
    public enum FontType {
        REGULAR, BOLD
    }

    public JsonObject toJson() {
        return JsonParser.parseString(GSON.toJson(this)).getAsJsonObject();
    }
}
