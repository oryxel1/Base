package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.render.EventRenderer;
import com.bascenario.engine.scenario.render.DialogueOptionsRender;
import com.bascenario.engine.scenario.render.DialogueRender;
import com.bascenario.render.api.Screen;
import com.bascenario.util.RenderUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
    @Getter
    private final Scenario scenario;
    private long sinceLast = System.currentTimeMillis();
    @Getter
    private long duration = 0, realDuration;
    // We want to keep track of the current background :P;
    private Scenario.Background background, queueBackground;
    private DynamicAnimation backgroundFadeIn, backgroundFadeOut;

    // private Scenario.DialogueOptions dialogueOptions;
    @Setter
    private int dialogueIndex = -2;
    private DialogueRender dialogue, lastDialogue;
    private DialogueOptionsRender dialogueOptions, lastDialogueOptions;

    private final Set<Object> alreadyPlays = new HashSet<>();
    private final Set<Object> donePlayings = new HashSet<>();
    private final List<EventRenderer> events = new ArrayList<>();

    private boolean canProceedWithDialogue;
    @Setter
    private boolean lockClick;

    private WindowInterface windowInterface;
    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        this.windowInterface = window;

        if (this.dialogueIndex == -2) {
            this.dialogueIndex = scenario.getDialogues().isEmpty() ? -1 : (int) scenario.getDialogues().keySet().toArray()[0];
        }

        long deltaTime = (this.dialogue != null || this.dialogueOptions != null) ? 0 : System.currentTimeMillis() - this.sinceLast;
        if (this.dialogue != null && this.lastDialogue != this.dialogue) {
            this.lastDialogue = this.dialogue;
            deltaTime += 1; // Y E P.
        }
        if (this.dialogueOptions != null && this.lastDialogueOptions != this.dialogueOptions) {
            this.lastDialogueOptions = this.dialogueOptions;
            deltaTime += 1; // Y E P.
        }

        this.duration += deltaTime;
        this.realDuration += (System.currentTimeMillis() - this.sinceLast);
        this.sinceLast = System.currentTimeMillis();

        System.out.println("Render!-----------------------");
        this.pollBackground();
        this.pollDialogueAndDialogueOptions();
        this.pollEvents();

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

        this.events.forEach(event -> event.render(this, event.getTime(), positionMatrix, window));

        if (this.dialogue != null) {
            this.dialogue.render(positionMatrix, window);
        }

        if (this.dialogueOptions != null) {
            this.dialogueOptions.render(positionMatrix, window);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.windowInterface == null) {
            return;
        }

        // Event use this.
        if (this.lockClick) {
            return;
        }

        if (this.dialogueOptions != null) {
            this.dialogueOptions.mouseClicked(this.windowInterface, mouseX, mouseY, button);
        }

        if (this.dialogueOptions == null && DialogueRender.hasClickedDialogue(this.windowInterface, mouseX, mouseY) && button == 0) {
            this.canProceedWithDialogue = true;
        }
    }

    @Override
    public void mouseRelease() {
        if (this.dialogueOptions != null) {
            this.dialogueOptions.mouseRelease();
        }
    }

    private void pollDialogueAndDialogueOptions() {
        if (this.dialogueOptions != null && this.dialogueOptions.isFinished()) {
            this.donePlayings.add(this.dialogueOptions.getDialogueOptions());
            setDialogueOptions(null);
            this.canProceedWithDialogue = true;
        }

        Scenario.DialogueOptions newDialogueOptions = null;
        Scenario.Dialogue newDialogue = null;

        boolean free = (this.dialogue == null || this.dialogueOptions == null) && this.dialogueIndex >= 0;
        if (!free) {
            if (this.canProceedWithDialogue) {
                setDialogue(null);
                this.canProceedWithDialogue = false;
            }
            return;
        }

        if (this.dialogueOptions == null && (this.dialogue == null || this.canProceedWithDialogue)) {
            final List<Scenario.Dialogue> dialogues = scenario.getDialogues().get(this.dialogueIndex);
            for (final Scenario.Dialogue dialogue : dialogues) {
                if (dialogue.time() > this.duration || this.alreadyPlays.contains(dialogue)) {
                    continue;
                }

                newDialogue = dialogue;
                break;
            }
        }

        if (this.dialogue == null || this.canProceedWithDialogue) {
            for (final Scenario.DialogueOptions dialogue : scenario.getDialogueOptions()) {
                if (dialogue.time() > this.duration || this.alreadyPlays.contains(dialogue)) {
                    continue;
                }

                newDialogueOptions = dialogue;
                break;
            }
        }

        if (newDialogue != null) {
            boolean update = true;
            if (newDialogueOptions != null) {
                System.out.println(newDialogueOptions + "," + newDialogue);
                update = newDialogueOptions.time() >= newDialogue.time();
            }

            System.out.println(newDialogue);
            if (update) {
                setDialogue(new DialogueRender(newDialogue));
                this.alreadyPlays.add(newDialogue);
                this.canProceedWithDialogue = false;
            }
        }

        if (newDialogueOptions != null && (this.dialogue == null || this.canProceedWithDialogue)) {
            boolean update = true;
            if (newDialogue != null) {
                update = newDialogue.time() >= newDialogueOptions.time();
            }

            System.out.println(newDialogueOptions);
            if (update) {
                if (this.dialogueOptions != null) {
                    this.donePlayings.add(this.dialogueOptions.getDialogueOptions());
                }
                setDialogueOptions(new DialogueOptionsRender(this, newDialogueOptions));
                this.alreadyPlays.add(newDialogueOptions);
                this.canProceedWithDialogue = false;
            }
        }

        if (this.dialogueOptions == null && this.canProceedWithDialogue) {
            setDialogue(null);
            System.out.println("Set null here!");
            this.canProceedWithDialogue = false;
        }
    }

    private void pollEvents() {
        this.events.removeIf(event -> {
            if (event.isFinished()) {
                event.onEnd(this);
                return true;
            }

            return false;
        });

        for (Scenario.Timestamp timestamp : scenario.getTimestamps()) {
            if (timestamp.time() > this.duration || this.alreadyPlays.contains(timestamp)) {
                continue;
            }
            if (!this.canPickupTimestamp(timestamp.time())) {
                continue;
            }

            System.out.println("Play: " + timestamp);

            this.alreadyPlays.add(timestamp);
            timestamp.events().forEach(event -> {
                this.events.add(new EventRenderer(event));
                event.onStart(this);
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
            if (background.start() > this.duration || background == this.background) {
                continue;
            }

            selected = background;
        }

        if (this.background != null && this.background.end() > 0 && this.duration > this.background.end()) {
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

    private void setDialogue(DialogueRender dialogue) {
        if (this.dialogue != null) {
            this.donePlayings.add(this.dialogue.getDialogue());
            System.out.println("Add: " + this.dialogue.getDialogue());
        }
        System.out.println("Set dialogue: " + (this.dialogue == null ? null : this.dialogue.getDialogue()));
        this.dialogue = dialogue;
    }

    private void setDialogueOptions(DialogueOptionsRender dialogueOptions) {
        if (this.dialogueOptions != null) {
            this.donePlayings.add(this.dialogueOptions.getDialogueOptions());
            System.out.println("Add: " + this.dialogueOptions.getDialogueOptions());
        }
        this.dialogueOptions = dialogueOptions;
    }

    private boolean canPickupTimestamp(long time) {
        for (final Scenario.DialogueOptions dialogue : scenario.getDialogueOptions()) {
            if (this.donePlayings.contains(dialogue)) {
                continue;
            }
            if (dialogue.time() > time) {
                break;
            }

            // System.out.println("False: " + dialogue);
            return false;
        }

        final List<Scenario.Dialogue> dialogues = scenario.getDialogues().get(this.dialogueIndex);
        for (final Scenario.Dialogue dialogue : dialogues) {
            if (this.donePlayings.contains(dialogue)) {
                continue;
            }
            if (dialogue.time() > time) {
                break;
            }

            // System.out.println("False: " + dialogue);
            return false;
        }

        return true;
    }
}
