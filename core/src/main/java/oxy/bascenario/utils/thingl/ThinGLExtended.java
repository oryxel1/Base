package oxy.bascenario.utils.thingl;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.program.Programs;
import net.raphimc.thingl.gl.wrapper.GLStateManager;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;

public class ThinGLExtended extends ThinGL {
    public ThinGLExtended(long windowHandle) {
        super(new GLFWWindowInterface(windowHandle));
    }

    public static ThinGLExtended get() {
        return (ThinGLExtended) ThinGL.get();
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
