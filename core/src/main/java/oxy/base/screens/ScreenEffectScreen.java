package oxy.base.screens;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.base.Base;
import oxy.base.api.effects.ScreenEffect;
import oxy.base.screens.renderer.effects.FilmGrainEffectRenderer;
import oxy.base.screens.renderer.effects.ShiningEffectRenderer;
import oxy.base.screens.renderer.effects.SmokeEffectRenderer;
import oxy.base.screens.renderer.effects.TvNoiseEffectRenderer;
import oxy.base.utils.ExtendableScreen;

import java.util.HashSet;
import java.util.Set;

import static oxy.base.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ScreenEffectScreen extends ExtendableScreen {
    @Getter
    private final Set<ScreenEffect> effects = new HashSet<>();

    private final SmokeEffectRenderer smokeEffectRenderer = new SmokeEffectRenderer();
    private final ShiningEffectRenderer shiningEffectRenderer = new ShiningEffectRenderer();
    private final FilmGrainEffectRenderer filmGrainEffectRenderer = new FilmGrainEffectRenderer();
    private final TvNoiseEffectRenderer tvNoiseEffectRenderer = new TvNoiseEffectRenderer();

    @Override
    public void show() {
        this.smokeEffectRenderer.init();
        this.filmGrainEffectRenderer.init();
        this.tvNoiseEffectRenderer.init();
    }

    protected void renderSmoke() {
        this.smokeEffectRenderer.render(effects.contains(ScreenEffect.SMOKE));
    }

    @Override
    public void render(float delta) {
        // Don't comment on this.
        if (effects.contains(ScreenEffect.GRAY_FILTER)) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_GT_Circle_Blur_inv.png"), 0, 0, 1920, 1080, Color.BLACK.withAlphaF(0.4f));
        }

        if (effects.contains(ScreenEffect.SHINING) || effects.contains(ScreenEffect.SHINING_NO_BG)) {
            shiningEffectRenderer.render(effects.contains(ScreenEffect.SHINING));
        }

        if (effects.contains(ScreenEffect.FILM_GRAIN) || effects.contains(ScreenEffect.FILM_GRAIN_NO_TONE)) {
            filmGrainEffectRenderer.render(effects.contains(ScreenEffect.FILM_GRAIN));
        }

        if (effects.contains(ScreenEffect.TV_NOISE)) {
            tvNoiseEffectRenderer.render();
        }
    }
}
