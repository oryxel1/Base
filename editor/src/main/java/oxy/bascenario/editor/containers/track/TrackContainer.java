package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.dragdrop.DropEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
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

    public TrackContainer(TimelineContainer container) {
        super(AbsoluteLayout.INSTANCE);
        this.container = container;
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(40, 40, 40));
        super.render(renderer, bounds);
    }

    @Override
    protected boolean onComponentDrop(DropEvent event, Size bounds) {
        long time = (long) ((ScenarioEditorScreen.DEFAULT_MAX_TIME * container.screen().scale()) * (event.x() / bounds.width()));

        long duration = TimeCompiler.compileTime(event.dragData());
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        final ObjectOrEvent object = new ObjectOrEvent(time, duration, event.dragData(), RenderLayer.ABOVE_DIALOGUE, true);

        boolean accepted = true;
        for (Component baseComponent : children()) {
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

        if (accepted) {
            this.addChild(new ObjectComponent(this.container.trackListContainer(), this, object), c -> c.layoutOptions(new AbsoluteLayoutOptions(event.x(), 0)));
        } else {
            final TrackContainer newTrack = new TrackContainer(container);
            container.trackListContainer().container().addChild(newTrack);
            container.screen().trackTabContainer().addChild(new TrackTabContainer(newTrack));
            newTrack.addChild(new ObjectComponent(this.container.trackListContainer(), newTrack, object), c -> c.layoutOptions(new AbsoluteLayoutOptions(event.x(), 0)));
        }

        return super.onComponentDrop(event, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), height));
        return new Size(Math.max(idealSize.width(), constraints.width()), height);
    }

    public int index() {
        return container.trackListContainer().container().children().indexOf(this);
    }
}
