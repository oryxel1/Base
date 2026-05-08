package oxy.bascenario.utils.thingl;

import net.lenni0451.commons.lazy.Lazy;
import net.raphimc.thingl.gl.program.Programs;
import net.raphimc.thingl.gl.program.RegularProgram;
import net.raphimc.thingl.gl.resource.shader.Shader;
import net.raphimc.thingl.util.glsl.GlSlPreprocessor;
import oxy.bascenario.utils.thingl.shaders.ColorProgram;
import oxy.bascenario.utils.thingl.shaders.NightVisionProgram;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static net.raphimc.thingl.gl.resource.shader.Shader.Type.FRAGMENT;
import static net.raphimc.thingl.gl.resource.shader.Shader.Type.VERTEX;

public class ProgramsExtended extends Programs {
    private final Map<String, Shader> customShaders = new HashMap<>();

    private final Lazy<RegularProgram> sdfText = Lazy.of(() -> {
        final RegularProgram program = new RegularProgram(this.shaderLoader.get("regular/sdf_text", VERTEX), this.getBaseShader("sdf_text", FRAGMENT));
        program.setDebugName("sdf_text");
        return program;
    });

    private final Lazy<ColorProgram> grayscale = Lazy.of(() -> {
        final ColorProgram program = new ColorProgram(this.shaderLoader.get("post/post_processing", VERTEX), this.getBaseShader("black_and_white", FRAGMENT));
        program.setDebugName("black_and_white");
        return program;
    });

    private final Lazy<NightVisionProgram> nightVision = Lazy.of(() -> {
        final NightVisionProgram program = new NightVisionProgram(this.shaderLoader.get("post/post_processing", VERTEX), this.getBaseShader("night_vision", FRAGMENT));
        program.setDebugName("night_vision");
        return program;
    });

    @Override
    public RegularProgram getSdfText() {
        return sdfText.get();
    }

    public ColorProgram getGrayscale() {
        return grayscale.get();
    }

    public NightVisionProgram getNightVision() {
        return nightVision.get();
    }

    private Shader getBaseShader(final String name, final Shader.Type type) {
        return this.customShaders.computeIfAbsent(name + "." + type.getFileExtension(), path -> {
            final net.raphimc.thingl.util.glsl.GlSlPreprocessor preprocessor = new GlSlPreprocessor(this.getProcessedShaderSource("assets/base/shaders/" + path));
//            preprocessor.prependDefines(defines);
            final Shader shader = new Shader(type, preprocessor.getCode());
            shader.setDebugName(name);
            return shader;
        });
    }

    private String getProcessedShaderSource(final String path) {
        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Shader " + path + " not found");
            }
            final GlSlPreprocessor preprocessor = new GlSlPreprocessor(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
            preprocessor.resolveIncludes(includePath -> {
                final Path resolvedIncludePath = Paths.get(path).getParent().resolve(includePath).normalize();
                final String fullIncludePath = resolvedIncludePath.toString().replace(resolvedIncludePath.getFileSystem().getSeparator(), "/");
                final GlSlPreprocessor innerPreprocessor = new GlSlPreprocessor(this.getProcessedShaderSource(fullIncludePath));
                innerPreprocessor.addIncludeGuard(fullIncludePath);
                return innerPreprocessor.getCode();
            });
            return preprocessor.getCode();
        } catch (Throwable e) {
            throw new RuntimeException("Failed to process shader file: " + path, e);
        }
    }
}
