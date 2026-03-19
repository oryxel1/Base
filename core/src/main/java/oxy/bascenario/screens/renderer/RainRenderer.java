package oxy.bascenario.screens.renderer;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.RandomUtils;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class RainRenderer {
    private final Queue<RainDrop> drops = new ConcurrentLinkedQueue<>();

    public void render() {
        while (drops.size() < 10) {
            drops.add(new RainDrop(RandomUtils.randomInt(200, 1000), RandomUtils.randomInt(0, 1920), RandomUtils.randomInt(80, 200)));
        }

        drops.forEach(RainDrop::render);
        drops.removeIf(RainDrop::remove);
    }

    public static class RainDrop {
        private final float height;
        private final float x;
        private final float speed;
        private float y;

        private RainDrop(float height, float x, float speed) {
            this.height = height;
            this.x = x;
            this.speed = speed;
            this.y = -height;
        }

        public void render() {
            y += speed;

            ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, x, y, x + 5, y + height, Color.WHITE.withAlphaF(0.4f));
        }

        public boolean remove() {
            return y > 1080;
        }
    }
}
