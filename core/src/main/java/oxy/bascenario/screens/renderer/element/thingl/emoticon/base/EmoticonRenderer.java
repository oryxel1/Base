package oxy.bascenario.screens.renderer.element.thingl.emoticon.base;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class EmoticonRenderer {
    protected final long duration;

    public abstract void render();
    public void init() {
    }

    public boolean finished() {
        return false;
    }
}
