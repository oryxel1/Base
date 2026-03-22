package oxy.bascenario.screens.renderer.weather;

import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.Base;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class SnowRenderer {
    private final Queue<SnowDrop> snows = new ConcurrentLinkedQueue<>();

    private long time = System.currentTimeMillis();
    public void render(boolean add) {
        if (System.currentTimeMillis() - time > 200 && add) {
            snows.add(new SnowDrop(RandomUtils.randomInt(20, 120), RandomUtils.randomInt(0, 1920), RandomUtils.randomInt(1, 4)));
            time = System.currentTimeMillis();
        }

        snows.forEach(SnowDrop::render);
        snows.removeIf(SnowDrop::remove);
    }

    private static class SnowDrop {
        private final int size;
        private float x, y;
        private final int direction;
        private final int speedY;

        public SnowDrop(int size, float x, int speedY) {
            this.size = size;
            this.x = x;
            this.y = -size;
            this.speedY = speedY;

            this.direction = RandomUtils.randomInt(0, 2);
        }

        public void render() {
            this.y += speedY;
            this.x += (this.direction == 0 ? -2 : this.direction == 1 ? 2 : 0);

            ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK,
                    Base.instance().assetsManager().texture("assets/base/snow.png"),
                    x, y, size, size, Color.WHITE.withAlphaF(0.4f));
        }

        public boolean remove() {
            return y > 1080;
        }
    }
}
