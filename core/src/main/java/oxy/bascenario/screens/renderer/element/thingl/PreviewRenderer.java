package oxy.bascenario.screens.renderer.element.thingl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.resource.font.Font;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class PreviewRenderer extends ThinGLElementRenderer<Preview> {
    private final Scenario scenario;

    public PreviewRenderer(Preview element, RenderLayer layer, Scenario scenario) {
        super(element, layer);
        this.scenario = scenario;
    }

    private DynamicAnimation borderFade = AnimationUtils.dummy(0);
    private DynamicAnimation titleBoxPopup = AnimationUtils.dummy(0), titleBoxFade = AnimationUtils.dummy(0);
    private DynamicAnimation titlePopup = AnimationUtils.dummy(0), titleFade = AnimationUtils.dummy(0);

    private long sinceFinished = -1;
    private DynamicAnimation globalFade = AnimationUtils.dummy(1);

    private boolean finished;

    @Override
    public boolean selfDestruct() {
        return this.finished;
    }

    @Override
    protected void renderThinGL() {
        ThinGLUtils.start();

        // Ehmmm... It's hardcoded yes.... I know but it works!
        if (borderFade instanceof AnimationUtils.DummyAnimation) {
            borderFade = AnimationUtils.build(420, 0, 100, EasingFunction.LINEAR);
        }
        if (borderFade.getValue() > 20 && titleBoxPopup instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - borderFade.resolve(20);

            titleBoxPopup = AnimationUtils.build(400, distance, 0, 1, EasingFunction.QUAD);
            titleBoxFade = AnimationUtils.build(400, distance, 0, 1, EasingFunction.LINEAR);
        }
        if (titleBoxPopup.getValue() > 0.6 && titlePopup instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - titleBoxPopup.resolve(0.6f);

            titlePopup = AnimationUtils.build(400, distance, 0.75F, 1, EasingFunction.SINE);
            titleFade = AnimationUtils.build(400, distance, 0, 255, EasingFunction.LINEAR);
        }
        if (!titleBoxPopup.isRunning() && !(titlePopup instanceof AnimationUtils.DummyAnimation) && globalFade instanceof AnimationUtils.DummyAnimation) {
            if (sinceFinished == -1) {
                sinceFinished = TimeUtils.currentTimeMillis();
                sinceFinished -= titleBoxPopup.resolve(titleBoxPopup.getTarget());
            }

            if (TimeUtils.currentTimeMillis() - this.sinceFinished >= 2000L) {
                long finishedDis = (TimeUtils.currentTimeMillis() - this.sinceFinished) - 2000L;
                long time = TimeUtils.currentTimeMillis() - finishedDis;

                globalFade = AnimationUtils.build(800, time, 1, 0, EasingFunction.LINEAR);
                sinceFinished = -1;
            }
        }

        if (isDoingExitingFade() && !globalFade.isRunning()) {
            if (sinceFinished == -1) {
                sinceFinished = TimeUtils.currentTimeMillis();
                sinceFinished -= globalFade.resolve(globalFade.getTarget());
            }

            if (TimeUtils.currentTimeMillis() - sinceFinished >= 500L) {
                finished = true;
            }
        }

        renderBackground();
        renderBorder();
        renderTitle();

        // If there are no background to fade, then draw a black overlay on top of everything to fade away.
        if (element.background() == null) {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(0, 0, 0, Math.round(255 * (1 - globalFade.getValue()))));
        }

        ThinGLUtils.end();
    }

    private void renderBackground() {
        if (element.background() != null) {
            ThinGLUtils.renderBackground(Base.instance().assetsManager().texture(scenario.getName(), element.background()), Color.WHITE);
            ThinGLUtils.blurRectangle(0, 0, 1920, 1080, Math.round(18 * this.globalFade.getValue())); // Very nice blur thanks to ThinGL.
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(60, 60, 60, Math.round(100 * globalFade.getValue())));
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(22, 23, 26, 255));
        }
    }

    private void renderBorder() {
        // The color of the border is different depending on the background....
        int generalColor = element.background() != null ? 255 : 227;
        Color color = Color.fromRGBA(generalColor, generalColor, generalColor, Math.round(borderFade.getValue()));
        if (isDoingExitingFade()) {
            color = color.withAlpha(Math.round(100 * globalFade.getValue()));
        }

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/preview/border.png"), 0, 0, 1920, 1080, color);
    }

    private void renderTitleBox() {
        if (titleBoxPopup instanceof AnimationUtils.DummyAnimation) {
            return;
        }

        float sizeY = 1080 - (400 * (1 - titleBoxPopup.getValue()));
        Color color = Color.fromRGBA(255, 255, 255, Math.round(255 * titleBoxFade.getValue()));
        if (isDoingExitingFade()) {
            color = color.withAlpha(Math.round(255 * globalFade.getValue()));
        }

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/preview/title.png"), 0, Math.max(0, 1080 / 2F - (sizeY / 2)), 1920, sizeY, color);
    }

    private void renderTitle() {
        renderTitleBox();

        int alpha = Math.round(titleFade.getValue());
        if (isDoingExitingFade()) {
            alpha = Math.round(255 * globalFade.getValue());
        }

        final float scale = this.titlePopup.getValue();
        {
            final TextRun text = TextRun.fromString(FontUtils.SEMI_BOLD, this.element.title(), Color.fromRGBA(70, 98, 150, alpha));
            float textCenterX = Math.max(0, 1920 / 2F - (TextUtils.getVisualWidth(76, text.shape()) * scale / 2));
            float textCenterY = Math.max(0, 1080 / 2F - (TextUtils.getVisualHeight(76, text.shape()) * scale / 2)) + 5;
            if (!element.subtitle().isEmpty()) {
                textCenterY += 32;
            }

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            TextUtils.textRun(76, text, 0, 0);
            GLOBAL_RENDER_STACK.popMatrix();
        }

        if (element.subtitle().isEmpty()) {
            return;
        }

        {
            final TextRun text = TextRun.fromString(FontUtils.SEMI_BOLD, this.element.subtitle(), Color.fromRGBA(46, 69, 96, alpha));
            float width = TextUtils.getVisualWidth(42, text.shape());
            float height = TextUtils.getVisualHeight(42, text.shape());
            float textCenterX = Math.max(0, 1920 / 2F - (width * scale / 2));
            float textCenterY = Math.max(0, 1080 / 2F - (height * scale / 2)) - 60;

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, -10, 20, width + 15, 28, Color.fromRGBA(250, 238, 129, Math.round(255 * globalFade.getValue())));
            TextUtils.textRun(42, text, 0, 22, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);
            GLOBAL_RENDER_STACK.popMatrix();
        }
    }

    private boolean isDoingExitingFade() {
        return !(globalFade instanceof AnimationUtils.DummyAnimation);
    }
}
