package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.render.EventRenderer;
import com.bascenario.engine.scenario.render.DialogueRender;
import com.bascenario.render.api.Screen;
import com.bascenario.util.RenderUtil;
import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.io.File;
import java.util.*;

@RequiredArgsConstructor
public class ScenarioScreen extends Screen {
    private final Scenario scenario;
    private long sinceLast = System.currentTimeMillis();
    private long duration = 0;
    // We want to keep track of the current background :P;
    private Scenario.Background background, queueBackground;
    private DynamicAnimation backgroundFadeIn, backgroundFadeOut;

    // private Scenario.DialogueOptions dialogueOptions;
    private int dialogueIndex = -2;
    private long sinceLastDialogue;
    private boolean hasPlayTheFirstDialogue;
    private DialogueRender dialogue;

    private final List<Object> alreadyPlays = new ArrayList<>();
    private final List<EventRenderer> events = new ArrayList<>();

    private WindowInterface windowInterface;
    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        this.windowInterface = window;

        if (this.dialogueIndex == -2) {
            this.dialogueIndex = scenario.getDialogues().isEmpty() ? -1 : (int) scenario.getDialogues().keySet().toArray()[0];
        }

        long deltaTime = System.currentTimeMillis() - this.sinceLast; // this.dialogueOptions == null ? System.currentTimeMillis() - this.sinceLast : 0;
        this.sinceLast = System.currentTimeMillis();
        this.duration += deltaTime;
        this.sinceLastDialogue += deltaTime;

        this.pollEvents();
        this.pollDialogue();
        this.pollBackground();

        if (this.background != null) {
            Color color;
            if (this.backgroundFadeOut != null) {
                color = Color.fromRGBA(255, 255, 255, Math.round(this.backgroundFadeOut.getValue()));
            } else if (this.backgroundFadeIn != null) {
                color = Color.fromRGBA(255, 255, 255, Math.round(this.backgroundFadeIn.getValue()));
            } else {
                color = Color.WHITE;
            }

            RenderUtil.renderBackground(positionMatrix, window.getFramebufferWidth(), window.getFramebufferHeight(), new File(this.background.path()), color);
        }

        this.events.forEach(event -> event.render(event.getTime(), positionMatrix, window));

        if (this.dialogue != null) {
            this.dialogue.render(positionMatrix, window);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.windowInterface == null) {
            return;
        }

        if (DialogueRender.hasClickedDialogue(this.windowInterface, mouseX, mouseY) && button == 0) {
            this.dialogue = null;
            this.sinceLastDialogue = 0;
        }
    }

    private void pollDialogue() {
        if (this.dialogue != null) {
            if (this.dialogue.isFinished()) {
                this.sinceLastDialogue = 0;
            }
            return;
        }

        if (this.dialogueIndex < 0) {
            return;
        }

        final List<Scenario.Dialogue> dialogues = scenario.getDialogues().get(this.dialogueIndex);
        for (int i = 0; i < dialogues.size(); i++) {
            final Scenario.Dialogue dialogue = dialogues.get(i);
            if (!this.hasPlayTheFirstDialogue && i > 0) {
                break;
            }

            if (dialogue.time() > this.sinceLastDialogue || this.alreadyPlays.contains(dialogue)) {
                continue;
            }

            if (i == 0) {
                this.hasPlayTheFirstDialogue = true;
            }
            this.alreadyPlays.add(dialogue);
            this.dialogue = new DialogueRender(dialogue);
            break;
        }
    }

    private void pollEvents() {
        this.events.removeIf(event -> {
            if (event.isFinished()) {
                event.onEnd();
                return true;
            }

            return false;
        });

        for (Scenario.Timestamp timestamp : scenario.getTimestamps()) {
            if (timestamp.time() > duration() || this.alreadyPlays.contains(timestamp)) {
                continue;
            }

            this.alreadyPlays.add(timestamp);
            timestamp.events().forEach(event -> {
                this.events.add(new EventRenderer(event));
                event.onStart();
            });
        }
    }

    private void pollBackground() {
        if (this.backgroundFadeIn != null && !this.backgroundFadeIn.isRunning()) {
            this.backgroundFadeIn = null;
        }

        if (this.backgroundFadeOut != null && !this.backgroundFadeOut.isRunning() && this.queueBackground != null) {
            updateBackground(this.queueBackground);
            this.queueBackground = null;
            this.backgroundFadeOut = null;
        }

        Scenario.Background selected = this.background;
        for (Scenario.Background background : scenario.getBackgrounds()) {
            if (background.start() > duration() || background == this.background) {
                continue;
            }

            selected = background;
        }

        if (this.background != null && this.background.end() > 0 && duration() > this.background.end()) {
            selected = null;
        }

        if (this.background == selected) {
            return;
        }

        if (this.background != null && this.background.fadeOut()) {
            this.queueBackground = selected;
            this.backgroundFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, 500L, 255);
            this.backgroundFadeOut.setTarget(0);
            return;
        }

        updateBackground(selected);
    }

    private void updateBackground(Scenario.Background background) {
        this.background = background;
        if (!this.background.fadeIn()) {
            return;
        }

        this.backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 500L, 0);
        this.backgroundFadeIn.setTarget(255);
    }

    private long duration() {
        return this.duration;
    }
}
