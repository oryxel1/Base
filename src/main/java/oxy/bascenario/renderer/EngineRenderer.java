package oxy.bascenario.renderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.utils.ScreenUtils;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import org.joml.Matrix4fStack;
import oxy.bascenario.renderer.config.RendererConfig;
import oxy.bascenario.renderer.util.SimpleWindowInterface;
import oxy.bascenario.renderer.util.ThinGLUtil;

@RequiredArgsConstructor
public final class EngineRenderer extends Game {
    private final RendererConfig config;

    @Override
    public void create() {
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();

        // Setup ThinGL, since we use this library for the main rendering logic.
        new ThinGL(new SimpleWindowInterface(windowHandle));
        ThinGL.config().setRestoreVertexArrayBinding(true);
        ThinGL.config().setRestoreProgramBinding(true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1, true);
        ThinGLUtil.GLOBAL_RENDER_STACK = new Matrix4fStack(8);
        super.render();
    }
}
