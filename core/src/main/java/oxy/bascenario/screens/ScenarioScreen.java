package oxy.bascenario.screens;

import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.animation.DynamicAnimation;
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
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.ThinGLUtils;

import java.util.*;
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

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(this.background.file()), 0, 0, 1920, 1080, color);
        }
    }

    private long sinceDialogue, sincePoll, sinceRender;

    @Setter
    private boolean busyDialogue, busyOptions;
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
                    final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
                    function.run(this);
                } catch (Exception ignored) {
                }
            });
        }

        if (this.sinceRender == 0) {
            this.sinceRender = System.currentTimeMillis();
        }

        long timeDelta = System.currentTimeMillis() - this.sinceRender;
        this.sincePoll += timeDelta;
        if (!this.busyDialogue && !this.busyOptions) {
            this.sinceDialogue += timeDelta;
        }
        this.sinceRender = System.currentTimeMillis();
    }

    @Getter
    private final Map<Integer, ElementRenderer<?>> elements = new TreeMap<>();
    @Getter
    private final BaseDialogueRenderer dialogueRenderer = new DialogueRenderer();
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
        pollEvents();
        renderBackground();

        this.elements.values().stream().filter(element -> element.getLayer() == RenderLayer.BEHIND_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.dialogueRenderer.render();

        // TODO: Should the above dialogue render layer above the options as well?
        this.elements.values().stream().filter(element -> element.getLayer() == RenderLayer.ABOVE_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.optionsRenderer.render(this);

        this.elements.values().stream().filter(element -> element.getLayer() == RenderLayer.TOP).forEach(ElementRenderer::renderAll);

        ThinGLUtils.end();

        this.elements.values().removeIf(ElementRenderer::selfDestruct);
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
