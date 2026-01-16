package oxy.bascenario.utils.thingl.shaders;

import net.raphimc.thingl.gl.program.post.MultiPassAuxInputPostProcessingProgram;
import net.raphimc.thingl.gl.resource.shader.Shader;

public class OutlineGlowProgram extends MultiPassAuxInputPostProcessingProgram {
    public OutlineGlowProgram(final Shader vertexShader, final Shader fragmentShader) {
        super(vertexShader, fragmentShader, 2);
    }

    public void configureParameters(final int width) {
        this.setUniformInt("u_Width", width);
    }
}
