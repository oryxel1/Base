package oxy.bascenario.screens.renderer.element.thingl;

import oxy.bascenario.Base;
import oxy.bascenario.api.render.elements.text.font.FontStyle;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.renderer.impl.RendererText;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.screens.renderer.element.base.ElementRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

public class LocationInfoRenderer extends ElementRenderer<LocationInfo> {
    private static final float Y = 200, HEIGH = 64.8F, SEPARATOR_Y = 216.2f, SEPARATOR_HEIGH = HEIGH / 2f;

    private final long start = TimeUtils.currentTimeMillis();
    private DynamicAnimation box = AnimationUtils.dummy(0), text = AnimationUtils.dummy(0);

    public LocationInfoRenderer(LocationInfo element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected void render() {
        if (this.box instanceof AnimationUtils.DummyAnimation) {
            this.box = AnimationUtils.build(element.fade(), 0, 0.78f, EasingFunction.LINEAR);
            this.text = AnimationUtils.build(element.fade(), 0, 1, EasingFunction.LINEAR);
        }
        if (TimeUtils.currentTimeMillis() - this.start >= element.duration()) {
            long distance = (TimeUtils.currentTimeMillis() - this.start) - element.duration();
            this.box.setTarget(0f, TimeUtils.currentTimeMillis() - distance);
            this.text.setTarget(0f, TimeUtils.currentTimeMillis() - distance);
        }

        final TextRun text = TextRun.fromString(FontUtils.font(FontStyle.SEMI_BOLD, element.font()), element.location(), Color.WHITE.withAlphaF(this.text.getValue()));
        float width = TextUtils.getVisualWidth(40, text.shape()) + 85;

        Color color = Color.WHITE.withAlphaF(this.box.getValue());
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 0, Y, width, Y + HEIGH, Color.fromRGB(85, 91, 124).withAlphaF(this.box.getValue()));
        ThinGL.renderer2D().filledRectangle(GLOBAL_RENDER_STACK, 30, SEPARATOR_Y, 35, SEPARATOR_Y + SEPARATOR_HEIGH, Color.fromRGB(182, 182, 182).withAlphaF(this.box.getValue()));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, Base.instance().assetsManager().texture("assets/base/uis/other/location_info.png"), width, Y,  30,  HEIGH, color);
        TextUtils.textRun(40, text, 56, Y + SEPARATOR_HEIGH + (TextUtils.getLogicalHeight(40, text.shape()) / 2), RendererText.VerticalOrigin.LOGICAL_BOTTOM, RendererText.HorizontalOrigin.LOGICAL_LEFT);
    }

    @Override
    public boolean selfDestruct() {
        return this.box.getTarget() == 0 && !this.box.isRunning() && !this.text.isRunning();
    }
}
