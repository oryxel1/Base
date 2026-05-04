package oxy.bascenario.managers.other;

import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import org.lwjgl.opengl.GL20;

@RequiredArgsConstructor
public class TextureAsset {
    private final byte[] bytes;
    private Texture2D texture2D;

    public Texture2D get(boolean filter) {
        if (this.texture2D == null) {
            this.texture2D = Texture2D.fromImage(bytes);
            if (filter) {
                this.texture2D.setFilter(GL20.GL_NEAREST);
            }
        }

        return this.texture2D;
    }
}
