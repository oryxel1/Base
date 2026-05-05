package oxy.bascenario.screens.renderer.effects;

import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.animation.DynamicAnimation;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class ShiningEffectRenderer {
    private final Queue<FlareRing> flareRings = new ConcurrentLinkedQueue<>();
    private final Queue<Flare> flares = new ConcurrentLinkedQueue<>();

    private long time = System.currentTimeMillis(), time1;
    public void render(boolean background) {
        if (System.currentTimeMillis() - time > 800 && this.flareRings.size() < 20) {
            this.flareRings.add(new FlareRing());
            time = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - time1 > 200) {
            this.flares.add(new Flare());
            time1 = System.currentTimeMillis();
        }

        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_GT_Circle_Blur_inv.png"),
                0, 0, 1920, 1080, Color.fromRGB(213, 159, 251).withAlphaF(background ? 0.8f : 0.2f));

        flareRings.forEach(FlareRing::render);
        flareRings.removeIf(FlareRing::remove);

        flares.forEach(Flare::render);
        flares.removeIf(Flare::remove);
    }

    private static class Flare {
        private final int x, y;
        private final DynamicAnimation scale;

        private Flare() {
            this.x = RandomUtils.randomInt(0, 1920);
            this.y = RandomUtils.randomInt(0, 1080);

            int initialScale = RandomUtils.randomInt(100, 200);
            this.scale = AnimationUtils.build(400, initialScale, RandomUtils.randomInt(initialScale, 280), EasingFunction.LINEAR);
        }

        public void render() {
            if (!this.scale.isRunning() && this.scale.getTarget() != 0) {
                this.scale.setTarget(0);
            }

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_Flare_03_I.png"),
                    this.x - this.scale.getValue() / 2,
                    this.y - this.scale.getValue() / 2, this.scale.getValue(), this.scale.getValue(), Color.WHITE
            );
        }

        public boolean remove() {
            return !this.scale.isRunning() && this.scale.getTarget() == 0;
        }
    }

    private static class FlareRing {
        private final int x, y;
        private final DynamicAnimation scale;
        private DynamicAnimation opacity;

        private FlareRing() {
            this.x = RandomUtils.randomInt(0, 1920);
            this.y = RandomUtils.randomInt(0, 1080);

            int initialScale = RandomUtils.randomInt(400, 500);
            this.scale = AnimationUtils.build(4000, initialScale, RandomUtils.randomInt(initialScale, 1000), EasingFunction.SINE);
            this.opacity = AnimationUtils.build(2000, 0, 0.8f, EasingFunction.SINE);
        }

        public void render() {
            if (!this.opacity.isRunning() && this.opacity.getTarget() == 0.8f) {
                this.opacity.setTarget(0);
            }

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_SCN_Ring_02.png"),
                    this.x - this.scale.getValue() / 2,
                    this.y - this.scale.getValue() / 2, this.scale.getValue(), this.scale.getValue(), Color.WHITE.withAlphaF(this.opacity.getValue())
            );
        }

        public boolean remove() {
            return !this.opacity.isRunning() && this.opacity.getTarget() == 0;
        }
    }
}
