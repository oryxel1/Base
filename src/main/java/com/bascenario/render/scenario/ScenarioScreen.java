package com.bascenario.render.scenario;

import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.Timestamp;
import com.bascenario.engine.scenario.elements.Background;
import com.bascenario.engine.scenario.elements.PopupImage;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.event.impl.QueueEventEvent;
import com.bascenario.render.scenario.others.EventRenderer;
import com.bascenario.render.scenario.others.DialogueOptionsRender;
import com.bascenario.render.scenario.others.DialogueRender;
import com.bascenario.render.scenario.others.SpriteRender;
import com.bascenario.render.api.Screen;
import com.bascenario.managers.TextureManager;
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
    private final List<Timestamp> copiedAlls = new ArrayList<>();

    private Background background, queueBackground;
    private DynamicAnimation backgroundFadeIn, backgroundFadeOut;

    private long sinceRender, sinceDialogue, sincePoll;

    @Getter
    private final List<EventRenderer> events = new ArrayList<>();
    @Getter
    private final List<SpriteRender> sprites = new ArrayList<>();

    // Dialogue index, determine what dialogue index it's (yes lol).
    // So if for example, I want to make dialogue different depending on the dialogue option I use
    // I can make that there is different dialogue list, each with it own index value, and for example
    // Index 0: "First dialogue", "Third dialogue"
    // Index 1: "Second Dialogue (option 1)"
    // Index 2: "Second Dialogue (option 2)"
    // I can switch back and forth depending on the dialogue option, which is nice.
    @Getter @Setter
    private int dialogueIndex = 0;
    @Getter
    private DialogueRender dialogue;

    // This determined whether we can move on to the next timestamp.
    // I used to do this by setting dialogue to null, but sometimes closing dialogue doesn't look ideal.
    // So now we can let the dialogue tell us if it has already finished playing, and we can move on to the next timestamp.
    private boolean isDialogueBusy;

    private PopupImage popupImage;
    private long sincePopup;
    public void setPopupImage(PopupImage popupImage) {
        this.popupImage = popupImage;
        this.sincePopup = System.currentTimeMillis();
    }

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

    private final boolean debugMode;
    public ScenarioScreen(Scenario scenario, boolean debugMode) {
        this.scenario = scenario;
        this.copiedAlls.addAll(this.scenario.getTimestamps());
        this.debugMode = debugMode;
    }
    public ScenarioScreen(Scenario scenario) {
        this.scenario = scenario;
        this.copiedAlls.addAll(this.scenario.getTimestamps());
        this.debugMode = false;
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

            // For popup image, ignore all value and hardcode it to 850x850 (and depending on the screen size).
            // TODO: This is a fucking retarded way to do it, so don't hardcoded, to lazy for that for now.
            if (this.popupImage != null) {
                float widthHeight = 0.28645833333F * window.getFramebufferWidth();
                ThinGL.renderer2D().texture(positionMatrix, TextureManager.getInstance().getTexture(new File(this.popupImage.path())), window.getFramebufferWidth() / 2F - (widthHeight / 2F), 0.14074074074F * window.getFramebufferHeight(), widthHeight, widthHeight);

                if (System.currentTimeMillis() - this.sincePopup >= this.popupImage.duration()) {
                    this.popupImage = null;
                }
            }

            // Non-functioning button here just for looks, too fucking lazy.
            float extraButtonWidth = (187 / 1920F) * window.getFramebufferWidth(), extraButtonHeight = (70 / 1080F) * window.getFramebufferHeight();
            float posX = window.getFramebufferWidth() - (0.10833333333f * window.getFramebufferWidth());
            final float posY = (26 / 1080F) * window.getFramebufferHeight();
            ThinGL.renderer2D().texture(positionMatrix,
                    TextureManager.getInstance().getTexture("/assets/base/uis/buttons/menu.png"),
                    posX, posY, extraButtonWidth, extraButtonHeight);

            posX -= (191 / 1920F) * window.getFramebufferWidth();
            ThinGL.renderer2D().texture(positionMatrix,
                    TextureManager.getInstance().getTexture("/assets/base/uis/buttons/auto.png"),
                    posX, posY, extraButtonWidth, extraButtonHeight);
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

        if (this.dialogue != null && (this.dialogue.isCanSkip() || this.debugMode) && DialogueRender.hasClickedDialogue(window, mouseX, mouseY) && button == 0) {
            if (this.dialogue.getDialogue().closeOnClick()) {
                this.dialogue = null;
            }

            this.isDialogueBusy = false;
        }

        this.sinceDialogue = this.sincePoll = Long.MAX_VALUE;
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

        final Iterator<Timestamp> iterator = this.copiedAlls.iterator();

        // So here is how the event system, dialogue system works, quite proud of it
        // There will be timestamps, and each timestamps will have an X distance between each other
        // to determine when to play. After this X amount of time then it will play the next timestamp.
        // However, if waitForDialogue is true, then it will wait for the current dialogue (option) to finish
        // before waiting for another X distance to play... Simple enough? And each timestamp will have events
        // that responsible for playing dialogue, adding sprite, play sound, ....
        while (iterator.hasNext()) {
            final Timestamp next = iterator.next();
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

        final List<Event<?>> queuedEvents = new ArrayList<>();

        // Let's see what event has finished playing and remove them.
        final Iterator<EventRenderer> rendererIterator = this.events.iterator();
        while (rendererIterator.hasNext()) {
            final EventRenderer event = rendererIterator.next();
            if (event.isFinished()) {
                event.onEnd(this);
                // If this is a queue event, then we should play the queued event after the duration finish!
                if (event.getEvent() instanceof QueueEventEvent queued) {
                    queuedEvents.add(queued.getQueuedEvent());
                }
                rendererIterator.remove();
            }
        }

        queuedEvents.forEach(queue -> {
            queue.onStart(this);
            this.events.add(new EventRenderer(queue));
        });
    }

    public void setBackground(Background background, boolean skipFadeOut) {
        if (background == null) {
            background = NULL_BACKGROUND;
        }

        // TODO: Again, hardcoding a specific fading value is such a retarded design choice, so change that later i guess.

        if (this.background != null && this.background.fadeOut() && !skipFadeOut) {
            this.queueBackground = background;
            this.backgroundFadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, 1000L, 255);
            this.backgroundFadeOut.setTarget(0);
            return;
        }

        this.background = background;
        if (!this.background.fadeIn()) {
            return;
        }

        this.backgroundFadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 1000L, 0);
        this.backgroundFadeIn.setTarget(255);
    }
}
