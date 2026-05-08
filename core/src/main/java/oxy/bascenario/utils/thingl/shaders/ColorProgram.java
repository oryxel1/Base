package oxy.bascenario.utils.thingl.shaders;

import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.program.post.AuxInputPostProcessingProgram;
import net.raphimc.thingl.gl.resource.shader.Shader;
import net.raphimc.thingl.gl.wrapper.Blending;

public class ColorProgram extends AuxInputPostProcessingProgram {

    public ColorProgram(final Shader vertexShader, final Shader fragmentShader) {
        super(vertexShader, fragmentShader);
    }

    @Override
    protected void prepareAndRenderInternal(final float xtl, final float ytl, final float xbr, final float ybr) {
        ThinGL.glStateStack().pushBlendFunc();
        Blending.premultipliedAlphaBlending();
        super.prepareAndRenderInternal(xtl, ytl, xbr, ybr);
        ThinGL.glStateStack().popBlendFunc();
    }
}
