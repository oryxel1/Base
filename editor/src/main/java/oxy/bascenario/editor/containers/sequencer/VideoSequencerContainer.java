package oxy.bascenario.editor.containers.sequencer;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.input.mouse.MouseScrollEvent;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.editor.containers.track.TrackTabListContainer;
import oxy.bascenario.editor.containers.track.component.TrackContainer;
import oxy.bascenario.editor.containers.track.component.TrackTabContainer;

@Accessors(fluent = true)
public class VideoSequencerContainer extends Container {
    final Component sequenceTimeSection;

    final TrackTabListContainer trackTabListContainer;
    @Getter
    final TrackListContainer trackListContainer;

    public VideoSequencerContainer() {
        super(new DockLayout());

        final Container mouseContainer = new Container(new VerticalListLayout()) {
            @Override
            public Size computeIdealSize(Size constraints) {
                return constraints.withWidth(55f);
            }
        };

        this.addChild(new DecoratedContainer(new SolidColor(Color.fromRGB(48, 48, 48)), mouseContainer), c -> c.layoutOptions(DockPosition.LEFT));

        this.addChild(trackTabListContainer = new TrackTabListContainer(this), c -> c.layoutOptions(DockPosition.LEFT));

        this.addChild(sequenceTimeSection = new SequencerTimeSection(), c -> c.layoutOptions(DockPosition.TOP));

        this.addChild(trackListContainer = new TrackListContainer(), c -> c.layoutOptions(DockPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRect(0, 0, size.width(), size.height(), Color.fromRGB(30, 30, 30));

        super.render(renderer, size);
        drawCursor(renderer, size);
    }

    @Override
    protected boolean onComponentMouseScroll(MouseScrollEvent event, Size size) {
        if (event.scrollY() > 0) {
            EditorValues.instance().scale(EditorValues.instance().scale() + 0.1f);
            trackListContainer.requestLayoutRecalculation();
            trackListContainer.recalculateObjectPosition();
        } else if (event.scrollY() < 0) {
            EditorValues.instance().scale(EditorValues.instance().scale() - 0.1f);
            trackListContainer.requestLayoutRecalculation();
            trackListContainer.recalculateObjectPosition();
        }

        return super.onComponentMouseScroll(event, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    private static final Color SEQUENCER_CURSOR_COLOR = Color.fromRGB(71, 114, 179);

    private void drawCursor(Renderer renderer, Size size) {
        Rectangle bounds = this.childBounds(trackListContainer);

        final float offsetX = bounds.x();
        final float cursorX = -2.5f + offsetX + EditorValues.instance().timestamp() * (EditorValues.instance().oneSecondWidth() / 1000f) - EditorValues.instance().scroll();

        renderer.componentBounds(bounds.x(), 0, bounds.width(), this.absoluteBounds().height(), () -> {
            renderer.fillRect(cursorX, 0, 5, size.height(), SEQUENCER_CURSOR_COLOR.withAlphaF(0.8f));
            renderer.outlineRect(cursorX, 0, 5, size.height(), 1, Color.BLACK);

            renderer.fillTriangle(cursorX + 2.5f - 10, 0, cursorX + 2.5f, 10, cursorX + 2.5f + 10, 0, SEQUENCER_CURSOR_COLOR);
        });
    }

    public TrackContainer createTrack() {
        final TrackContainer container = new TrackContainer();
        ((Container) trackListContainer.child()).addChild(container);
        trackTabListContainer.container().addChild(new TrackTabContainer(this, container));
        return container;
    }

    public void removeTrack(TrackContainer container, TrackTabContainer tabContainer) {
        ((Container) trackListContainer.child()).removeChild(container);
        trackTabListContainer.container().removeChild(tabContainer);
    }
}
