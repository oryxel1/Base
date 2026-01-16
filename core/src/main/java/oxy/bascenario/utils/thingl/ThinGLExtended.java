package oxy.bascenario.utils.thingl;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.program.Programs;
import net.raphimc.thingl.gl.resource.shader.Shader;
import net.raphimc.thingl.gl.wrapper.GLStateManager;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import java.util.HashMap;
import java.util.Map;

public class ThinGLExtended extends ThinGL {
    private static final Map<String, Shader> shaders = new HashMap<>();

    public ThinGLExtended(long windowHandle) {
        super(new GLFWWindowInterface(windowHandle));
    }

    @Override
    protected GLStateManager createGLStateManager() {
        return new GLStateManager();
    }

    @Override
    protected Programs createPrograms() {
        return new ProgramsExtended();
    }

    @Override
    public ProgramsExtended getPrograms() {
        return (ProgramsExtended) super.getPrograms();
    }
}
