package oxy.bascenario.screens.renderer.dialogue;

import lombok.Getter;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.Base;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.animation.DynamicAnimation;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import java.util.List;

import static oxy.bascenario.screens.renderer.dialogue.OptionsRenderer.*;
import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class QuestionSelectionRenderer {
    public static final float BUTTON_WIDTH = 980;

    @Getter
    private DynamicAnimation offset = AnimationUtils.dummy(1);
    private DynamicAnimation scale = AnimationUtils.dummy(1);
    private DynamicAnimation flash = AnimationUtils.dummy(1);
    private boolean flipped;

    private FontType type = FontType.NotoSans;
    private String question = "";
    private List<ShowQuestionSelectionEvent.Answer> answers;

    public void setValues(FontType type, String question, List<ShowQuestionSelectionEvent.Answer> answers) {
        this.clicked = null;
        this.answers = answers;
        this.type = type;
        this.question = question;

        this.flipped = false;
        this.releasedalready = false;

        if (this.answers != null) {
            this.offset = AnimationUtils.build(400, -420, -86, EasingFunction.SINE);
        }
    }

    public void render(ScenarioScreen screen) {
        if (this.answers == null) {
            return;
        }

        if (flipped && this.flash.getValue() == 0 && !this.scale.isRunning() && this.clicked != null) {
            this.answers = null;
            this.question = null;
            screen.setBusyAnswerSelection(false);
            this.offset = new AnimationUtils.DummyAnimation(1);
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

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                Base.instance().assetsManager().texture("assets/base/uis/buttons/Scenario_SelectionGroup_Deco.png"), offset.getValue(),
                -550, 1430, 2480, Color.WHITE.withAlphaF(this.offset.getProgress()));

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.translate(this.offset.getValue() + 86, 0, 0);
        final int fontSize = type == FontType.NotoSans ? 38 : 45;
        float y = 250;
        for (String s : question.split("\n")) {
            final TextRun run = TextRun.fromString(FontUtils.font(FontStyle.REGULAR, type), s, Color.WHITE.withAlphaF(this.offset.getProgress()), 0, TEXT_COLOR.withAlphaF(this.offset.getProgress()));
            TextUtils.textRun(fontSize, run, 165 + 1020 / 2f - TextUtils.getVisualWidth(fontSize, run.shape()) / 2f, y, RendererText.VerticalOrigin.LOGICAL_TOP, RendererText.HorizontalOrigin.VISUAL_LEFT);
            y += TextUtils.getLogicalHeight(fontSize, run.shape());
        }

        int i = 0;
        for (ShowQuestionSelectionEvent.Answer text : this.answers) {
            float scale = this.clicked != null ? (text.equals(this.clicked) ? this.scale.getValue() : 1) : this.scale.getValue();
            float alpha = this.clicked != null ? (text.equals(this.clicked) ? this.flash.getValue() : 1) : this.flash.getValue();

            if (this.offset.isRunning()) {
                alpha = this.offset.getProgress();
            }

            final float buttonHeight = BUTTON_HEIGHT * scale, buttonWidth = BUTTON_WIDTH * scale;

            // Nothing to note, this is just basic math.
            final float buttonX = 135 - (i * 20) + 1020 / 2f - buttonWidth / 2f;
            float buttonY = 450 + ((BUTTON_HEIGHT + DISTANCE_BETWEEN) * i);

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/uis/buttons/dialogue_button.png"),
                    buttonX, buttonY, BUTTON_WIDTH * scale, BUTTON_HEIGHT * scale, text.grayedOut() ? Color.GRAY.withAlphaF(alpha) : Color.WHITE.withAlphaF(alpha));

            float textScale = 1;
            TextRun textRun = TextRun.fromString(FontUtils.font(FontStyle.REGULAR, type), text.answer(), TEXT_COLOR.withAlphaF(alpha * (text.grayedOut() ? 0.5f : 1)));
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

        GLOBAL_RENDER_STACK.popMatrix();
    }

    private ShowQuestionSelectionEvent.Answer clicked;

    private long sinceClicked;
    private boolean releasedalready;
    public void mouseRelease() {
        if (this.answers == null) {
            return;
        }

        if (!releasedalready && clicked != null) {
            if (System.currentTimeMillis() - this.sinceClicked > 500) {
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
            }

            this.scale = AnimationUtils.build(400, this.scale.getValue(), SCALE_SIZE, EasingFunction.CUBIC);
            releasedalready = true;
        }
    }

    public void mouseClicked(ScenarioScreen screen, double mouseX, double mouseY) {
        if (this.answers == null || this.offset.isRunning() || this.clicked != null) {
            return;
        }

        int i = 0;
        for (ShowQuestionSelectionEvent.Answer text : this.answers) {
            if (text.grayedOut()) {
                i++;
                continue;
            }

            float buttonY = 450 + ((BUTTON_HEIGHT + DISTANCE_BETWEEN) * i);
            final float buttonX = 135 - (i * 20) + 1020 / 2f - BUTTON_WIDTH / 2f;

            if (mouseX >= buttonX && mouseX <= buttonX + BUTTON_WIDTH && mouseY >= buttonY && mouseY <= buttonY + BUTTON_HEIGHT) {
                this.clicked = text;
                this.scale = AnimationUtils.build(250, 1f, 0.94f, EasingFunction.CUBIC);
                screen.getDialogueRenderer().setCurrentIndex(text.dialogueIndex());
                this.sinceClicked = System.currentTimeMillis();
                AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
                break;
            }

            i++;
        }
    }
}
