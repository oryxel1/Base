package oxy.bascenario.editor.screen;

import com.badlogic.gdx.Screen;
import imgui.ImGui;
import imgui.ImVec2;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.editor.timeline.ObjectOrEvent;
import oxy.bascenario.editor.utils.TrackParser;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.TimeUtils;

import java.util.*;

public final class ScenarioEditorScreen extends BaseScenarioEditorScreen {
    private ScenarioScreen screen;

    public ScenarioEditorScreen(Screen screen, Scenario scenario, Scenario.Builder builder) {
        super(screen, builder);
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
    public void render(float delta) {
        boolean busyDialogue = screen.isBusyDialogue() && !screen.getDialogueRenderer().isBusy();
        boolean busyOptions = screen.isBusyOptions() && !(screen.getOptionsRenderer().getScale().isRunning() && screen.getOptionsRenderer().getScale().getTarget() == 1);
        timeline.setTickTime(!busyDialogue && !busyOptions);
        super.render(delta);
    }

    ImVec2 windowSize = new ImVec2(0, 0), windowPos = new ImVec2(0, 0);
    @Override
    public void renderScenario() {
        windowSize = ImGui.getWindowSize();
        windowPos = ImGui.getWindowPos();
        screen.render(0);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!timeline.isPlaying()) {
            return;
        }

        mouseX *= (ThinGL.windowInterface().getFramebufferWidth() / 1920F);
        mouseY *= (ThinGL.windowInterface().getFramebufferHeight() / 1080F);
        mouseX -= windowPos.x;
        mouseY -= windowPos.y + 23;

        mouseX /= (windowSize.x / 1920f);
        mouseY /= ((windowSize.y - 23) / 1080f);

        screen.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseRelease() {
        if (!timeline.isPlaying()) {
            return;
        }

        screen.mouseRelease();
    }

    @Override
    public void update() {
        screen = new ScenarioScreen(screen.getScenario());
        screen.show();
        screen.getTimestamps().clear();
        screen.getTimestamps().addAll(TrackParser.parse(timeline.getObjects()));
        screen.setPlaying(false);

        long lastDuration = 0, last = 0;
        for (ObjectOrEvent object : this.timeline.getObjects()) {
            if (object.start > timeline.getTimestamp()) {
                break;
            }

            long duration = object.duration;
            long distance;
            if (timeline.getTimestamp() > object.start + duration) {
                distance = duration + (timeline.getTimestamp() - (object.start + duration));
            } else {
                distance = duration - ((object.start + duration) - timeline.getTimestamp());
            }

            screen.sinceDialogue = screen.sincePoll = lastDuration + (object.start - last);
            TimeUtils.fakeTimeMillis = System.currentTimeMillis() - distance;
            screen.pollEvents(true);

            lastDuration = duration;
            last = object.start + duration;

            if (object.object instanceof StartDialogueEvent && timeline.getTimestamp() > object.start + duration) {
                screen.setBusyDialogue(false);
            }
        }

        renderScenarioWindow();

        TimeUtils.fakeTimeMillis = timeline.isPlaying() ? null : System.currentTimeMillis();
        screen.setPlaying(timeline.isPlaying());
        screen.sinceDialogue = screen.sincePoll = timeline.getTimestamp() - (last - lastDuration);
    }

    @Override
    public void setPlaying(boolean playing) {
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
