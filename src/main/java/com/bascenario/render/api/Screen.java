package com.bascenario.render.api;

import com.bascenario.render.api.components.api.Component;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    @Getter @Setter
    private boolean init;
    public int width, height;
    protected final List<Component> components = new ArrayList<>();

    public void init() {

    }

    public void render(double mouseX, double mouseY) {
        components.forEach(c -> c.render(this.width, this.height, mouseX, mouseY));
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        components.forEach(c -> c.mouseClicked(mouseX, mouseY, button));
    }

    public void mouseRelease() {
        components.forEach(Component::mouseRelease);
    }
}
