package oxy.bascenario.renderer.util;

import lombok.experimental.UtilityClass;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.util.DefaultGLStates;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11C;

@UtilityClass
public final class ThinGLUtil {
    public Matrix4fStack GLOBAL_RENDER_STACK;

    public void render(final Runnable runnable) {
        ThinGL.globalUniforms().getViewMatrix().pushMatrix().identity();
        // Pretty hardcoded? yes I do know that.
        ThinGL.globalUniforms().getProjectionMatrix().pushMatrix().setOrtho(0F, 1920, 1080, 0F, -1000F, 1000F);
        DefaultGLStates.push();
        ThinGL.glStateStack().disable(GL11C.GL_DEPTH_TEST);
        runnable.run();
        ThinGL.glStateStack().enable(GL11C.GL_DEPTH_TEST);
        DefaultGLStates.pop();
        ThinGL.globalUniforms().getProjectionMatrix().popMatrix();
        ThinGL.globalUniforms().getViewMatrix().popMatrix();
    }
}
