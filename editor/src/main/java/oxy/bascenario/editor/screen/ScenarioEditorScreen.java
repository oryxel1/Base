package oxy.bascenario.editor.screen;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.editor.element.Track;
import oxy.bascenario.editor.utils.TrackParser;
import oxy.bascenario.event.EventRegistries;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Pair;
import oxy.bascenario.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ScenarioEditorScreen extends BaseScenarioEditorScreen {
    private ScenarioScreen screen;

    public ScenarioEditorScreen(Scenario scenario, Scenario.Builder builder) {
        super(builder);
        this.screen = new ScenarioScreen(scenario);
        this.screen.setPlaying(false);
    }

    @Override
    public void show() {
        super.show();
        this.screen.show();
    }

    @Override
    public void renderScenario() {
        screen.render(0);
    }

    private void update() {
        screen = new ScenarioScreen(screen.getScenario());
        screen.getTimestamps().clear();
        screen.getTimestamps().addAll(scenario.timestamps());
        System.out.println(screen.getTimestamps().size());
//
//        TimeUtils.fakeTimeMillis = 0L;
//        TrackParser.parse(timeline.getTracks()).forEach(timestamp -> {
//            for (Event<?> event : timestamp.events()) {
//                try {
//                    final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
//                    function.run(screen);
//                } catch (Exception ignored) {
//                }
//            }
//        });
//        TimeUtils.fakeTimeMillis = null;

//        final Map<Integer, Track> playedParts = TrackParser.parse(timeline, scenario.build());

//
//        long minTrackTimestamp = 0, maxTrackTimestamp = 0;
//        for (Track track : playedParts.values()) {
//            for (Map.Entry<Long, Pair<Track.Cache, Long>> entry : track.getElements().entrySet()) {
//                minTrackTimestamp = Math.max(minTrackTimestamp, entry.getKey());
//                maxTrackTimestamp = Math.max(maxTrackTimestamp, entry.getKey() + entry.getValue().right());
//            }
//        }
//
//        TrackParser.parse(playedParts).forEach(timestamp -> timestamp.events().forEach(event -> {
//            try {
//                final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
//                function.run(screen);
//            } catch (Exception ignored) {
//            }
//        }));
//
//        final Map<Integer, Track> needPlay = TrackParser.parse(timeline, scenario.build(), timeline.getTimestamp(), true);
//        screen.getTimestamps().addAll(TrackParser.parse(needPlay));

//        if (timeline.getTimestamp() <= maxTrackTimestamp) {
//            timeline.setTimestamp(minTrackTimestamp);
//        }
//        if (distance > 0) {
//            long since = distance / 60L;
//
//            while (since > 0) {
//                screen.render(0);
//                System.out.println(since);
//                since--;
//            }
//        }
//
//        TimeUtils.fakeTimeMillis = null;
    }

    @Override
    protected void setPlaying(boolean playing) {
        super.setPlaying(playing);
        this.screen.setPlaying(playing);
        if (playing) {
            TimeUtils.fakeTimeMillis = null;
            update();
        } else {
            TimeUtils.fakeTimeMillis = System.currentTimeMillis();
        }
    }
}
