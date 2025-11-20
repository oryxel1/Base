package oxy.bascenario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.wrapper.GLStateManager;
import org.joml.Matrix4fStack;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.utils.FontUtils;
import oxy.bascenario.utils.ThinGLUtils;

@RequiredArgsConstructor
public final class EngineRenderer extends Game {
    private final Screen screen;

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

        FontUtils.loadFonts();

        this.setScreen(screen);
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
