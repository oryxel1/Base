package oxy.bascenario.screens.renderer.element.thingl;

import com.badlogic.gdx.math.MathUtils;
import oxy.bascenario.Base;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.utils.ScreenUtils;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.screens.renderer.element.base.ThinGLElementRenderer;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.thingl.ThinGLUtils;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class PreviewRenderer extends ThinGLElementRenderer<Preview> {
    private final Scenario scenario;

    public PreviewRenderer(Preview element, RenderLayer layer, Scenario scenario) {
        super(element, layer);
        this.scenario = scenario;
    }

    private final DynamicAnimation borderFade = AnimationUtils.build(420, 0, 100, EasingFunction.LINEAR);
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
        // Ehmmm... It's hardcoded yes.... I know but it works!
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
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, ScreenUtils.width(), ScreenUtils.height(), Color.fromRGBA(0, 0, 0, Math.round(255 * (1 - globalFade.getValue()))));
        }
    }

    private void renderBackground() {
        if (element.background() != null) {
            ThinGLUtils.renderBackground(Base.instance().assetsManager().texture(scenario.getName(), element.background()), Color.WHITE);
            ThinGLUtils.fullScreenBlur(Math.round(10 * this.globalFade.getValue())); // Very nice blur thanks to ThinGL.
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, ScreenUtils.width(), ScreenUtils.height(), Color.fromRGBA(60, 60, 60, Math.round(100 * globalFade.getValue())));
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, ScreenUtils.width(), ScreenUtils.height(), Color.fromRGBA(22, 23, 26, 255));
        }
    }

    private void renderBorder() {
        // The color of the border is different depending on the background....
        int generalColor = element.background() != null ? 255 : 227;
        Color color = Color.fromRGBA(generalColor, generalColor, generalColor, Math.round(borderFade.getValue()));
        if (isDoingExitingFade()) {
            color = color.withAlpha(Math.round(100 * globalFade.getValue()));
        }

        final float widthScale = ScreenUtils.widthScale(), heightScale = ScreenUtils.heightScale(), globalScale = ScreenUtils.globalScale();

        float width = 1848 * widthScale;
        float x = ScreenUtils.width() / 2f - width / 2f;

        float height = 1006 * heightScale;

        float y = ScreenUtils.height() / 2f - height / 2f;

        ThinGL.renderer2D().outlinedRoundedRectangle(GLOBAL_RENDER_STACK, x, y, x + width, y + height, 4 * globalScale, color, 5 * widthScale);

        float innerHeight = height - (30 * widthScale);
        float innerWidth = width - (30 * widthScale);
        float innerX = ScreenUtils.width() / 2f - innerWidth / 2f;
        float innerY = ScreenUtils.height() / 2f - innerHeight / 2f;

        // todo: flip the corner!
        ThinGL.renderer2D().outlinedRoundedRectangle(GLOBAL_RENDER_STACK, innerX, innerY, innerX + innerWidth, innerY + innerHeight, 15 * globalScale, color, 2 * widthScale);

        float circleWidth = 4 * globalScale;
        ThinGL.renderer2D().outlinedCircle(GLOBAL_RENDER_STACK, innerX + circleWidth * 2f, innerY + circleWidth * 2f, circleWidth, color, circleWidth);

        float crossHeight = 16 * globalScale, crossY = innerY + innerHeight - crossHeight;
        float crossWidth = 3 * globalScale, crossX = innerX + (crossWidth * 2f);

        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, crossX, crossY, crossX + crossWidth, crossY + crossHeight, color);

        crossX -= crossHeight / 2f - crossWidth / 2f;
        crossY += crossHeight / 2f - crossWidth / 2f;
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, crossX, crossY, crossX + crossHeight, crossY + crossWidth, color);
    }

    private void renderTitleBox() {
        if (titleBoxPopup instanceof AnimationUtils.DummyAnimation) {
            return;
        }

        float widthScale = ScreenUtils.widthScale();
        float height = 260 * widthScale * MathUtils.lerp(0.62f, 1f, titleBoxPopup.getValue()), width = 1848 * widthScale;
        Color color = Color.fromRGBA(255, 255, 255, Math.round(255 * titleBoxFade.getValue()));
        if (isDoingExitingFade()) {
            color = color.withAlpha(Math.round(255 * globalFade.getValue()));
        }

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                Base.instance().assetsManager().texture("assets/base/uis/preview/title.png"),
                ScreenUtils.width() / 2f - width / 2f, ScreenUtils.height() / 2F - height / 2, width, height, color);
    }

    private void renderTitle() {
        renderTitleBox();

        int alpha = Math.round(titleFade.getValue());
        if (isDoingExitingFade()) {
            alpha = Math.round(255 * globalFade.getValue());
        }

        final float widthScale = ScreenUtils.widthScale();
        final float scale = this.titlePopup.getValue();
        {
            float fontSize = 76 * ScreenUtils.widthScale();
            final TextRun text = TextRun.fromString(FontUtils.font(FontStyle.SEMI_BOLD, element.type()), this.element.title(), Color.fromRGBA(70, 98, 150, alpha));
            float textCenterX = Math.max(0, ScreenUtils.width() / 2F - (TextUtils.getVisualWidth(fontSize, text.shape()) * scale / 2));
            float textCenterY = Math.max(0, ScreenUtils.height() / 2F - (TextUtils.getVisualHeight(fontSize, text.shape()) * scale / 2)) + 5 * widthScale;
            if (!element.subtitle().isEmpty()) {
                textCenterY += 32 * widthScale;
            }

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            TextUtils.textRun(fontSize, text, 0, 0);
            GLOBAL_RENDER_STACK.popMatrix();
        }

        if (element.subtitle().isEmpty()) {
            return;
        }

        {
            float fontSize = 42 * ScreenUtils.widthScale();
            final TextRun text = TextRun.fromString(FontUtils.font(FontStyle.SEMI_BOLD, element.type()), this.element.subtitle(), Color.fromRGBA(46, 69, 96, alpha));
            float width = TextUtils.getVisualWidth(fontSize, text.shape());
            float height = TextUtils.getVisualHeight(fontSize, text.shape());
            float textCenterX = Math.max(0, ScreenUtils.width() / 2F - (width * scale / 2));
            float textCenterY = Math.max(0, ScreenUtils.height() / 2F - (height * scale / 2)) - 60 * widthScale;

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, -10 * widthScale, 20 * widthScale, width + 15 * widthScale, 28 * widthScale, Color.fromRGBA(250, 238, 129, Math.round(255 * globalFade.getValue())));
            TextUtils.textRun(fontSize, text, 0, 22 * widthScale, RendererText.VerticalOrigin.BASELINE, RendererText.HorizontalOrigin.VISUAL_LEFT);
            GLOBAL_RENDER_STACK.popMatrix();
        }
    }

    private boolean isDoingExitingFade() {
        return !(globalFade instanceof AnimationUtils.DummyAnimation);
    }
}
