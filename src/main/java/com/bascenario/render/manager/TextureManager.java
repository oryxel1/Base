package com.bascenario.render.manager;

import com.bascenario.render.manager.api.TextureKey;
import com.bascenario.util.RenderUtil;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
        final BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(stream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.textures.put(path, new TextureKey(path, RenderUtil.fromBufferedImage(image)));
    }

    public int getTexture(String path) {
        return this.textures.get(path).key();
    }
}
