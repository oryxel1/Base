package oxy.bascenario.editor.containers.sequencer;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.math.Size;

public class VideoSequencerContainer extends Container {
    public VideoSequencerContainer() {
        super(BorderLayout.INSTANCE);
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRect(0, 0, size.width(), size.height(), Color.fromRGB(30, 30, 30));
        super.render(renderer, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }
}
