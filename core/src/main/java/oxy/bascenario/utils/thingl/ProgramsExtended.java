package oxy.bascenario.utils.thingl;

import net.lenni0451.commons.lazy.Lazy;
import net.raphimc.thingl.gl.program.Programs;
import net.raphimc.thingl.gl.program.RegularProgram;
import net.raphimc.thingl.gl.program.post.impl.OutlineProgram;
import net.raphimc.thingl.gl.resource.shader.Shader;
import net.raphimc.thingl.util.GlSlPreprocessor;
import oxy.bascenario.utils.thingl.shaders.OutlineGlowProgram;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static net.raphimc.thingl.gl.resource.shader.Shader.Type.FRAGMENT;
import static net.raphimc.thingl.gl.resource.shader.Shader.Type.VERTEX;

public class ProgramsExtended extends Programs {
    private final Map<String, Shader> customShaders = new HashMap<>();

    private final Lazy<RegularProgram> sdfText = Lazy.of(() -> {
        final RegularProgram program = new RegularProgram(this.getShader("regular/sdf_text", VERTEX), this.getBaseShader("sdf_text", FRAGMENT));
        program.setDebugName("sdf_text");
        return program;
    });

    private final Lazy<OutlineGlowProgram> outlineGlow = Lazy.of(() -> {
        final OutlineGlowProgram program = new OutlineGlowProgram(this.getShader("post/post_processing", VERTEX), this.getBaseShader("outline_glow", FRAGMENT));
        program.setDebugName("outline_glow");
        return program;
    });

    public OutlineGlowProgram getOutlineGlow() {
        return outlineGlow.get();
    }

    @Override
    public RegularProgram getSdfText() {
        return sdfText.get();
    }

    private Shader getBaseShader(final String name, final Shader.Type type) {
        return customShaders.computeIfAbsent(name + "." + type.getFileExtension(), path -> {
            try {
                try (InputStream stream = ThinGLExtended.class.getClassLoader().getResourceAsStream("assets/base/shaders/" + path)) {
                    if (stream == null) {
                        throw new IOException("Shader " + name + " not found");
                    }
                    final String source = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    final GlSlPreprocessor preprocessor = new GlSlPreprocessor();
                    preprocessor.addDefines(Map.of());
                    final Shader shader = new Shader(type, preprocessor.process(source));
                    shader.setDebugName(name);
                    return shader;
                }
            } catch (Throwable e) {
                throw new RuntimeException("Failed to load shader " + name, e);
            }
        });
    }
}
