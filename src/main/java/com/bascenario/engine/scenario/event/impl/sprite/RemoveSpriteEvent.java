package com.bascenario.engine.scenario.event.impl.sprite;

import com.bascenario.engine.scenario.elements.Sprite;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.SpriteRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

public class RemoveSpriteEvent extends Event {
    private final Sprite sprite;
    private final boolean fadeOut;
    private final long fadeDuration;

    public RemoveSpriteEvent(Sprite sprite, boolean fadeOut, long fadeDuration) {
        super(fadeDuration > 0 ? fadeDuration + 200L : 0);
        this.sprite = sprite;
        this.fadeOut = fadeOut;
        this.fadeDuration = fadeDuration;
    }

    public RemoveSpriteEvent(Sprite sprite) {
        super(0);
        this.sprite = sprite;
        this.fadeOut = false;
        this.fadeDuration = 0;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (!this.fadeOut) {
            this.yeetSprite(screen);
        } else {
            final SpriteRender render = getRender(screen);
            if (render != null && render.getFadeColor() != null) {
                render.setFadeColor(new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN_OUT, this.fadeDuration, render.getFadeColor().getValue()));
                render.getFadeColor().setTarget(1);
            }
        }
    }

    @Override
    public void onEnd(ScenarioScreen screen) {
        if (this.fadeOut) {
            this.yeetSprite(screen);
        }
    }

    private SpriteRender getRender(final ScenarioScreen screen) {
        SpriteRender render = null;
        for (SpriteRender spriteRender : screen.getSprites()) {
            if (spriteRender.getSprite().equals(this.sprite)) {
                render = spriteRender;
            }
        }

        return render;
    }

    private void yeetSprite(final ScenarioScreen screen) {
        SpriteRender render = getRender(screen);
        render.dispose();
        screen.getSprites().remove(render);
    }
}
