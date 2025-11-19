package oxy.bascenario.screens;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.elements.image.FadeImage;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.event.RenderEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.EventFunction;
import oxy.bascenario.event.EventMapper;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.DialogueRenderer;
import oxy.bascenario.utils.AnimationUtils;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ScenarioScreen extends ExtendableScreen {
    private final Queue<Timestamp> timestamps = new ConcurrentLinkedQueue<>();
    public ScenarioScreen(Scenario scenario) {
        this.timestamps.addAll(scenario.getTimestamps());
    }

    private Image background, queueBackground;
    private DynamicAnimation backgroundFade = AnimationUtils.dummy(1);

    public void setBackground(Image background) {
        this.queueBackground = null;
        if (this.background != null && this.background instanceof FadeImage fade && Fade.canFade(fade.fadeOut())) {
            this.backgroundFade = AnimationUtils.build(fade.fadeOut().duration(), this.backgroundFade.isRunning() ? this.backgroundFade.getValue() : 1, 0, EasingFunction.LINEAR);
            this.queueBackground = background;
            return;
        }

        this.background = background;
        if (this.background != null && background instanceof FadeImage fade && Fade.canFade(fade.fadeIn())) {
            this.backgroundFade = AnimationUtils.build(fade.fadeIn().duration(), this.backgroundFade.isRunning() ? this.backgroundFade.getValue() : 0, 1, EasingFunction.LINEAR);
        }
    }

    private void renderBackground() {
        if (!this.backgroundFade.isRunning() && this.queueBackground != null) {
            this.background = null;
            setBackground(this.queueBackground);
        }

        if (this.background != null) {
            Color color = Color.WHITE;
            if (this.backgroundFade.isRunning()) {
                color = color.withAlphaF(this.backgroundFade.getValue());
            }

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(this.background.file()), 0, 0, 1920, 1080, color);
        }
    }

    private final List<EventFunction<?>> events = new ArrayList<>();
    private long sinceDialogue, sincePoll, sinceRender;
    private void pollEvents() {
        while (!timestamps.isEmpty()) {
            final Timestamp peek = timestamps.peek();
            if (peek == null) {
                break;
            }

            final long duration = peek.waitForDialogue() ? this.sinceDialogue : this.sincePoll;
            if (duration < peek.time()) {
                break;
            }
            timestamps.poll();

            this.sincePoll = this.sinceDialogue = 0;
            peek.events().forEach(event -> {
                try {
                    final EventFunction<?> function = EventMapper.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
                    function.start(this);
                    if (event.getDuration() > 0) {
                        events.add(function);
                    }
                } catch (Exception ignored) {
                }
            });
        }

        if (this.sinceRender == 0) {
            this.sinceRender = System.currentTimeMillis();
        }

        long timeDelta = System.currentTimeMillis() - this.sinceRender;
        this.sincePoll += timeDelta;
//        if (!this.isDialogueBusy && !this.isDialogueOptionsBusy) {
//            this.sinceDialogue += timeDelta;
//        }
        this.sinceRender = System.currentTimeMillis();

        events.removeIf(event -> {
            if (event.finished()) {
                event.end(this);
                return true;
            }

            return false;
        });
    }

    private final DialogueRenderer dialogueRenderer = new DialogueRenderer();

    @Override
    public void render(float delta) {
        ThinGLUtils.start();
        this.events.stream().filter(event -> !(event.event() instanceof RenderEvent<?>)).forEach(e -> e.render(this));

        pollEvents();
        renderBackground();

        this.events.stream().filter(event -> event.event() instanceof RenderEvent<?> render && render.layer() == RenderLayer.BEHIND_DIALOGUE).forEach(e -> e.render(this));
        this.dialogueRenderer.render(this);
        this.events.stream().filter(event -> event.event() instanceof RenderEvent<?> render && render.layer() == RenderLayer.ABOVE_DIALOGUE).forEach(e -> e.render(this));

        this.events.stream().filter(event -> event.event() instanceof RenderEvent<?> render && render.layer() == RenderLayer.TOP).forEach(e -> e.render(this));
        ThinGLUtils.end();
    }
}
