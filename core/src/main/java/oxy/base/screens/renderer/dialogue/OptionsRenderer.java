package oxy.base.screens.renderer.dialogue;

import lombok.Getter;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import oxy.base.Base;
import oxy.base.api.effects.Sound;
import oxy.base.api.render.elements.text.font.FontStyle;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.managers.AudioManager;
import oxy.base.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.animation.AnimationUtils;
import oxy.base.utils.font.FontUtils;
import oxy.base.utils.font.TextUtils;

import java.util.Map;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class OptionsRenderer {
    public static final long FLASH_DURATION = 90L;
    public static final float SCALE_SIZE = 1.125f;
    public static final float FLASH_ALPHA = 0.5f;

    public static final Color TEXT_COLOR = Color.fromRGB(45, 58, 71);
    public static final float TEXT_SIZE = 40.5f;
    public static final float BUTTON_HEIGHT = 97, BUTTON_WIDTH = 1340, DISTANCE_BETWEEN = 35;

    private FontType type;
    private Map<String, Integer> options;
    public void setOptions(FontType type, Map<String, Integer> options) {
        this.clicked = null;
        this.options = options;
        this.type = type;
        this.flipped = false;
        this.releasedalready = false;
        if (this.options != null) {
            this.scale = AnimationUtils.build(250, 0.7F, 1F, EasingFunction.CUBIC);
        }
    }

    public boolean isBusy() {
        return this.options != null;
    }

    @Getter
    private DynamicAnimation scale = AnimationUtils.dummy(1);
    private DynamicAnimation flash = AnimationUtils.dummy(1);
    private boolean flipped;

    public void render(ScenarioScreen screen) {
        if (this.options == null) {
            return;
        }

        if (flipped && this.flash.getValue() == 0 && !this.scale.isRunning() && this.clicked != null) {
            this.options = null;
            screen.setBusyOptions(false);
            this.scale = new AnimationUtils.DummyAnimation(1);
            this.flash = new AnimationUtils.DummyAnimation(1);
            return;
        }

        if (this.scale.getValue() == SCALE_SIZE && this.flash instanceof AnimationUtils.DummyAnimation) {
            this.flash = AnimationUtils.build(FLASH_DURATION, 0, FLASH_ALPHA, EasingFunction.LINEAR);
        }

        if (!(this.flash instanceof AnimationUtils.DummyAnimation) && !this.flash.isRunning()) {
            this.flash.setTarget(this.flash.getTarget() == FLASH_ALPHA ? 0 : FLASH_ALPHA);
            flipped = true;
        }

        float totalHeight = BUTTON_HEIGHT * this.options.size() + DISTANCE_BETWEEN * (options.size() - 1);
        int i = 0;
        for (String text : this.options.keySet()) {
            float scale = this.clicked != null ? text.equals(this.clicked) ? this.scale.getValue() : 1 : this.scale.getValue(),
                    alpha = this.clicked != null ? text.equals(this.clicked) ? this.flash.getValue() : 1 : this.flash.getValue();

            final float buttonHeight = BUTTON_HEIGHT * scale, buttonWidth = BUTTON_WIDTH * scale;

            // Nothing to note, this is just basic math.
            final float buttonX = 1920 / 2f - buttonWidth / 2f;

            // We need to find the center of the button. Then we can use it so the button must be centered around it regardless of size.
            float buttonY = 540 - (totalHeight / 2f) - BUTTON_HEIGHT + 14.9796f + ((BUTTON_HEIGHT + DISTANCE_BETWEEN) * i) + BUTTON_HEIGHT / 2;
            buttonY -= buttonHeight / 2f;

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/uis/buttons/dialogue_button.png"),
                    buttonX, buttonY, BUTTON_WIDTH * scale, BUTTON_HEIGHT * scale, Color.WHITE.withAlphaF(alpha));

            float textScale = 1;
            TextRun textRun = TextRun.fromString(FontUtils.font(FontStyle.REGULAR, type), text, TEXT_COLOR.withAlphaF(alpha));
            if (TextUtils.getVisualWidth(TEXT_SIZE * scale, textRun.shape()) > buttonWidth - 100) {
                textScale = Math.min((buttonWidth - 120) / TextUtils.getVisualWidth(TEXT_SIZE * scale, textRun.shape()), 1);
            }

            float textX = buttonX + (buttonWidth / 2) - (TextUtils.getVisualWidth(TEXT_SIZE, textRun.shape()) * textScale * scale) / 2;
            float textY = buttonY + (buttonHeight / 2) - (TextUtils.getLogicalHeight(TEXT_SIZE, textRun.shape()) * textScale * scale) / 2;

            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textX, textY, 0);
            GLOBAL_RENDER_STACK.scale(textScale);
            GLOBAL_RENDER_STACK.scale(scale);
            TextUtils.textRun(TEXT_SIZE, textRun, 0, 0, RendererText.VerticalOrigin.LOGICAL_TOP, RendererText.HorizontalOrigin.VISUAL_LEFT);
            GLOBAL_RENDER_STACK.popMatrix();

            i++;
        }
    }

    private long sinceClicked;
    private boolean releasedalready;
    public void mouseRelease() {
        if (!releasedalready && clicked != null) {
            if (System.currentTimeMillis() - this.sinceClicked > 500) {
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
            }

            this.scale = AnimationUtils.build(400, this.scale.getValue(), SCALE_SIZE, EasingFunction.CUBIC);
            releasedalready = true;
        }
    }

    private String clicked;
    public void mouseClicked(ScenarioScreen screen, double mouseX, double mouseY) {
        if (this.scale.getValue() != 1 || this.clicked != null) {
            return;
        }

        float totalHeight = BUTTON_HEIGHT * this.options.size() + DISTANCE_BETWEEN * (options.size() - 1);
        float buttonX = 1920f / 2 - (BUTTON_WIDTH / 2);

        int i = 0;
        for (String text : this.options.keySet()) {
            float buttonY = 540 - (totalHeight / 2f) - BUTTON_HEIGHT + 14.9796f + ((BUTTON_HEIGHT + DISTANCE_BETWEEN) * i);
            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH && mouseY >= buttonY && mouseY <= buttonY + BUTTON_HEIGHT) {
                this.clicked = text;
                this.scale.setTarget(0.94f);
                screen.getDialogueRenderer().setCurrentIndex(this.options.get(text));
                this.sinceClicked = System.currentTimeMillis();
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
                break;
            }

            i++;
        }
    }
}
