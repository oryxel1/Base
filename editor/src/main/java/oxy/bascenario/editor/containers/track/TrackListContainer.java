package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.dragdrop.DropEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.object.ObjectComponent;
import oxy.bascenario.editor.containers.object.ObjectOrEvent;
import oxy.bascenario.editor.containers.track.tab.TrackTabContainer;

@Accessors(fluent = true)
public class TrackListContainer extends ScrollContainer {
    @Getter
    private final TimelineContainer timelineContainer;
    private final Container container;

    public TrackListContainer(TimelineContainer timelineContainer) {
        super(container = new Container(new VerticalListLayout(3, false)), true, true);
        this.timelineContainer = timelineContainer;
    }

    private float prevWidth;

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        if (prevWidth != bounds.width()) {
            resize(bounds.width());
            this.requestLayoutRecalculation();
        }

        prevWidth = bounds.width();

        if (container.children().isEmpty()) {
            renderer.fillRoundedRect(0, 0, 300, 60f, 5, Color.fromRGB(50, 50, 50));
            renderer.scale(0.4f, () -> {
                ShapedText text = this.rivet().backend().shapeText("Drop anything here to get started.", Color.WHITE);
                renderer.text(text, 50,(60f / 2f - (this.rivet().backend().getTextHeight() * 0.4f) / 2f) / 0.4f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP);
            });
        }

        super.render(renderer, bounds);
    }

    @Override
    protected boolean onComponentDrop(DropEvent event, Rectangle bounds) {
        if (!container.children().isEmpty()) {
            return super.onComponentDrop(event, bounds);
        }
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < 60f) {
            final TrackContainer component = new TrackContainer(timelineContainer);
            container.addChild(component);

            timelineContainer.screen().trackTabContainer().addChild(new TrackTabContainer(component));

            add(bounds.width(), component, new ObjectOrEvent(9000, 1000, null, RenderLayer.TOP, true));
        }

        return super.onComponentDrop(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    private void add(float width, TrackContainer container, ObjectOrEvent object) {
        float newX = TimelineContainer.timestampToPosition(object.start, 0, width, timelineContainer.screen().scale(), timelineContainer.screen().scroll());
        container.addChild(new ObjectComponent(this, object), c -> c.layoutOptions(new AbsoluteLayoutOptions(newX, 0)));
    }

    public void resize(float width) {
        for (Component component : container.children()) {
            if (!(component instanceof TrackContainer track)) {
                continue;
            }

            for (Component child : track.children()) {
                if (!(child instanceof ObjectComponent object)) {
                    continue;
                }

                float newX = TimelineContainer.timestampToPosition(object.object().start, 0, width, timelineContainer.screen().scale(), timelineContainer.screen().scroll());
                child.layoutOptions(new AbsoluteLayoutOptions(newX, 0));
            }
        }
    }
}
