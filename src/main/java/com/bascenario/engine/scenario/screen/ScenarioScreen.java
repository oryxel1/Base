package com.bascenario.engine.scenario.screen;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.event.impl.sprite.AddSpriteEvent;
import com.bascenario.engine.scenario.event.render.EventRenderer;
import com.bascenario.engine.scenario.render.DialogueOptionsRender;
import com.bascenario.engine.scenario.render.DialogueRender;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.render.api.Screen;
import com.bascenario.util.render.RenderUtil;
import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.io.File;
import java.util.*;

public class ScenarioScreen extends Screen {
    private static final Background NULL_BACKGROUND = new Background("", false, false);

    @Getter
    private final Scenario scenario;
    private final List<Scenario.Timestamp> copiedAlls = new ArrayList<>();

    private Background background, queueBackground;
    private DynamicAnimation backgroundFadeIn, backgroundFadeOut;

    private long sinceRender, sinceDialogue, sincePoll;

    private final List<EventRenderer> events = new ArrayList<>();
    @Getter
    private final List<SpriteRender> sprites = new ArrayList<>();

    @Getter @Setter
    private int dialogueIndex = 0;
    @Getter
    private DialogueRender dialogue;
    private boolean isDialogueBusy;

    public void setDialogue(DialogueRender dialogue) {
        this.dialogue = dialogue;
        this.isDialogueBusy = this.dialogue != null;
    }

    @Getter
    private DialogueOptionsRender dialogueOptions;
    private boolean isDialogueOptionsBusy;

    public void setDialogueOptions(DialogueOptionsRender dialogueOptions) {
        this.dialogueOptions = dialogueOptions;
        this.isDialogueOptionsBusy = this.dialogueOptions != null;
    }

    @Setter
    private boolean lockClick;

    public ScenarioScreen(Scenario scenario) {
        this.scenario = scenario;
        this.copiedAlls.addAll(this.scenario.getTimestamps());
    }

    @Override
    public void dispose() {
        this.sprites.forEach(SpriteRender::dispose);
    }

    @Override
    public void init() {
        this.sprites.forEach(SpriteRender::init);
    }

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        this.poll();

        if (this.sinceRender == 0) {
            this.sinceRender = System.currentTimeMillis();
        }

        long timeDelta = System.currentTimeMillis() - this.sinceRender;
        this.sincePoll += timeDelta;
        if (!this.isDialogueBusy && !this.isDialogueOptionsBusy) {
            this.sinceDialogue += timeDelta;
        }

        this.sinceRender = System.currentTimeMillis();

        RenderUtil.render(() -> {
            if (this.background != null && this.background != NULL_BACKGROUND) {
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
        });

        this.events.forEach(event -> event.render(this, event.getTime(), positionMatrix, window));
        this.sprites.forEach(SpriteRender::render);
        RenderUtil.render(() -> {
            this.events.forEach(event -> event.render(this, event.getTime(), positionMatrix, window));

            if (this.dialogue != null) {
                this.dialogue.render(positionMatrix, window);
            }

            if (this.dialogueOptions != null) {
                this.dialogueOptions.render(positionMatrix, window);
            }
        });
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.lockClick || ThinGL.windowInterface() == null) {
            return;
        }

        final WindowInterface window = ThinGL.windowInterface();

        if (this.dialogueOptions != null) {
            this.dialogueOptions.mouseClicked(window, mouseX, mouseY, button);
            return;
        }

        if (this.dialogue != null && this.dialogue.isCanSkip() && DialogueRender.hasClickedDialogue(window, mouseX, mouseY) && button == 0) {
            if (this.dialogue.getDialogue().closeOnClick()) {
                this.dialogue = null;
            }

            this.isDialogueBusy = false;
        }
    }

    @Override
    public void mouseRelease() {
        if (this.dialogueOptions != null) {
            this.dialogueOptions.mouseRelease();
        }
    }

    private void poll() {
        if (this.dialogueOptions != null && this.dialogueOptions.isFinished()) {
            this.setDialogueOptions(null);
        }

        if (this.backgroundFadeIn != null && !this.backgroundFadeIn.isRunning()) {
            this.backgroundFadeIn = null;
        }
        if (this.backgroundFadeOut != null && !this.backgroundFadeOut.isRunning() && this.queueBackground != null) {
            this.setBackground(this.queueBackground, true);
            this.queueBackground = null;
            this.backgroundFadeOut = null;
        }

        final Iterator<Scenario.Timestamp> iterator = this.copiedAlls.iterator();

        while (iterator.hasNext()) {
            final Scenario.Timestamp next = iterator.next();
            final long duration = next.waitForDialogue() ? this.sinceDialogue : this.sincePoll;

            if (duration < next.time()) {
                break;
            }

            this.sincePoll = this.sinceDialogue = 0;

            next.events().forEach(event -> {
                event.onStart(this);
                if (event.getDuration() > 0) {
                    this.events.add(new EventRenderer(event));
                }
            });
            iterator.remove();
        }

        this.events.removeIf(event -> {
            if (event.isFinished()) {
                event.onEnd(this);
                return true;
            }

            return false;
        });
    }

    public void setBackground(Background background, boolean skipFadeOut) {
        if (background == null) {
            background = NULL_BACKGROUND;
        }

        if (this.background != null && this.background.fadeOut() && !skipFadeOut) {
            this.queueBackground = background;
            this.backgroundFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, 500L, 255);
            this.backgroundFadeOut.setTarget(0);
            return;
        }

        this.background = background;
        if (!this.background.fadeIn()) {
            return;
        }

        this.backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 500L, 0);
        this.backgroundFadeIn.setTarget(255);
    }
}
