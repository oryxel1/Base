package oxy.bascenario.utils;

import lombok.experimental.UtilityClass;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.utils.thingl.ThinGLUtils;

@UtilityClass
public class ScreenUtils {
    public static Integer SPOOFED_HEIGHT, SPOOFED_WIDTH;

    public int width() {
        return SPOOFED_WIDTH == null ? ThinGL.windowInterface().getFramebufferWidth() : SPOOFED_WIDTH;
    }

    public int height() {
        return SPOOFED_HEIGHT == null ? ThinGL.windowInterface().getFramebufferHeight() : SPOOFED_HEIGHT;
    }

    public float globalScale() {
        return (width() + height()) / (1920f + 1080);
    }

    public float widthScale() {
        return width() / 1920f;
    }

    public void legacyScale(Runnable runnable) {
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
        ThinGLUtils.GLOBAL_RENDER_STACK.pushMatrix();
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(x, ThinGL.windowInterface().getFramebufferHeight() / 1080F, 0);

        runnable.run();

        ThinGLUtils.GLOBAL_RENDER_STACK.popMatrix();
    }
}
