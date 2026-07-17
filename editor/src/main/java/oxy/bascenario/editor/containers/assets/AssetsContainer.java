package oxy.bascenario.editor.containers.assets;

import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.GlobalContainer;
import oxy.bascenario.editor.containers.assets.object.ObjectContainer;

@Accessors(fluent = true)
public class AssetsContainer extends GlobalContainer.ResizeableContainer {
    public AssetsContainer() {
        super(new DockLayout(0));

        addChild(new DecoratedContainer(new SolidColor(), new ObjectContainer()), c -> {
                c.layoutOptions(DockPosition.CENTER);
                c.innerPadding(new Padding(10, 10, 0, 0));
        });
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, size);
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(53, 53, 53));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withWidth(constraints.width() * relativeScale);
    }
}
