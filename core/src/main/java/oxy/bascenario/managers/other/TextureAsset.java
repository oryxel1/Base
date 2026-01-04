package oxy.bascenario.managers.other;

import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;

@RequiredArgsConstructor
public class TextureAsset {
    private final byte[] bytes;
    private Texture2D texture2D;

    public Texture2D get() {
        if (this.texture2D == null) {
            this.texture2D = Texture2D.fromImage(bytes);
        }

        return this.texture2D;
    }
}
