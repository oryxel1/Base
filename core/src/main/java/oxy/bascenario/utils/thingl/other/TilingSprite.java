package oxy.bascenario.utils.thingl.other;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.math.MathUtils;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.api.utils.math.Vec2;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

@RequiredArgsConstructor
public class TilingSprite {
    private final Texture2D texture2D;
    public float width, height;

    private Vec2 tilePosition = new Vec2(0, 0);
    public float scale = 1;
    public int color = Color.WHITE.toRGB();
    public float rotation = 0;

    public void render(float x, float y) {
        final float textureWidth = texture2D.getWidth(), textureHeight = texture2D.getHeight();

        int totalTimesX = MathUtils.ceilInt(width / textureWidth);
        int totalTimesY = MathUtils.ceilInt(height / textureHeight);

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.rotateZ(rotation);
//        ThinGL.scissorStack().pushOverwrite(x, y, x + width, y + height);
        for (int i = 0; i < totalTimesX; i++) {
            for (int n = 0; n < totalTimesY; n++) {
                float tileX = x + i * textureWidth, tileY = y + n * textureHeight;
                GLOBAL_RENDER_STACK.translate(tileX, tileY, 0);
                GLOBAL_RENDER_STACK.scale(scale);

                ThinGL.renderer2D().coloredTextureWithRawTexCoord(GLOBAL_RENDER_STACK,
                        texture2D, 0, 0, textureWidth - tilePosition.x(), textureHeight,
                        tilePosition.x() / textureWidth, tilePosition.y() / textureHeight,
                        (textureWidth - tilePosition.x()) / textureWidth, 1, Color.fromRGB(color));

                ThinGL.renderer2D().coloredTextureWithRawTexCoord(GLOBAL_RENDER_STACK,
                        texture2D, (textureWidth - tilePosition.x()), 0, tilePosition.x(), textureHeight,
                        0, tilePosition.y() / textureHeight,
                        tilePosition.x() / textureWidth, 1, Color.fromRGB(color));

                GLOBAL_RENDER_STACK.scale(-scale);
                GLOBAL_RENDER_STACK.translate(-tileX, -tileY, 0);
            }
        }
//        ThinGL.scissorStack().pop();
        GLOBAL_RENDER_STACK.popMatrix();
    }

    public void setTilePosition(float x, float y) {
        if (Math.abs(x) > texture2D.getWidth()) {
            float newX = Math.abs(x);
            while (newX > texture2D.getWidth()) {
                newX -= texture2D.getWidth();
            }
            x = newX * Math.signum(x);
        }

        if (Math.abs(y) > texture2D.getHeight()) {
            float newY = Math.abs(y);
            while (newY > texture2D.getHeight()) {
                newY -= texture2D.getHeight();
            }
            y = newY * Math.signum(y);
        }

        this.tilePosition = new Vec2(x, y);
    }

    public void addTileOffset(float x, float y) {
        setTilePosition(this.tilePosition.x() + x, this.tilePosition.y() + y);
    }
}
