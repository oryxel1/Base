package oxy.base.managers.other;

import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import net.raphimc.thingl.resource.image.impl.AwtSvgByteImage2D;
import org.lwjgl.opengl.GL20;
import oxy.base.api.managers.other.AssetType;

@RequiredArgsConstructor
public class TextureAsset {
    private final byte[] bytes;
    private Texture2D texture2D;

    public Texture2D get(boolean filter) {
        return get(false, filter);
    }

    public Texture2D get(boolean svg, boolean filter) {
        if (this.texture2D == null) {
            this.texture2D = svg ? Texture2D.fromImage(new AwtSvgByteImage2D(bytes, 1)) : Texture2D.fromImage(bytes);
            if (filter) {
                this.texture2D.setFilter(GL20.GL_NEAREST);
            }
        }

        return this.texture2D;
    }
}
