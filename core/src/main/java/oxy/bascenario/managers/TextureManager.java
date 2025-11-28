package oxy.bascenario.managers;

import lombok.Getter;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.managers.other.TextureKey;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureManager {
    private static TextureKey INVALID_TEXTURE;

    @Getter
    private static final TextureManager instance = new TextureManager();
    private TextureManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }

        try {
            INVALID_TEXTURE = new TextureKey("invalid_texture", Texture2D.fromImage(Objects.requireNonNull(TextureManager.class.getResourceAsStream("/assets/base/invalid.png")).readAllBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    private final Map<String, TextureKey> textures = new HashMap<>();

    public void loadTexture(String path, final InputStream stream) {
        try {
            this.textures.put(path, new TextureKey(path, Texture2D.fromImage(stream.readAllBytes())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTexture(String path, final File file) throws IOException {
        this.textures.put(path, new TextureKey(path, Texture2D.fromImage(Files.readAllBytes(file.toPath()))));
    }

    public Texture2D getTexture(FileInfo file) {
        return file.internal() ? getTexture(file.path()) : getTexture(new File(file.path()));
    }

    public Texture2D getTexture(File file) {
        if (!this.textures.containsKey(file.getPath())) {
            try {
                loadTexture(file.getPath(), file);
            } catch (IOException exception) {
                return INVALID_TEXTURE.key();
            }
        }

        return this.textures.get(file.getPath()).key();
    }

    public Texture2D getTexture(String path) {
        if (!this.textures.containsKey(path)) {
            loadTexture(path, Objects.requireNonNull(TextureManager.class.getResourceAsStream("/" + path)));
        }

        return this.textures.get(path).key();
    }
}