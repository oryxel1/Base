package oxy.bascenario.screens;

import lombok.Getter;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.api.effects.ScreenEffect;
import oxy.bascenario.screens.renderer.effects.SmokeEffectRenderer;
import oxy.bascenario.utils.ExtendableScreen;

import java.util.HashSet;
import java.util.Set;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ScreenEffectScreen extends ExtendableScreen {
    @Getter
    private final Set<ScreenEffect> effects = new HashSet<>();

    private final SmokeEffectRenderer smokeEffectRenderer = new SmokeEffectRenderer();

    @Override
    public void show() {
        this.smokeEffectRenderer.init();
    }

    @Override
    public void render(float delta) {
        this.smokeEffectRenderer.render(this.effects.contains(ScreenEffect.SMOKE));
        // Don't comment on this.
        if (effects.contains(ScreenEffect.GRAY_FILTER)) {
            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_GT_Circle_Blur_inv.png"), 0, 0, 1920, 1080, Color.BLACK.withAlphaF(0.4f));
        }
    }
}
