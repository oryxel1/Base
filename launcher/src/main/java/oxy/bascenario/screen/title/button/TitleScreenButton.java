package oxy.bascenario.screen.title.button;

import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.utils.Launcher;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TitleScreenButton {
    private final float x, y, width, height;
    private final Runnable runnable;

    private final String text;

    private final DynamicAnimation hoverY;
    public TitleScreenButton(String text, float x, float y, float width, float height, Runnable action) {
        this.text = text;
        this.hoverY = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, 200L, y);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.runnable = action;
    }

    public void render() {
        ThinGL.renderer2D().filledRoundedRectangle(GLOBAL_RENDER_STACK, x, this.hoverY.getValue(), x + width, this.hoverY.getValue() + height, 5, Color.WHITE.withAlphaF(0.4f));
        ThinGL.renderer2D().outlinedRoundedRectangle(GLOBAL_RENDER_STACK, x, this.hoverY.getValue(), x + width, this.hoverY.getValue() + height, 5, Color.WHITE, 2);

        if (this.isHoveringOver(Launcher.WINDOW.mouseX, Launcher.WINDOW.mouseY)) {
            this.hoverY.setTarget(this.y - 0.009F * 1080);
        } else {
            this.hoverY.setTarget(this.y);
        }

        final TextRun run = TextRun.fromString(FontUtils.SEMI_BOLD, text);
        TextUtils.textRun(35, run,
                x + (width / 2) - (TextUtils.getVisualWidth(35, run.shape()) / 2),
                this.hoverY.getValue() + (height / 2) - (TextUtils.getVisualHeight(35, run.shape()) / 2));
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height && button == 0) {
            this.runnable.run();
            AudioManager.getInstance().play(Sound.sound(new FileInfo("assets/base/sounds/click-sound.mp3", false, true), false), -1);
        }
    }

    public boolean isHoveringOver(double mouseX, double mouseY) {
        mouseX /= (ThinGL.windowInterface().getFramebufferWidth() / 1920F);
        mouseY /= (ThinGL.windowInterface().getFramebufferHeight() / 1080f);

        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}