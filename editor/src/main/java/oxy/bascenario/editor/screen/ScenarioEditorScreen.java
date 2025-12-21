package oxy.bascenario.editor.screen;

import com.badlogic.gdx.utils.ScreenUtils;
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

    @Override
    public void update() {
        screen = new ScenarioScreen(screen.getScenario());
        screen.getTimestamps().clear();
        screen.getTimestamps().addAll(scenario.timestamps());
        screen.setPlaying(false);

        long totalTime = 0, last = 0;
        for (Track track : timeline.getTracks().values()) {
            for (Map.Entry<Long, Pair<Track.Cache, Long>> entry : track.getElements().entrySet()) {
                if (entry.getKey() > timeline.getTimestamp()) {
                    break;
                }
                long duration = entry.getValue().right();
                long distance;
                if (timeline.getTimestamp() > entry.getKey() + duration) {
                    distance = duration + (timeline.getTimestamp() - (entry.getKey() + duration));
                } else {
                    distance = duration - ((entry.getKey() + duration) - timeline.getTimestamp());
                }

                screen.sinceDialogue = screen.sincePoll = totalTime + (entry.getKey() - last);
                TimeUtils.fakeTimeMillis = System.currentTimeMillis() - distance;
                screen.pollEvents(true);
                screen.render(0);
                ScreenUtils.clear(0, 0, 0, 1, true);

                totalTime = duration;
                last = entry.getKey() + duration;
            }
        }

        screen.render(0);

        TimeUtils.fakeTimeMillis = timeline.isPlaying() ? null : System.currentTimeMillis();
        screen.setPlaying(timeline.isPlaying());
    }

    @Override
    protected void setPlaying(boolean playing) {
        if (playing) {
            if (!timeline.isPlaying()) {
                update();
            }
            TimeUtils.fakeTimeMillis = null;
        } else {
            TimeUtils.fakeTimeMillis = System.currentTimeMillis();
        }
        super.setPlaying(playing);
        this.screen.setPlaying(playing);
    }
}
