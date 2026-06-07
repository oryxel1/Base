package oxy.bascenario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.lenni0451.commons.collections.Maps;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.thingl.ThinGLBackend;
import net.lenni0451.rivet.backend.thingl.render.ThinGLRenderer;
import net.lenni0451.rivet.backend.thingl.text.ThinGLFont;
import net.lenni0451.rivet.backend.thingl.util.GLFWMapper;
import net.lenni0451.rivet.input.keyboard.CharEvent;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.input.mouse.MouseScrollEvent;
import net.lenni0451.rivet.layout.fullsize.FullSizeLayout;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.resource.font.face.impl.FreeTypeFontFace;
import net.raphimc.thingl.resource.font.instance.FontInstance;
import net.raphimc.thingl.resource.font.instance.FontInstanceSet;
import net.raphimc.thingl.resource.font.instance.impl.FreeTypeFontInstance;
import net.raphimc.thingl.text.util.GlyphPredicate;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.utils.*;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.thingl.ThinGLExtended;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import java.util.EnumSet;
import java.util.Set;

@RequiredArgsConstructor
public final class EngineRenderer extends Game {
    private ThinGLBackend backend;
    private Rivet rivet;

    private final Screen initialScreen;
    private final boolean initialFullScreen;

    public double mouseX, mouseY;

    @SneakyThrows
    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        // Setup ThinGL, since we use this library for the main rendering logic.
        new ThinGLExtended(windowHandle);
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);

