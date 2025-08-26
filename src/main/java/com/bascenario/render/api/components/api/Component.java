package com.bascenario.render.api.components.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

@AllArgsConstructor
@Getter
public class Component {
    protected float x, y;

    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {}
    public void mouseClicked(double mouseX, double mouseY, int button) {}
    public void mouseRelease() {}

    public boolean isHoveringOver(double mouseX, double mouseY, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
