package com.bascenario.render.api;

import com.bascenario.render.api.components.api.Component;
import lombok.Getter;
import lombok.Setter;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    @Getter @Setter
    private boolean init;
    protected final List<Component> components = new ArrayList<>();

    public void init() {

    }

    public void render(Matrix4fStack positionMatrix, WindowInterface window, double mouseX, double mouseY) {
        components.forEach(c -> c.render(positionMatrix, window, mouseX, mouseY));
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        components.forEach(c -> c.mouseClicked(mouseX, mouseY, button));
    }

    public void mouseRelease() {
        components.forEach(Component::mouseRelease);
    }
}
