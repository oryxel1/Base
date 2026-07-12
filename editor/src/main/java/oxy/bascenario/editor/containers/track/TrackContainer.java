package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.dragdrop.DropEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.utils.drag.FakeObjectComponent;
import oxy.bascenario.utils.drag.SecondaryDragComponent;

@Accessors(fluent = true)
public class TrackContainer extends Container {
    @Getter
    private final TimelineContainer container;

    @Getter
    private float height = 60f;

    public TrackContainer(TimelineContainer container) {
        super(AbsoluteLayout.INSTANCE);
        this.container = container;

        // So the user can scroll more by default.
        this.addChild(new Component() {
            @Override
            public Size computeIdealSize(Size constraints) {
                return new Size(1.0e-16f, 1.0e-16f);
            }
        }, c -> c.layoutOptions(new AbsoluteLayoutOptions(3000, 0, 5f, 5f)));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, size);
    }

    @Override
    protected boolean onComponentDrop(DropEvent event, Size bounds) {
        container.screen().playing(false);

        event.dragData().forEach(data -> {
            if (data instanceof FakeObjectComponent component) {
                component.handle(this, event.x());
            } else if (data instanceof SecondaryDragComponent component) {
                component.handle(this, event.x());
            }
        });

        return !event.dragData().isEmpty();
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), height));
        return new Size(idealSize.width(), height);
    }

    public int index() {
        return container.trackListContainer().container().children().indexOf(this);
    }
}
