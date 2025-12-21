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

    @Override
    public void update() {
        screen = new ScenarioScreen(screen.getScenario());
        screen.getTimestamps().clear();
        screen.getTimestamps().addAll(scenario.timestamps());

        screen.sinceDialogue = timeline.getTimestamp();
        screen.sincePoll = timeline.getTimestamp();

        long maxTime = 0;
        for (Track track : timeline.getTracks().values()) {
            for (Map.Entry<Long, Pair<Track.Cache, Long>> entry : track.getElements().entrySet()) {
                if (entry.getKey() > timeline.getTimestamp()) {
                    break;
                }

                maxTime = Math.max(maxTime, entry.getKey());
            }
        }

        Long oldFreeze = TimeUtils.fakeTimeMillis;
        if (timeline.getTimestamp() > 0) {
            TimeUtils.fakeTimeMillis = TimeUtils.currentTimeMillis() - timeline.getTimestamp() + maxTime;
        }
        screen.pollEvents(true);
        screen.render(0);
        TimeUtils.fakeTimeMillis = timeline.isPlaying() ? null : oldFreeze == null ? System.currentTimeMillis() : oldFreeze;
    }

    @Override
    protected void setPlaying(boolean playing) {
        this.screen.setPlaying(playing);
        if (playing) {
            if (!timeline.isPlaying()) {
                update();
            }
            TimeUtils.fakeTimeMillis = null;
        } else {
            TimeUtils.fakeTimeMillis = System.currentTimeMillis();
        }
        super.setPlaying(playing);
    }
}
