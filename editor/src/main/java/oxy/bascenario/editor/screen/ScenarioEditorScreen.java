package oxy.bascenario.editor.screen;

import com.badlogic.gdx.utils.ScreenUtils;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.TimeUtils;

import java.util.*;

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
    public void resize(int width, int height) {
        super.resize(width, height);
        screen.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        screen.dispose();
    }

    @Override
    public void renderScenario() {
        screen.render(0);
    }

    @Override
    public void update() {
//        screen = new ScenarioScreen(screen.getScenario());
//        screen.show();
//        screen.getTimestamps().clear();
//        screen.getTimestamps().addAll(scenario.timestamps());
//        screen.setPlaying(false);
//
//        final List<Map.Entry<Long, Track.ObjectOrEvent>> sorted = new ArrayList<>();
//        for (Track track : timeline.getTracks().values()) {
//            for (Map.Entry<Long, Track.ObjectOrEvent> entry : track.getObjects().entrySet()) {
//                if (entry.getKey() > timeline.getTimestamp()) {
//                    break;
//                }
//
//                sorted.add(entry);
//            }
//        }
//        sorted.sort(Comparator.comparingLong(Map.Entry::getKey));
//
//        long lastDuration = 0, last = 0;
//        for (Map.Entry<Long, Track.ObjectOrEvent> entry : sorted) {
//            long duration = entry.getValue().duration;
//            long distance;
//            if (timeline.getTimestamp() > entry.getKey() + duration) {
//                distance = duration + (timeline.getTimestamp() - (entry.getKey() + duration));
//            } else {
//                distance = duration - ((entry.getKey() + duration) - timeline.getTimestamp());
//            }
//
//            screen.sinceDialogue = screen.sincePoll = lastDuration + (entry.getKey() - last);
//            TimeUtils.fakeTimeMillis = System.currentTimeMillis() - distance;
//            screen.pollEvents(true);
//            renderScenarioWindow();
//            ScreenUtils.clear(0, 0, 0, 0, true);
//
//            lastDuration = duration;
//            last = entry.getKey() + duration;
//        }
//
//        renderScenarioWindow();
//
//        TimeUtils.fakeTimeMillis = timeline.isPlaying() ? null : System.currentTimeMillis();
//        screen.setPlaying(timeline.isPlaying());
//        screen.sinceDialogue = screen.sincePoll = timeline.getTimestamp() - (last - lastDuration);
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
