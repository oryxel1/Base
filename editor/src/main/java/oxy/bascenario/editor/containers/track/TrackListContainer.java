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
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.object.ObjectComponent;
import oxy.bascenario.editor.containers.object.ObjectOrEvent;
import oxy.bascenario.editor.containers.selection.SelectionManager;
import oxy.bascenario.editor.containers.track.tab.TrackTabContainer;
import oxy.bascenario.utils.TimeCompiler;

@Accessors(fluent = true)
public class TrackListContainer extends ScrollContainer {
    @Getter
    private final TimelineContainer timelineContainer;
    @Getter
    private final Container container;

    @Getter
    private final SelectionManager selectionManager = new SelectionManager();

    public TrackListContainer(TimelineContainer timelineContainer) {
        super(container = new Container(new VerticalListLayout(3, false)), true, true);
        this.timelineContainer = timelineContainer;
    }

    private float prevWidth;

    @Override
    public void render(Renderer renderer, Size bounds) {
        if (prevWidth != bounds.width()) {
            recalculateObjectPosition();
            this.requestLayoutRecalculation();
        }

        prevWidth = bounds.width();

        if (container.children().isEmpty()) {
            renderer.fillRect(0, 0, 300, 60f, Color.fromRGB(50, 50, 50));
            renderer.scale(0.4f, () -> {
                ShapedText text = this.rivet().backend().font().shapeText("Drop anything here to get started.", Color.WHITE);
                renderer.text(text, 50,(60f / 2f - (this.rivet().backend().font().height() * 0.4f) / 2f) / 0.4f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP);
            });
        }

        super.render(renderer, bounds);

        this.selectionManager.render(renderer, timelineContainer.childBounds(this));
    }

    @Override
    protected boolean onComponentDrop(DropEvent event, Size bounds) {
        if (!container.children().isEmpty()) {
            return super.onComponentDrop(event, bounds);
        }
        if (event.x() > 0 && event.x() < bounds.width() && event.y() > 0 && event.y() < 60f) {
            final TrackContainer component = new TrackContainer(timelineContainer);
            container.addChild(component);

            timelineContainer.screen().trackTabContainer().addChild(new TrackTabContainer(component));

            long duration = TimeCompiler.compileTime(event.dragData());
            if (duration == Long.MAX_VALUE) {
                duration = 1000L;
            }
            add(component, new ObjectOrEvent(0, duration, event.dragData(), RenderLayer.ABOVE_DIALOGUE, true));
        }

        return super.onComponentDrop(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    private void add(TrackContainer container, ObjectOrEvent object) {
        float newX = TimelineContainer.timestampToPosition(object.start, 0, timelineContainer.screen().oneMilSecondWidth(), timelineContainer.screen().scale());
        container.addChild(new ObjectComponent(this, container, object), c -> c.layoutOptions(new AbsoluteLayoutOptions(newX, 0)));
    }

    public void recalculateObjectPosition() {
        for (Component component : container.children()) {
            if (!(component instanceof TrackContainer track)) {
                continue;
            }

            for (Component child : track.children()) {
                if (!(child instanceof ObjectComponent object)) {
                    continue;
                }

                float newX = TimelineContainer.timestampToPosition(object.object().start, 0, timelineContainer.screen().oneMilSecondWidth(), timelineContainer.screen().scale());
                child.layoutOptions(new AbsoluteLayoutOptions(newX, 0));
            }
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size bounds) {
        if (event.buttons().contains(MouseButton.LEFT)) {
            this.selectionManager.x1(event.x());
            this.selectionManager.y1(event.y());
            this.rivet().focusedComponent(this);
        }

        return super.onComponentMouseMove(event, bounds);
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        if (!super.onComponentMouseDown(event, bounds) && event.button() == MouseButton.LEFT) {
            this.selectionManager.x(event.x());
            this.selectionManager.y(event.y());

            this.selectionManager.x1(event.x());
            this.selectionManager.y1(event.y());

            this.selectionManager.objects().clear();

            this.rivet().focusedComponent(this);
        }
        return true;
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size bounds) {
        if (event.button() == MouseButton.LEFT) {
            this.selectionManager.x(0);
            this.selectionManager.y(0);
        }

        return super.onComponentMouseUp(event, bounds);
    }

    @Override
    protected boolean onComponentKeyUp(KeyEvent event) {
        if (event.key() == Key.DELETE) {
            selectionManager.objects().forEach(component -> component.parentTrack().removeChild(component));
            selectionManager().objects().clear();
        }

        return false;
    }
}
