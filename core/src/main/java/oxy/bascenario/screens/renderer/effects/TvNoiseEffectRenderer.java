package oxy.bascenario.screens.renderer.effects;

import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.utils.thingl.other.TilingSprite;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class TvNoiseEffectRenderer {
    private long time;

    private TilingSprite noise;

    public void init() {
        this.noise = new TilingSprite(Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_Noise_42.png"));
        this.noise.width = 1920;
        this.noise.height = 1080;
    }

    public void render() {
        if (System.currentTimeMillis() - time > 90) {
            this.noise.setTilePosition(RandomUtils.randomInt(0, 512), RandomUtils.randomInt(0, 512));
            time = System.currentTimeMillis();
        }

        this.noise.thecorrectone(0, 0);
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_GT_Circle_Blur_inv.png"), 0, 0, 1920, 1080, Color.BLACK.withAlphaF(0.4f));
    }
}
