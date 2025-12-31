package oxy.bascenario.utils;

import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.color.ColorUtils;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.gl.util.DefaultGLStates;
import net.raphimc.thingl.text.TextRun;
import org.joml.Matrix4fStack;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11C;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.utils.math.Axis;

import java.util.Map;

public final class ThinGLUtils {
    public static Matrix4fStack GLOBAL_RENDER_STACK;

    public static void renderBackground(final Matrix4fStack positionMatrix, Texture2D texture2D, Color color) {
        Vector4f vec = MathUtils.findBackgroundRender(new Vector2f(1920, 1080), new Vector2f(texture2D.getWidth(), texture2D.getHeight()));
        ThinGL.renderer2D().coloredTexture(positionMatrix, texture2D, vec.x, vec.y, vec.z, vec.w, color);
    }

    public static void renderEffect(final Runnable runnable, final Map<Effect, Object[]> effects) {
        effects.forEach((k, v) -> {
            switch (k) {
                case HOLOGRAM -> {
                    ThinGL.programs().getColorTweak().bindInput();
                    runnable.run();
                    ThinGL.programs().getColorTweak().unbindInput();

                    ThinGL.programs().getColorTweak().configureParameters(Color.fromRGBA(30, 97, 205, 60));
                    ThinGL.programs().getColorTweak().render(0, 0, 1920, 1080);
                    ThinGL.programs().getColorTweak().configureParameters(Color.GRAY.withAlphaF(0.2f));
                    if (v[0] == Axis.X) {
                        for (int x = (int) -(1920 * 20f); x < 0; x += 200) {
                            long index = TimeUtils.currentTimeMillis() + x;
                            index %= (long) (1920 * 20f);

                            ThinGL.programs().getColorTweak().render((index / 20f), 0, (index / 20f) + 4, 1080);
                        }
                    } else {
                        for (int y = (int) -(1080 * 20f); y < 0; y += 200) {
                            long index = TimeUtils.currentTimeMillis() + y;
                            index %= (long) (1080 * 20f);

                            ThinGL.programs().getColorTweak().render(0, (index / 20f), 1920, (index / 20f) + 4);
                        }
                    }

                    ThinGL.programs().getColorTweak().clearInput();
                }

                case RAINBOW -> {
                    // Yes I do know there is a rainbow shader in ThinGL, but this required less input sooooo.
                    ThinGL.programs().getColorTweak().bindInput();
                    runnable.run();
                    ThinGL.programs().getColorTweak().unbindInput();

                    float f = (Float) v[1];
                    if (f == 0) {
                        f = 1.0e-10f;
                    }
                    if (v[0] == Axis.X) {
                        for (int x = 0; x < 1920; x += 5) {
                            ThinGL.programs().getColorTweak().configureParameters(Color.fromRGB(ColorUtils.getRainbowColor(x, f).getRGB()).withAlphaF(0.3f));
                            ThinGL.programs().getColorTweak().render(x, 0, x + 5, 1080);
                        }
                    } else {
                        for (int y = 0; y < 1080; y += 5) {
                            ThinGL.programs().getColorTweak().configureParameters(Color.fromRGB(ColorUtils.getRainbowColor(y, f).getRGB()).withAlphaF(0.3f));
                            ThinGL.programs().getColorTweak().render(0, y, 1920, y + 5);
                        }
                    }

                    ThinGL.programs().getColorTweak().clearInput();
                }

                case BLUR -> {
                    ThinGL.programs().getGaussianBlur().bindInput();
                    runnable.run();
                    ThinGL.programs().getGaussianBlur().unbindInput();
                    ThinGL.programs().getGaussianBlur().configureParameters((Integer) v[0]);
                    ThinGL.programs().getGaussianBlur().render(0, 0,1920, 1080);
                    ThinGL.programs().getGaussianBlur().clearInput();
                }

                case OUTLINE -> {
                    ThinGL.programs().getOutline().bindInput();
                    runnable.run();
                    ThinGL.programs().getOutline().unbindInput();
                    ThinGL.programs().getOutline().configureParameters((Integer) v[0], (Integer) v[1]);
                    ThinGL.programs().getOutline().renderFullscreen();
                    ThinGL.programs().getOutline().clearInput();
                }
            }
        });
    }

    public static void render(final Runnable runnable) {
        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, 1920, 1080, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);

        runnable.run();

        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }

    public static void start() {
        final int width = ThinGL.windowInterface().getFramebufferWidth();
        final int height = ThinGL.windowInterface().getFramebufferHeight();

        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, width, height, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);
    }

    public static void end() {
        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }

    public static void blurRectangle(float x, float y, float width, float height, int strength) {
        ThinGL.programs().getGaussianBlur().bindInput();
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + width, y + height, Color.WHITE);
        ThinGL.programs().getGaussianBlur().unbindInput();
        ThinGL.programs().getGaussianBlur().configureParameters(strength);
        ThinGL.programs().getGaussianBlur().render(x, y, x + width, y + height);
        ThinGL.programs().getGaussianBlur().clearInput();
    }
}
