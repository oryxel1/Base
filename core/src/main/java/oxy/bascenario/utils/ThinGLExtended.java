package oxy.bascenario.utils;

import net.lenni0451.commons.lazy.Lazy;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.program.Programs;
import net.raphimc.thingl.gl.program.RegularProgram;
import net.raphimc.thingl.gl.resource.shader.Shader;
import net.raphimc.thingl.gl.wrapper.GLStateManager;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.util.GlSlPreprocessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static net.raphimc.thingl.gl.resource.shader.Shader.Type.FRAGMENT;
import static net.raphimc.thingl.gl.resource.shader.Shader.Type.VERTEX;

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
        return new Programs() {
            private final Lazy<RegularProgram> sdfText = Lazy.of(() -> {
                final RegularProgram program = new RegularProgram(this.getShader("regular/sdf_text", VERTEX), ThinGLExtended.getShader("sdf_text", FRAGMENT));
                program.setDebugName("sdf_text");
                return program;
            });

            @Override
            public RegularProgram getSdfText() {
                return sdfText.get();
            }
        };
    }

    private static Shader getShader(final String name, final Shader.Type type) {
        return shaders.computeIfAbsent(name + "." + type.getFileExtension(), path -> {
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
