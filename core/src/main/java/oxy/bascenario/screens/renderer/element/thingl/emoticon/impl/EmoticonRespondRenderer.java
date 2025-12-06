package oxy.bascenario.screens.renderer.element.thingl.emoticon.impl;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.color.Color;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.resource.image.texture.Texture2D;
import oxy.bascenario.managers.TextureManager;
import oxy.bascenario.screens.renderer.element.thingl.emoticon.base.EmoticonRenderer;
import oxy.bascenario.utils.animation.AnimationUtils;

import static oxy.bascenario.utils.ThinGLUtils.GLOBAL_RENDER_STACK;

@RequiredArgsConstructor
public class EmoticonRespondRenderer implements EmoticonRenderer {
    private final long duration;

    private DynamicAnimation opacity = AnimationUtils.dummy(1);
    private long since = -1;

    @Override
    public void render() {
        final Color color = Color.WHITE.withAlphaF(this.opacity.getValue());

        final Texture2D action = TextureManager.getInstance().getTexture("assets/base/uis/emoticons/Emoticon_Action.png");
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, 0, 127, 51, color);

        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(-20));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, 81, 127, 51, color);

        GLOBAL_RENDER_STACK.rotateZ((float) Math.toRadians(40));
        ThinGL.renderer2D().coloredTexture(GLOBAL_RENDER_STACK, action, 0, -81, 127, 51, color);
    }
}
