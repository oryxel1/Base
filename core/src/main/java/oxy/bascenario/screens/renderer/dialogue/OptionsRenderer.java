package oxy.bascenario.screens.renderer.dialogue;

import lombok.Getter;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import oxy.bascenario.Base;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import java.util.Map;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class OptionsRenderer {
    private static final Color TEXT_COLOR = Color.fromRGB(44, 67, 90);
    private static final float BUTTON_HEIGHT = 97, BUTTON_WIDTH = 1345, DISTANCE_BETWEEN = 35;

    private FontType type;
    private Map<String, Integer> options;
    public void setOptions(FontType type, Map<String, Integer> options) {
        this.type = type;
        this.options = options;
        if (this.options != null) {
            this.scale = AnimationUtils.build(250, 0.7F, 1F, EasingFunction.QUAD);
        }
    }

    public boolean isBusy() {
        return this.options != null;
    }

    @Getter
    private DynamicAnimation scale = AnimationUtils.dummy(1);
    private DynamicAnimation flash = AnimationUtils.dummy(1);

    public void render(ScenarioScreen screen) {
        if (this.options == null) {
            return;
        }

        if (this.flash.getValue() == 0 && !this.scale.isRunning() && this.clicked != null) {
            this.options = null;
            screen.setBusyOptions(false);
            return;
        }

        if (this.queue && this.scale.getValue() == 0.9f) {
            this.scale.setTarget(1.1f);
            this.flash = AnimationUtils.build(110, 1, 0, EasingFunction.LINEAR);
            this.queue = false;
        }

        if (!(this.flash instanceof AnimationUtils.DummyAnimation) && !this.flash.isRunning()) {
            if (this.flash.getTarget() == 1 || !this.scale.isRunning()) {
                this.flash.setTarget(0);
            } else if (this.flash.getTarget() == 0 && this.scale.isRunning()) {
                this.flash.setTarget(1);
            }
        }

        float totalHeight = BUTTON_HEIGHT * this.options.size() + DISTANCE_BETWEEN * (options.size() - 1);
        int i = 0;
        for (String text : this.options.keySet()) {
            float scale = this.clicked != null ? text.equals(this.clicked) ? this.scale.getValue() : 1 : this.scale.getValue();
            float alpha = this.clicked != null ? text.equals(this.clicked) ? this.flash.getValue() : 1 : this.flash.getValue();

            float buttonHeight = BUTTON_HEIGHT * scale, buttonWidth = BUTTON_WIDTH * scale;
            float buttonY = 540 - (totalHeight / 2f) - buttonHeight + 14.9796f + ((buttonHeight + DISTANCE_BETWEEN) * i);
            float buttonX = 1920 / 2f - buttonWidth / 2f;
            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(buttonX, buttonY, 0);
            GLOBAL_RENDER_STACK.scale(scale);
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/buttons/button.png"), 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, Color.WHITE.withAlphaF(alpha));
            GLOBAL_RENDER_STACK.popMatrix();

            float textScale = 1;
            // For fuck’s sake, I don't fucking know what the actual font is, It's slightly thinner than noto sans regular that for sure.
            TextRun textRun = TextRun.fromString(FontUtils.font(FontStyle.REGULAR, type), text, TEXT_COLOR.withAlphaF(alpha));
            if (TextUtils.getVisualWidth(44, textRun.shape()) > buttonWidth - 100) {
                textScale = Math.min((buttonWidth - 100) / TextUtils.getVisualWidth(44, textRun.shape()), 1);
            }

            float textX = buttonX + (buttonWidth / 2) - (TextUtils.getVisualWidth(44, textRun.shape()) * textScale * scale) / 2;
            float textY = buttonY + (buttonHeight / 2) - (TextUtils.getLogicalHeight(44, textRun.shape()) * textScale * scale) / 2;
            GLOBAL_RENDER_STACK.pushMatrix();
            GLOBAL_RENDER_STACK.translate(textX + 7, textY, 0);
            GLOBAL_RENDER_STACK.scale(textScale);
            GLOBAL_RENDER_STACK.scale(scale);
            TextUtils.textRun(44, textRun, 0, 0, RendererText.VerticalOrigin.LOGICAL_TOP, RendererText.HorizontalOrigin.LOGICAL_LEFT);
            GLOBAL_RENDER_STACK.popMatrix();

            i++;
        }
    }

    private boolean queue, sound;
    public void mouseRelease() {
        if (this.clicked != null) {
            if (!sound) {
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
                sound = true;
            }
            if (this.scale.getValue() != 0.9f) {
                this.queue = true;
            } else {
                this.scale.setTarget(1.1f);
                this.flash = AnimationUtils.build(110, 1, 0, EasingFunction.LINEAR);
            }
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
                this.scale.setTarget(0.9f);
                screen.getDialogueRenderer().setCurrentIndex(this.options.get(text));
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
//                AudioManager.getInstance().play("assets/base/sounds/click-sound.mp3", false, 1, true);
                break;
            }

            i++;
        }
    }
}
