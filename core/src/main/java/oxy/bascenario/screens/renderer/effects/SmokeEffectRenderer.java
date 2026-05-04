package oxy.bascenario.screens.renderer.effects;

import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;
import oxy.bascenario.utils.thingl.other.TilingSprite;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class SmokeEffectRenderer {
    private TilingSprite sprite, sprite1;

    public void init() {
        sprite = new TilingSprite(Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_Smoke_Scroll_23.png"));
        sprite.scale = 4;
        sprite.color = 0x4c413f;
        sprite.setTilePosition(0, 240);
        sprite.rotation = 0.55f;

        sprite1 = new TilingSprite(Base.instance().assetsManager().texture("assets/base/uis/effects/FX_TEX_Smoke_Scroll_23.png"));

        sprite1.scale = 4;
        sprite1.color = 0x4c413f;
        sprite1.setTilePosition(0, 240);
        sprite1.rotation = -0.35f;

        sprite1.width = 2500;
        sprite1.height = 800;

        sprite.width = 2000;
        sprite.height = 512;
    }

    private final Queue<Particle> particles = new ConcurrentLinkedQueue<>();

    private long time = System.currentTimeMillis();
    public void render(boolean add) {
        if (System.currentTimeMillis() - time > 80 && add) {
            particles.add(new Particle());
            time = System.currentTimeMillis();
        }

        if (add) {
            sprite1.render(-400, 1080 - 100 * 1.6f);
            sprite1.addTileOffset(1, 0);

            sprite.render(0, 1080 - 512 * 1.6f);
            sprite.addTileOffset(1, 0);
        }

        particles.forEach(Particle::render);
        particles.removeIf(Particle::remove);
    }

    private static class Particle {
        private final int size;
        private float x, y;
        private final int direction;
        private final int speedY, speedX;

        public Particle() {
            this.size = RandomUtils.randomInt(10, 30);

            this.x = 0;
            this.y = 1080 - size;
            this.speedY = RandomUtils.randomInt(10, 20);
            this.speedX = RandomUtils.randomInt(10, 40);

            this.direction = RandomUtils.randomInt(0, 2);
        }

        public void render() {
            this.y -= speedY;
            this.x += (this.direction == 0 ? -this.speedX : this.direction == 1 ? this.speedX : 0);

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/uis/effects/dust_smoke.png"),
                    x, y, size, size, Color.WHITE);
        }

        public boolean remove() {
            return y < 0;
        }
    }
}
