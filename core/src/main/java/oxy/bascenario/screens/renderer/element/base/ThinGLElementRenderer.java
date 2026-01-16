package oxy.bascenario.screens.renderer.element.base;

import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public abstract class ThinGLElementRenderer<T> extends ElementRenderer<T> {
    private static final float DEGREES_TO_RADIANS = 0.017453292519943295f;

    public ThinGLElementRenderer(T element, RenderLayer layer) {
        super(element, layer);
    }

    @Override
    protected final void render() {
        ThinGL.programs().getMsaa().bindInput();

        GLOBAL_RENDER_STACK.pushMatrix();

        GLOBAL_RENDER_STACK.translate(this.pivot.x(), this.pivot.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.offset.x(), this.offset.y(), 0);
        GLOBAL_RENDER_STACK.rotateXYZ(this.rotation.x() * DEGREES_TO_RADIANS, this.rotation.y() * DEGREES_TO_RADIANS, this.rotation.z() * DEGREES_TO_RADIANS);
        GLOBAL_RENDER_STACK.translate(-this.offset.x(), -this.offset.y(), 0);
        GLOBAL_RENDER_STACK.translate(-this.pivot.x(), -this.pivot.y(), 0);

        GLOBAL_RENDER_STACK.translate(this.offset.x(), this.offset.y(), 0);
        GLOBAL_RENDER_STACK.translate(this.position.x(), this.position.y(), 0);
        GLOBAL_RENDER_STACK.scale(this.scale.x(), this.scale.y(), 1);

        // TODO: The offsetting wouldn't works with Sprite Rendering, but welp, if they put sprite inside an element, they're fucking crazy.
        this.subElements.values().forEach(ElementRenderer::renderAll);

        if (!this.effects.containsKey(Effect.OUTLINE) || this.effects.size() > 1) {
            renderThinGL();
        }

        ThinGLUtils.renderEffect(this::renderThinGL, this.effects);
        GLOBAL_RENDER_STACK.popMatrix();

        ThinGL.programs().getMsaa().unbindInput();
        ThinGL.programs().getMsaa().renderFullscreen();
        ThinGL.programs().getMsaa().clearInput();
    }

    protected abstract void renderThinGL();
}
