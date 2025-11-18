package oxy.bascenario.screens;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import net.raphimc.thingl.text.font.Font;
import oxy.bascenario.Launcher;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.utils.AnimationUtils;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

@RequiredArgsConstructor
public class ScenarioPreviewScreen extends ExtendableScreen {
    private final Scenario scenario;

    @Override
    public void show() {
        if (scenario.getPreviewSound().isPresent()) {
            AudioManager.getInstance().play(scenario.getPreviewSound().get());
        }
    }

    private DynamicAnimation borderFade = AnimationUtils.dummy(0);
    private DynamicAnimation titleBoxPopup = AnimationUtils.dummy(0), titleBoxFade = AnimationUtils.dummy(0);
    private DynamicAnimation titlePopup = AnimationUtils.dummy(0), titleFade = AnimationUtils.dummy(0);

    private long sinceFinished = -1;
    private DynamicAnimation globalFade = AnimationUtils.dummy(1);

    @Override
    public void render(float delta) {
        ThinGLUtils.start();

        renderBackground();
        renderBorder();
        renderTitle();

        if (borderFade instanceof AnimationUtils.DummyAnimation) {
            borderFade = AnimationUtils.build(420, 0, 100, EasingFunction.LINEAR);
        } else if (borderFade.getValue() > 20 && titleBoxPopup instanceof AnimationUtils.DummyAnimation) {
            titleBoxPopup = AnimationUtils.build(400, 0, 1, EasingFunction.QUAD);
            titleBoxFade = AnimationUtils.build(400, 0, 1, EasingFunction.LINEAR);
        } else if (titleBoxPopup.getValue() > 0.6 && titlePopup instanceof AnimationUtils.DummyAnimation) {
            titlePopup = AnimationUtils.build(400, 0.75F, 1, EasingFunction.SINE);
            titleFade = AnimationUtils.build(400, 0, 255, EasingFunction.LINEAR);
        } else if (!titleBoxPopup.isRunning() && !(titlePopup instanceof AnimationUtils.DummyAnimation) && globalFade instanceof AnimationUtils.DummyAnimation) {
            if (sinceFinished == -1) {
                sinceFinished = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - sinceFinished >= 2000L) {
                globalFade = AnimationUtils.build(800, 1, 0, EasingFunction.LINEAR);
                sinceFinished = -1;
            }
        } else if (isDoingExitingFade() && !globalFade.isRunning()) {
            if (sinceFinished == -1) {
                sinceFinished = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - sinceFinished >= 500L) {
                // Finished, switch to the screen responsible for main scenario rendering!
                Launcher.WINDOW.setScreen(new ScenarioScreen(this.scenario));
            }
        }

        // If there are no background to fade, then draw a black overlay on top of everything to fade away.
        if (this.scenario.getPreviewBackground().isEmpty()) {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(0, 0, 0, Math.round(255 * (1 - globalFade.getValue()))));
        }

        ThinGLUtils.end();
    }

    private void renderBackground() {
        if (scenario.getPreviewBackground().isPresent()) {
            final Image preview = scenario.getPreviewBackground().get();
            ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture(preview.file()), 0, 0, 1920, 1080);
            ThinGLUtils.blurRectangle(0, 0, 1920, 1080, Math.round(18 * this.globalFade.getValue())); // Very nice blur thanks to ThinGL.
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(60, 60, 60, Math.round(100 * globalFade.getValue())));
        } else {
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGBA(22, 23, 26, 255));
        }
    }

    private void renderBorder() {
        // The color of the border is different depending on the
        int generalColor = scenario.getPreviewBackground().isPresent() ? 255 : 227;
        Color color = Color.fromRGBA(generalColor, generalColor, generalColor, Math.round(borderFade.getValue()));
        if (isDoingExitingFade()) {
            color = color.withAlpha(Math.round(100 * globalFade.getValue()));
        }

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture("/assets/base/uis/preview/border.png"), 0, 0, 1920, 1080, color);
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

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, TextureManager.getInstance().getTexture("/assets/base/uis/preview/title.png"), 0, Math.max(0, 1080 / 2F - (sizeY / 2)), 1920, sizeY, color);
    }

    private static Font TITLE, SUBTITLE;
    private void renderTitle() {
        renderTitleBox();

        if (TITLE == null) {
            TITLE = FontUtils.getFont("NotoSansSemiBold", 76);
        }
        if (SUBTITLE == null) {
            SUBTITLE = FontUtils.getFont("NotoSansSemiBold", 42);
        }

        int alpha = Math.round(titleFade.getValue());
        if (isDoingExitingFade()) {
            alpha = Math.round(255 * globalFade.getValue());
        }

        final float scale = this.titlePopup.getValue();
        {
            final TextRun text = TextRun.fromString(TITLE, this.scenario.getTitle(), Color.fromRGBA(70, 98, 150, alpha));
            float textCenterX = Math.max(0, 1920 / 2F - (ThinGL.rendererText().getVisualWidth(text.shape()) * scale / 2));
            float textCenterY = Math.max(0, 1080 / 2F - (ThinGL.rendererText().getVisualHeight(text.shape()) * scale / 2)) + 5;
            if (!scenario.getSubtitle().isEmpty()) {
                textCenterY += 32;
            }

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, text, 0, 0);
            GLOBAL_RENDER_STACK.popMatrix();
        }

        if (scenario.getSubtitle().isEmpty()) {
            return;
        }

        {
            final TextRun text = TextRun.fromString(SUBTITLE, this.scenario.getSubtitle(), Color.fromRGBA(46, 69, 96, alpha));
            float width = ThinGL.rendererText().getVisualWidth(text.shape());
            float height = ThinGL.rendererText().getVisualHeight(text.shape());
            float textCenterX = Math.max(0, 1920 / 2F - (width * scale / 2));
            float textCenterY = Math.max(0, 1080 / 2F - (height * scale / 2)) - 60;

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textCenterX, textCenterY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, -10, height - 15, width + 10, height - 15 + 8, Color.fromRGBA(250, 238, 129, Math.round(255 * globalFade.getValue())));
            ThinGL.rendererText().textRun(GLOBAL_RENDER_STACK, text, 0, 0);
            GLOBAL_RENDER_STACK.popMatrix();
        }
    }

    private boolean isDoingExitingFade() {
        return !(globalFade instanceof AnimationUtils.DummyAnimation);
    }
}
