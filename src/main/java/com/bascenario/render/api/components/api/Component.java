package com.bascenario.render.api.components.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Component {
    protected int x, y;

    public void render(int width, int height, double mouseX, double mouseY) {}
    public void mouseClicked(double mouseX, double mouseY, int button) {}
    public void mouseRelease() {}

    public boolean isHoveringOver(double mouseX, double mouseY, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
