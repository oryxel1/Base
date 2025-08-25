package com.bascenario.render.api.components.impl.base;

import com.bascenario.render.api.components.api.Component;

public class ClickableComponent extends Component {
    private final int width, height;
    private final Runnable action;

    public ClickableComponent(int x, int y, int width, int height, Runnable action) {
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
