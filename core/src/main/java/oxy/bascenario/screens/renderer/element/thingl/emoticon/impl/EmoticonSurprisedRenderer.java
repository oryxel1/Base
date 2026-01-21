package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import oxy.bascenario.Base;
import oxy.bascenario.utils.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.thingl.ThinGLUtils.GLOBAL_RENDER_STACK;

public class EmoticonSurprisedRenderer extends EmoticonRenderer {
    private DynamicAnimation opacity = AnimationUtils.dummy(1);
    private DynamicAnimation suprisedEM = AnimationUtils.dummy(0.6f), suprisedQM = AnimationUtils.dummy(0.6f);

    private long since = -1;

    public EmoticonSurprisedRenderer(long duration) {
        super(duration);
    }

    @Override
    public void init() {
        this.suprisedEM = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.QUAD);
        this.suprisedQM = AnimationUtils.build(200L, 0.6f, 1, EasingFunction.QUAD);

        this.since = TimeUtils.currentTimeMillis();
    }

    @Override
    public void render() {
        if (TimeUtils.currentTimeMillis() - this.since >= this.duration && this.opacity instanceof AnimationUtils.DummyAnimation) {
            long distance = TimeUtils.currentTimeMillis() - (TimeUtils.currentTimeMillis() - this.since - this.duration);

            this.opacity = AnimationUtils.build(800, distance, 1, 0, EasingFunction.LINEAR);
            this.since = -2;
        }

        final Texture2D em = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Exclamation.png");
        final Texture2D qm = Base.instance().assetsManager().texture("assets/base/uis/emoticons/Emoticon_Question.png");

        GLOBAL_RENDER_STACK.pushMatrix();
        GLOBAL_RENDER_STACK.scale(1, this.suprisedEM.getValue(), 1);
        GLOBAL_RENDER_STACK.translate(1, (1 - this.suprisedEM.getValue()) * 149 * 0.7f, 1);
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, em, 0, 0, 60 * 0.7f, 149 * 0.7f);
        GLOBAL_RENDER_STACK.translate(1, -((1 - this.suprisedEM.getValue()) * 149 * 0.7f), 1);
        GLOBAL_RENDER_STACK.scale(1, 1 / this.suprisedEM.getValue(), 1);
        GLOBAL_RENDER_STACK.scale(1, this.suprisedQM.getValue(), 1);
        GLOBAL_RENDER_STACK.translate(1, -((1 - this.suprisedEM.getValue()) * 149 * 0.7f), 1);
        GLOBAL_RENDER_STACK.translate(1, (1 - this.suprisedQM.getValue()) * 148 * 0.7f, 1);
        ThinGL.renderer2D().texture(GLOBAL_RENDER_STACK, qm, 65 * 0.7f, 0, 103 * 0.7f, 148 * 0.7f);
        GLOBAL_RENDER_STACK.popMatrix();
    }

    @Override
    public boolean finished() {
        return this.since == -2 && !this.opacity.isRunning();
    }
}
