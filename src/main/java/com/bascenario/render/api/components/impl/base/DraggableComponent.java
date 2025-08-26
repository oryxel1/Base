package com.bascenario.render.api.components.impl.base;

import com.bascenario.render.api.components.api.Component;
import lombok.Getter;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

public class DraggableComponent extends Component {
    private final float width, height;
    @Getter
    private boolean dragging;
    private int mouseToX, mouseToY;

    public DraggableComponent(float x, float y, float width, float height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
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
