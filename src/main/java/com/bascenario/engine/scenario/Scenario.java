package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.event.api.Event;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Builder
public class Scenario {
    /**
     *  The title that going to save and show at the start (preview) of the story.
     */
    private final String name;

    /**
     *  The preview background, shown along with the title, if this is disabled, this will display a gray background instead.
     */
    private final Background previewBackground;

    private final List<Timestamp> timestamps = new ArrayList<>();
    private final List<Dialogue> dialogues = new ArrayList<>();
    private final List<Background> backgrounds = new ArrayList<>();

    /**
     *  Timestamp, consist of time (in ms since start of scenario) and the events going to get play at time.
     *  The event can still play after this "time" value and each event can have it own duration, depending on the user.
     */
    @Builder
    public record Timestamp(long time, List<Event> events) {
    }

    /**
     *  Consist of location name background file location, when to start showing background and when to end.
     *  If this background play from the start value to the end of the scenario, then the end value can be any negative value.
     *  Also, if this a preview background then the location name value is ignored, if location name value is empty/null then nothing happened.
     *  If this was set to a certain value however, this will pop up on the side showing the "location" corresponding to the background dependent on the user
     *  can be anything.
     */
    @Builder
    public record Background(String locationName, String path, long start, long end) {}

    /**
     *  Dialogue, literally what it's, contain dialogue, timestamp (in ms since start of scenario), the text play speed,
     *  The name that is going to show up, and the role (blue text). The text value can be customized to change the text size or font type (bold/regular),
     *  If we want to use the default text, then set text size to any negative value and fontType to null.
     *  If the cutscene value is set, both name and role value is ignored and the black gradient background won't show up,
     *  like how it's in the cutscene.
     */
    @Builder
    public record Dialogue(long time, String dialogue, String name, String role, long playSpeed, int textSize, FontType fontType, boolean cutscene) {}
    public enum FontType {
        REGULAR, BOLD
    }
}
