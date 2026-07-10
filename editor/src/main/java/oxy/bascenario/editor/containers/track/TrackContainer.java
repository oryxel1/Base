package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.dragdrop.DropEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.object.ObjectComponent;
import oxy.bascenario.editor.containers.object.ObjectOrEvent;
import oxy.bascenario.editor.containers.track.tab.TrackTabContainer;
import oxy.bascenario.util.TimeCompiler;

@Accessors(fluent = true)
public class TrackContainer extends Container {
    @Getter
    private final TimelineContainer container;

    @Getter
    private float height = 60f;

    @Getter
    private float cachedWidth;

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
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, bounds);
    }

    @Override
    protected boolean onComponentDrop(DropEvent event, Size bounds) {
        container.screen().playing(false);

        final float TOTAL_TIME = ScenarioEditorScreen.DEFAULT_MAX_TIME * container.screen().scale();
        long time = (long) (TOTAL_TIME * (event.x() / cachedWidth));

        long duration = TimeCompiler.compileTime(event.dragData());
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        final ObjectOrEvent object = new ObjectOrEvent(time, duration, event.dragData(), RenderLayer.ABOVE_DIALOGUE, true);

        TrackContainer track = this;
        while (true) {
            boolean accepted = true;
            for (Component baseComponent : track.children()) {
                if (!(baseComponent instanceof ObjectComponent component)) {
                    continue;
                }

                long otherMin = component.object().start;

                long maxTime = component.object().start + component.object().duration;
                long otherMaxTime = component.object().start + component.object().duration;

                if (maxTime >= otherMin && time <= otherMaxTime) {
                    accepted = false;
                    break;
                }
            }

            if (!accepted) {
                int size = container.trackListContainer().container().children().size();
                if (track.index() >= size - 1) {
                    track = new TrackContainer(container);
                    container.trackListContainer().container().addChild(track);
                    container.screen().trackTabContainer().addChild(new TrackTabContainer(track));
                    break;
                } else {
                    track = (TrackContainer) container.trackListContainer().container().children().get(track.index() + 1);
                }
            } else {
                break;
            }
        }

        track.addChild(new ObjectComponent(this.container.trackListContainer(), track, object), c -> c.layoutOptions(new AbsoluteLayoutOptions(event.x(), 0)));

        return super.onComponentDrop(event, bounds);
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        this.container.trackListContainer().selectionManager().prevX(Float.MAX_VALUE);
        this.container.trackListContainer().selectionManager().prevX(Float.MAX_VALUE);

        return super.onComponentMouseUp(event, size);
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (!event.buttons().contains(MouseButton.LEFT)) {
            return true;
        }

        if (this.container.trackListContainer().selectionManager().prevX() == Float.MAX_VALUE || this.container.trackListContainer().selectionManager().prevY() == Float.MAX_VALUE) {
            this.container.trackListContainer().selectionManager().prevX(event.x());
            this.container.trackListContainer().selectionManager().prevY(event.y());
            return true;
        }

        float delta = (event.x() - this.container.trackListContainer().selectionManager().prevX());
        this.container.trackListContainer().selectionManager().prevX(event.x());
        this.container.trackListContainer().selectionManager().prevY(event.y());

        final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * container.screen().scale();
        for (Component baseComponent : children()) {
            if (!(baseComponent instanceof ObjectComponent component) || !this.container.trackListContainer().selectionManager().isSelected(component)) {
                continue;
            }

            component.object().start += (long) ((delta / cachedWidth) * maxTime);
            component.object().start = Math.max(0, component.object().start);
            float newX = TimelineContainer.timestampToPosition(
                    component.object().start,
                    0,
                    cachedWidth,
                    container.screen().scale()
            );

            component.layoutOptions(new AbsoluteLayoutOptions(newX, 0));
        }

        return super.onComponentMouseMove(event, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        cachedWidth = constraints.width();
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), height));
        return new Size(Math.max(idealSize.width(), constraints.width()), height);
    }

    public int index() {
        return container.trackListContainer().container().children().indexOf(this);
    }
}
