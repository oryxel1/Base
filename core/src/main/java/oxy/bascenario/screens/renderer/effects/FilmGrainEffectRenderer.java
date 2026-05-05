package oxy.bascenario.screens.renderer.effects;

import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.utils.thingl.other.TilingSprite;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class FilmGrainEffectRenderer {
    private long time;

    private TilingSprite noise2, noise1;
    private int x, y;

    public void init() {
        this.noise2 = new TilingSprite(Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_SCN_Noise_02.png"));
        this.noise2.width = 1920;
        this.noise2.height = 1080;
        this.noise2.scale = 7;

        this.noise1 = new TilingSprite(Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_SCN_Noise_01.png"));
        this.noise1.width = 1920;
        this.noise1.height = 1080;
        this.noise1.scale = 7;
    }

    public void render() {
        if (System.currentTimeMillis() - time > 90) {
            this.noise2.setTilePosition(RandomUtils.randomInt(0, 1024), RandomUtils.randomInt(0, 1024));
            this.noise1.setTilePosition(RandomUtils.randomInt(0, 1024), RandomUtils.randomInt(0, 1024));
            time = System.currentTimeMillis();
        }

        this.noise2.render(0, 0);
        this.noise1.render(0, 0);

        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, Color.fromRGB(255, 247, 167).withAlphaF(0.15f));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_GT_Circle_Blur_inv.png"), 0, 0, 1920, 1080, Color.BLACK.withAlphaF(0.3f));
    }
}
