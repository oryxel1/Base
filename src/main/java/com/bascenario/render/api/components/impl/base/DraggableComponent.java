package com.bascenario.render.api.components.impl.base;

import com.bascenario.render.api.components.api.Component;
import lombok.Getter;

public class DraggableComponent extends Component {
    private final int width, height;
    @Getter
    private boolean dragging;
    private int mouseToX, mouseToY;

    public DraggableComponent(int x, int y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(int width, int height, double mouseX, double mouseY) {
        this.dragging = this.dragging && this.isHoveringOver(mouseX, mouseY, width, height);

        if (this.dragging) {
            this.x = (int)mouseX - this.mouseToX;
            this.y = (int)mouseY - this.mouseToY;
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isHoveringOver(mouseX, mouseY, width, height) && button == 0) {
            this.dragging = true;
            this.mouseToX = (int) (mouseX - x);
            this.mouseToY = (int) (mouseY - y);
        }
    }

    @Override
    public void mouseRelease() {
        this.dragging = false;
    }
}
