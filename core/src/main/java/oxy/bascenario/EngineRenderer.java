package oxy.bascenario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.wrapper.GLStateManager;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

@RequiredArgsConstructor
public final class EngineRenderer extends Game {
    private final Screen initialScreen;

    private double mouseX, mouseY;
    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        // Setup ThinGL, since we use this library for the main rendering logic.
        new ThinGL(new GLFWWindowInterface(windowHandle)) {
            @Override
            protected GLStateManager createGLStateManager() {
                return new GLStateManager();
            }
        };
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);

        GLFW.glfwSetCursorPosCallback(windowHandle, (window, x, y) -> {
            if (window != windowHandle) {
                return;
            }

            this.mouseX = x;
            this.mouseY = y;
        });
        GLFW.glfwSetMouseButtonCallback(windowHandle, (window, button, action, mode) -> {
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

        this.setScreen(this.initialScreen);
    }

    @Override
    public void render() {
        AudioManager.getInstance().tick();

        ScreenUtils.clear(0, 0, 0, 1, true);
        ThinGLUtils.GLOBAL_RENDER_STACK = new Matrix4fStack(8);
        float x = ThinGL.windowInterface().getFramebufferWidth() / 1920F;
        ThinGLUtils.GLOBAL_RENDER_STACK.scale(x, ThinGL.windowInterface().getFramebufferHeight() / 1080F, x);
        super.render();
    }
}
