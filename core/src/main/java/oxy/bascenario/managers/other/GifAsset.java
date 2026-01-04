package oxy.bascenario.managers.other;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.raphimc.thingl.gl.texture.animated.SequencedTexture;
import net.raphimc.thingl.image.animated.impl.AwtGifImage;

@RequiredArgsConstructor
public class GifAsset {
    private final byte[] bytes;
    private SequencedTexture texture;

    @SneakyThrows
    public SequencedTexture get() {
        if (texture == null) {
            texture = new SequencedTexture(new AwtGifImage(bytes));
        }

        return texture;
    }
}
