package oxy.base.editor.containers.preview;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.math.Size;

public class PreviewContainer extends Container {
    public PreviewContainer() {
        super(new DockLayout());
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.BLACK);
        super.render(renderer, size);
    }
}
