package oxy.bascenario.screens;

import lombok.Getter;
import lombok.Setter;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.Timestamp;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.event.EventRegistries;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.renderer.element.ColorOverlayRenderer;
import oxy.bascenario.screens.renderer.dialogue.DialogueRenderer;
import oxy.bascenario.screens.renderer.dialogue.OptionsRenderer;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.screens.renderer.dialogue.BaseDialogueRenderer;
import oxy.bascenario.utils.animation.DynamicAnimation;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.thingl.ThinGLUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ScenarioScreen extends ExtendableScreen {
    @Getter
    private final Scenario scenario;

    @Getter
    private final Queue<Timestamp> timestamps = new ConcurrentLinkedQueue<>();
    @Setter
    private boolean playing = true;

    public static boolean RENDER_WITHIN_IMGUI = false;
    private final boolean preload;
    public ScenarioScreen(Scenario scenario, boolean preload) {
        this.timestamps.addAll(scenario.getTimestamps());
        this.scenario = scenario;
        this.dialogueRenderer = new DialogueRenderer(this.scenario);

        this.preload = preload;
    }

    public ScenarioScreen(Scenario scenario) {
        this(scenario, true);
    }


    private FileInfo background, targetBackground;
    private DynamicAnimation backgroundFade = AnimationUtils.dummy(0);

    public void clearBackground(long duration) {
        this.backgroundFade = AnimationUtils.build(duration, this.backgroundFade.getValue(), 0, EasingFunction.LINEAR);
        this.targetBackground = null;
    }

    public void background(FileInfo file, long duration) {
        if (this.background != null) {
            this.targetBackground = file;
            this.backgroundFade = AnimationUtils.build(duration, this.backgroundFade.getValue(), 0, EasingFunction.LINEAR);
        } else {
            this.background = file;
            this.backgroundFade = AnimationUtils.build(duration, this.backgroundFade.getValue() == 1 ? 0 : this.backgroundFade.getValue(), 1, EasingFunction.LINEAR);
        }
    }

    public long sinceDialogue, sincePoll;
    private long sinceRender;

    @Setter @Getter
    private boolean busyDialogue, busyOptions;
    public void pollEvents(boolean bypass) {
        if (!playing && !bypass) {
            this.sinceRender = System.currentTimeMillis();
            this.sinceDialogue = this.sincePoll = 0;
            return;
        }

        while (!timestamps.isEmpty()) {
            final Timestamp peek = timestamps.peek();
            if (peek == null) {
                break;
            }

            final long duration = peek.waitForDialogue() ? this.sinceDialogue : this.sincePoll;
            if (peek.waitForDialogue() && (this.busyDialogue || this.busyOptions)) {
                this.sinceDialogue = 0;
                this.sincePoll = 0;
                break;
            }

            if (duration < peek.time()) {
                break;
            }
            timestamps.poll();
            if (bypass && this.isBusyOptions()) {
                this.optionsRenderer.setOptions(null, null);
            }

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

        boolean busyDialogue = this.busyDialogue && this.dialogueRenderer.isBusy();
        boolean busyOptions = this.busyOptions && !(this.optionsRenderer.getScale().isRunning() && this.optionsRenderer.getScale().getTarget() == 1);
        if (!busyDialogue && !busyOptions) {
            this.sinceDialogue += timeDelta;
        } else {
            this.sinceDialogue = 0;
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
        // Load everything so that there won't be any tiny freezes when loading assets....
        if (preload) {
            final ScenarioScreen dummy = new ScenarioScreen(scenario, false);

            AudioManager.FREEZE = true;

            this.timestamps.forEach(timestamp -> {
                timestamp.events().forEach(event -> {
                    try {
                        final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(event.getClass()).getDeclaredConstructor(event.getClass()).newInstance(event);
                        function.run(dummy);
                    } catch (Exception ignored) {
                    }
                });
            });

            AudioManager.FREEZE = false;
        }

        int start = Integer.MIN_VALUE;
        for (RenderLayer layer : RenderLayer.values()) {
            this.elements.put(start++, new ColorOverlayRenderer(layer));
        }
    }

    @Setter
    private boolean showButtons;
    @Override
    public void render(float delta) {
        ThinGLUtils.start();
        pollEvents(false);

        if (this.backgroundFade.getValue() == 0 && !this.backgroundFade.isRunning()) {
            this.background = this.targetBackground;
            if (this.targetBackground != null) {
                this.backgroundFade = AnimationUtils.dummy(1);
            }
            this.targetBackground = null;
        }
        if (this.targetBackground != null) {
            ThinGLUtils.renderBackground(Base.instance().assetsManager().texture(scenario.getName(), this.targetBackground), Color.WHITE.withAlphaF(1 - this.backgroundFade.getValue()));
        }

        if (this.background != null) {
            ThinGLUtils.renderBackground(Base.instance().assetsManager().texture(scenario.getName(), this.background), Color.WHITE.withAlphaF(this.backgroundFade.getValue()));
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.BLACK);
        }

        final Collection<ElementRenderer<?>> elements = this.elements.reversed().values();
        elements.stream().filter(element -> element.getLayer() == RenderLayer.BEHIND_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.dialogueRenderer.render();

        // TODO: Should the above dialogue render layer above the options as well?
        elements.stream().filter(element -> element.getLayer() == RenderLayer.ABOVE_DIALOGUE).forEach(ElementRenderer::renderAll);

        this.optionsRenderer.render(this);

        // TODO: Properly render shadow?
        if (showButtons) {
            ThinGLUtils.renderTriangleRectangle(1526, 30, 160, 60, 12, 5, Color.fromRGB(244, 245, 246));
            ThinGLUtils.renderTriangleRectangle(1526 + 160 + 30, 32, 160, 60 - 2, 12, 5, Color.fromRGB(244, 245, 246));

            final TextRun auto = TextRun.fromString(FontUtils.font(FontStyle.BOLD, FontType.NotoSans), "Auto", Color.fromRGB(45, 70, 99), 1 << 2);
            TextUtils.textRun(36, auto, 1526 + 12 + (160 / 2f) - (TextUtils.getVisualWidth(36, auto.shape()) / 2f) - 3,
                    30 + (60 / 2f) + (TextUtils.getVisualHeight(36, auto.shape()) / 2f) + 2,
                    RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);

            final TextRun menu = TextRun.fromString(FontUtils.font(FontStyle.BOLD, FontType.NotoSans), "Menu", Color.fromRGB(45, 70, 99), 1 << 2);
            TextUtils.textRun(36, menu, 1526 + 160 + 30 + 12 + (160 / 2f) - (TextUtils.getVisualWidth(36, menu.shape()) / 2f) - 5,
                    30 + (60 / 2f) + (TextUtils.getVisualHeight(36, menu.shape()) / 2f) + 2,
                    RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.LOGICAL_LEFT);
        }

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
