package oxy.bascenario.screens;

import lombok.Getter;
import lombok.Setter;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.render.elements.image.FadeImage;
import oxy.bascenario.api.render.elements.image.Image;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.event.EventRegistries;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.ColorOverlayRenderer;
import oxy.bascenario.screens.renderer.dialogue.DialogueRenderer;
import oxy.bascenario.screens.renderer.dialogue.OptionsRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.screens.renderer.dialogue.BaseDialogueRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ScenarioScreen extends ExtendableScreen {
    @Getter
    private final Scenario scenario;

    @Getter
    private final Queue<Timestamp> timestamps = new ConcurrentLinkedQueue<>();
    @Setter
    private boolean playing = true;

    public ScenarioScreen(Scenario scenario) {
        this.timestamps.addAll(scenario.getTimestamps());
        this.scenario = scenario;
        this.dialogueRenderer = new DialogueRenderer(this.scenario);
    }

    private Image background, queueBackground;
    private DynamicAnimation backgroundFade = AnimationUtils.dummy(1);

    public void setBackground(Image background) {
        this.queueBackground = null;
        if (this.background != null && this.background instanceof FadeImage fade && fade.fadeOut() > 0) {
            this.backgroundFade = AnimationUtils.build(fade.fadeOut(), this.backgroundFade.isRunning() ? this.backgroundFade.getValue() : 1, 0, EasingFunction.LINEAR);
            this.queueBackground = background;
            return;
        }

        this.background = background;
        if (this.background != null && background instanceof FadeImage fade && fade.fadeOut() > 0) {
            this.backgroundFade = AnimationUtils.build(fade.fadeIn(), this.backgroundFade.isRunning() ? this.backgroundFade.getValue() : 0, 1, EasingFunction.LINEAR);
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

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(scenario, this.background.file()), 0, 0, 1920, 1080, color);
        }
    }

    public long sinceDialogue, sincePoll;
    private long sinceRender;

    @Setter @Getter
    private boolean busyDialogue, busyOptions;
    public void pollEvents(boolean bypass) {
        if (!playing && !bypass) {
            this.sinceRender = TimeUtils.currentTimeMillis();
            this.sinceDialogue = this.sincePoll = 0;
            return;
        }

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

            peek.events().forEach(event -> {
                try {
                    final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
                    function.run(this);
                } catch (Exception ignored) {
                }
            });

            this.sincePoll -= peek.time();
            this.sinceDialogue -= peek.time();

            if ((this.busyDialogue || this.busyOptions) && !bypass) {
                this.sinceDialogue = 0;
            }
        }

        if (bypass) {
            return;
        }

        if (this.sinceRender == 0) {
            this.sinceRender = TimeUtils.currentTimeMillis();
        }

        long timeDelta = TimeUtils.currentTimeMillis() - this.sinceRender;
        this.sincePoll += timeDelta;
        if (!this.busyDialogue && !this.busyOptions) {
            this.sinceDialogue += timeDelta;
        }
        this.sinceRender = TimeUtils.currentTimeMillis();
    }

    @Getter
    private final TreeMap<Integer, ElementRenderer<?>> elements = new TreeMap<>();
    @Getter
    private final BaseDialogueRenderer dialogueRenderer;
    @Getter
    private final OptionsRenderer optionsRenderer = new OptionsRenderer();

    @Override
    public void show() {
        int start = Integer.MIN_VALUE;
        for (RenderLayer layer : RenderLayer.values()) {
            this.elements.put(start++, new ColorOverlayRenderer(layer));
        }

        this.dialogueRenderer.create();
    }

    @Override
    public void render(float delta) {
        ThinGLUtils.start();
        pollEvents(false);
        renderBackground();

        final Collection<ElementRenderer<?>> elements = this.elements.values();
        elements.stream().filter(element -> element.getLayer() == RenderLayer.BEHIND_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.dialogueRenderer.render();

        // TODO: Should the above dialogue render layer above the options as well?
        elements.stream().filter(element -> element.getLayer() == RenderLayer.ABOVE_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.optionsRenderer.render(this);

        elements.stream().filter(element -> element.getLayer() == RenderLayer.TOP).forEach(ElementRenderer::renderAll);

        ThinGLUtils.end();

        elements.removeIf(ElementRenderer::selfDestruct);
        elements.forEach(element -> element.getSubElements().values().removeIf(ElementRenderer::selfDestruct));
    }

    @Setter
    private boolean lockClick;
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.lockClick) {
            return;
        }

        if (this.optionsRenderer.isBusy()) {
            this.optionsRenderer.mouseClicked(this, mouseX, mouseY);
            return;
        }

        if (!this.dialogueRenderer.isBusy() && this.dialogueRenderer.hasClickedDialogue(mouseX, mouseY) && button == 0) {
            this.busyDialogue = false;
        }
    }

    @Override
    public void mouseRelease() {
        if (this.optionsRenderer.isBusy()) {
            this.optionsRenderer.mouseRelease();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.elements.values().forEach(render -> render.resize(width, height));
    }

    @Override
    public void dispose() {
        this.elements.values().forEach(ElementRenderer::dispose);
    }
}
