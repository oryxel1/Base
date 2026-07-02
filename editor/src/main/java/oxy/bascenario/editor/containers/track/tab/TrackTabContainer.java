package oxy.bascenario.editor.containers.track.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.Image;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.layout.Layout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;
import oxy.bascenario.editor.containers.track.TrackContainer;
import oxy.bascenario.util.components.ButtonImage;

public class TrackTabContainer extends Container {
    private final TrackContainer container;

    public TrackTabContainer(TrackContainer container) {
        super(AbsoluteLayout.INSTANCE);
        this.container = container;

        final ButtonImage trash = new ButtonImage(Base.instance().assetsManager().texture("assets/base/uis/editor/trash_18.png"), event -> {
            if (event.button() != MouseButton.LEFT) {
                return;
            }

            this.container.container().screen().trackTabContainer().removeChild(this);
            this.container.container().trackListContainer().container().removeChild(container);
        });
        trash.hoverColor().set(Color.fromRGB(202, 74, 92));
        this.addChild(trash, image -> image.layoutOptions(new AbsoluteLayoutOptions(5, 35)));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
//        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.RED);
        renderer.scale(0.5f, () -> renderer.text(this.rivet().backend().font().shapeText("Track " + container.index(), Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), container.height());
    }
}
