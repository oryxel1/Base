package oxy.bascenario.editor.containers.inspector;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;

public class InspectorContainer extends Container {
    public InspectorContainer() {
        super(new VerticalListLayout(5, true));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRect(0, 0, size.width(), size.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, size);
    }
}
