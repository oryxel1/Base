package com.bascenario.render.manager;

import com.bascenario.render.manager.api.TextureKey;
import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.resource.image.texture.Texture2D;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureManager {
    @Getter
    private static final TextureManager instance = new TextureManager();
    private TextureManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }
    }

    private final Map<String, TextureKey> textures = new HashMap<>();

    public void loadTexture(String path, final InputStream stream) {
        try {
            this.textures.put(path, new TextureKey(path, Texture2D.fromImage(stream.readAllBytes())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTexture(String path, final File file) {
        try {
            this.textures.put(path, new TextureKey(path, Texture2D.fromImage(Files.readAllBytes(file.toPath()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Texture2D getTexture(File file) {
        if (!this.textures.containsKey(file.getPath())) {
            loadTexture(file.getPath(), file);
        }

        return this.textures.get(file.getPath()).key();
    }

    public Texture2D getTexture(String path) {
        if (!this.textures.containsKey(path)) {
            loadTexture(path, Objects.requireNonNull(TextureManager.class.getResourceAsStream(path)));
        }

        return this.textures.get(path).key();
    }
}
