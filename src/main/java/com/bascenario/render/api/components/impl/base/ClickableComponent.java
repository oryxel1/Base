package com.bascenario.render.api.components.impl.base;

import com.bascenario.render.api.components.api.Component;

public class ClickableComponent extends Component {
    protected final float width, height;
    private final Runnable action;

    public ClickableComponent(float x, float y, float width, float height, Runnable action) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.action = action;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHoveringOver(mouseX, mouseY, width, height) && button == 0) {
            this.action.run();
        }
    }
}
