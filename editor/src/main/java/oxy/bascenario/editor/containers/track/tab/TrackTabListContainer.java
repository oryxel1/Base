package oxy.bascenario.editor.containers.track.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.Base;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.track.TrackContainer;
import oxy.bascenario.util.components.ButtonImage;

public class TrackTabListContainer extends Container {
    private final ScrollContainer scrollContainer;

    public TrackTabListContainer(Container container, TimelineContainer timelineContainer) {
        super(BorderLayout.INSTANCE);

        // Just to take space.
        this.addChild(new UpperContainer(timelineContainer, container), c -> c.layoutOptions(BorderPosition.TOP));

        this.scrollContainer = new ScrollContainer(container, false, false) {
            @Override
            public void render(Renderer renderer, Size size) {
                super.render(renderer, size);
            }
        };
        this.addChild(this.scrollContainer, c -> c.layoutOptions(BorderPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
    }

    private static class UpperContainer extends Container {
        public UpperContainer(TimelineContainer timelineContainer, Container container) {
            super(AbsoluteLayout.INSTANCE);

            final ButtonImage add = new ButtonImage(Base.instance().assetsManager().texture("assets/base/uis/editor/add_24.png"), event -> {
                if (event.button() != MouseButton.LEFT) {
                    return;
                }

                final TrackContainer component = new TrackContainer(timelineContainer);
                timelineContainer.trackListContainer().container().addChild(component);
                container.addChild(new TrackTabContainer(component));
            });
            add.hoverColor().set(Color.fromRGB(202, 74, 92));

            this.addChild(add, c -> c.layoutOptions(new AbsoluteLayoutOptions(5, 5)));
        }

        @Override
        public Size computeIdealSize(Size constraints) {
            return new Size(constraints.width(), 35);
        }
    }
}
