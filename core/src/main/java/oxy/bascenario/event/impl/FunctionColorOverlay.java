package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.event.impl.ColorOverlayEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.serializers.utils.GsonUtils;
import oxy.bascenario.utils.AnimationUtils;
import oxy.bascenario.utils.ThinGLUtils;

public class FunctionColorOverlay extends EventFunction<ColorOverlayEvent> {
    private DynamicAnimation fade = AnimationUtils.dummy(1);
    public FunctionColorOverlay(ColorOverlayEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        if (Fade.canFade(event.getIn())) {
            fade = AnimationUtils.build(event.getIn().duration(), 0, 1, EasingFunction.LINEAR);
        }
    }

    private long start = -1;

    @Override
    public void render(ScenarioScreen screen) {
        if (!fade.isRunning() && start == -1) {
            start = System.currentTimeMillis();
        }

        long actual = event.getDuration() - event.getIn().duration() - event.getOut().duration() - 100L;
        if (start > 0 && System.currentTimeMillis() - start >= actual) {
            start = -2;
            fade = AnimationUtils.build(event.getIn().duration(), 1, 0, EasingFunction.LINEAR);
        }

        final Color color = Color.fromRGB(event.getColor().getRGB()).withAlphaF(fade.getValue());
        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, 1920, 1080, color);
    }

    @Override
    public void serialize(JsonObject serialized) {
        super.serialize(serialized);
        serialized.addProperty("color", event.getColor().getRGB());
        serialized.add("fade-in", GsonUtils.toJson(event.getIn()));
        serialized.add("fade-out", GsonUtils.toJson(event.getOut()));
    }

    @Override
    public ColorOverlayEvent deserialize(JsonObject serialized) {
        final RenderLayer layer = RenderLayer.valueOf(serialized.get("render-layer").getAsString());
        final Fade fadeIn = GsonUtils.getGson().fromJson(serialized.get("fade-in"), Fade.class);
        final Fade fadeOut = GsonUtils.getGson().fromJson(serialized.get("fade-in"), Fade.class);
        return new ColorOverlayEvent(layer, serialized.get("duration").getAsInt(), fadeIn, fadeOut, new java.awt.Color(serialized.get("color").getAsInt()));
    }
}