//        GLFW.glfwSetWindowAspectRatio(windowHandle, 16, 9);

        GLFW.glfwSetCursorPosCallback(windowHandle, (window, x, y) -> {
            float[] mouseScale = this.getMouseScale();
            this.rivet.onMouseMove(new MouseMoveEvent((float) x * mouseScale[0], (float) y * mouseScale[1], this.heldMouseButtons));

            if (window != windowHandle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
            float[] mouseScale = this.getMouseScale();
            final double[] xpos = new double[1];
            final double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);

            MouseButtonEvent event = GLFWMapper.mapMouseButton((float) xpos[0] * mouseScale[0], (float) ypos[0] * mouseScale[1], button, mode);
            if (event != null) {
                if (action == GLFW.GLFW_PRESS) {
                    this.heldMouseButtons.add(event.button());
                    this.rivet.onMouseDown(event);
                } else if (action == GLFW.GLFW_RELEASE) {
                    this.rivet.onMouseUp(event);
                    this.heldMouseButtons.add(event.button());
                }
            }

            if (window != windowHandle || !(this.screen instanceof ExtendableScreen extendableScreen)) {
                return;
            }

            if (action == 1) {
                float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
                float y = ThinGL.windowInterface().getFramebufferHeight() / 1080f;

                extendableScreen.mouseClicked(this.mouseX / x, this.mouseY / y, button);
            } else {
                extendableScreen.mouseRelease();
            }
        });

        FontUtils.loadFonts();

        final FontInstance rivetFont = FontUtils.font("SFUIRegular");
        this.backend = new ThinGLBackend(windowHandle, new ThinGLFont(new FontInstanceSet(Maps.linkedHashMap(rivetFont, GlyphPredicate.all()))));
        this.rivet = new Rivet(this.backend, FullSizeLayout.INSTANCE, new Size(ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight()));

        setupRivetCallbacks();

        this.setScreen(this.initialScreen);

        if (this.initialFullScreen) {
            someTempHackyBool = true;
        }
    }

    @Override
    public void setScreen(Screen screen) {
        TimeUtils.fakeTimeMillis = null;
        super.setScreen(screen);

        if (screen instanceof ExtendableScreen extendableScreen) {
            extendableScreen.init(this.rivet);
        }
    }

    private boolean someTempHackyBool;

    private boolean fullScreen;
    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1, true);

        AudioManager.getInstance().tick();

        ThinGLUtils.GLOBAL_RENDER_STACK = new Matrix4fStack(8);

        ThinGLUtils.start();
        if (screen != null && screen instanceof ExtendableScreen extendableScreen) {
            extendableScreen.renderBehindRivet();
        }

        ThinGL.programs().getMsaa().bindInput();
        ThinGLRenderer.renderList(ThinGLUtils.GLOBAL_RENDER_STACK, this.rivet.render());
        ThinGL.programs().getMsaa().unbindInput();
        ThinGL.programs().getMsaa().renderFullscreen();
        ThinGL.programs().getMsaa().clearInput();
        ThinGLUtils.end();

        ThinGLUtils.GLOBAL_RENDER_STACK = new Matrix4fStack(8);
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(x, ThinGL.windowInterface().getFramebufferHeight() / 1080F, x);

        super.render();

        if (/*ImGui.isKeyReleased(ImGuiKey.F11) || */ someTempHackyBool) {
            if (this.fullScreen) {
                Gdx.graphics.setUndecorated(false);
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setUndecorated(true);
                Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height + 1); // Height needs to be plus 1 to fix flickering
            }
            this.fullScreen = !this.fullScreen;
            this.someTempHackyBool = false;
        }
    }

    private final Set<MouseButton> heldMouseButtons = EnumSet.noneOf(MouseButton.class);
    private void setupRivetCallbacks() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        GLFW.glfwSetScrollCallback(windowHandle, (window, xoffset, yoffset) -> {
            float[] mouseScale = this.getMouseScale();
            final double[] xpos = new double[1];
            final double[] ypos = new double[1];
            GLFW.glfwGetCursorPos(window, xpos, ypos);
            this.rivet.onMouseScroll(new MouseScrollEvent((float) xpos[0] * mouseScale[0], (float) ypos[0] * mouseScale[1], (float) xoffset, (float) yoffset));
        });
        GLFW.glfwSetKeyCallback(windowHandle, (_, key, _, action, mods) -> {
            KeyEvent event = GLFWMapper.mapKey(key, mods);
            if (event != null) {
                if (action == GLFW.GLFW_PRESS) {
                    this.rivet.onKeyDown(event);
                } else if (action == GLFW.GLFW_RELEASE) {
                    this.rivet.onKeyUp(event);
                } else if (action == GLFW.GLFW_REPEAT) {
                    this.rivet.onKeyDown(event);
                }
            }
        });
        GLFW.glfwSetCharCallback(windowHandle, (_, codepoint) -> {
            if (Character.isBmpCodePoint(codepoint)) {
                this.rivet.onCharTyped(new CharEvent((char) codepoint));
            } else if (Character.isValidCodePoint(codepoint)) {
                this.rivet.onCharTyped(new CharEvent(Character.highSurrogate(codepoint)));
                this.rivet.onCharTyped(new CharEvent(Character.lowSurrogate(codepoint)));
            }
        });
        GLFWFramebufferSizeCallback[] oldCallback = new GLFWFramebufferSizeCallback[1];
        oldCallback[0] = GLFW.glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
            if (oldCallback[0] != null) oldCallback[0].invoke(window, width, height);
            this.rivet.size(new Size(width, height));
        });
        GLFW.glfwSetWindowFocusCallback(windowHandle, (window, focused) -> {
            if (!focused) {
                this.heldMouseButtons.clear();
                this.rivet.unfocus();
            }
        });
    }

    private float[] getMouseScale() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        int[] windowSizeX = new int[1];
        int[] windowSizeY = new int[1];
        GLFW.glfwGetWindowSize(windowHandle, windowSizeX, windowSizeY);
        int[] framebufferSizeX = new int[1];
        int[] framebufferSizeY = new int[1];
        GLFW.glfwGetFramebufferSize(windowHandle, framebufferSizeX, framebufferSizeY);
        return new float[]{(float) framebufferSizeX[0] / windowSizeX[0], (float) framebufferSizeY[0] / windowSizeY[0]};
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 1920 && height <= 1080) {
            this.rivet.scale(1);
        } else {
            float scale = (width + height) / (1920 + 1080f);
            this.rivet.scale(scale);
        }

        super.resize(width, height);
    }
}
