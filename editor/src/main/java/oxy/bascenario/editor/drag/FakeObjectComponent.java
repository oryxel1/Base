package oxy.bascenario.editor.drag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.sequencer.VideoSequencerContainer;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.editor.containers.track.component.ObjectComponent;
import oxy.bascenario.editor.containers.track.component.TrackContainer;
import oxy.bascenario.editor.object.ObjectOrEvent;
import oxy.bascenario.utils.NameUtils;
import oxy.bascenario.utils.TimeCompiler;

import static oxy.bascenario.editor.containers.track.component.ObjectComponent.OBJECT_COLOR;

@AllArgsConstructor
@Accessors(fluent = true)
public class FakeObjectComponent extends Component {
    @Getter
    private final Object object;
    private final long duration;

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, OBJECT_COLOR);
        renderer.outlineRoundedRect(0, 0, bounds.width(), bounds.height(), 5, 1, Color.WHITE);

        renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(object), Color.WHITE), 12, 12, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(EditorValues.instance().oneMilSecondWidth() * duration, 60);
    }

    public void handle(TrackContainer trackContainer, float x) {
        long time = (long) (x / EditorValues.instance().oneMilSecondWidth());

        long duration = TimeCompiler.compileTime(object());
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        final ObjectOrEvent object = new ObjectOrEvent(time, duration, object(), RenderLayer.ABOVE_DIALOGUE, true);

        TrackContainer track = trackContainer;
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
                int size = trackContainer.parent().children().size();
                if (track.index() >= size - 1) {
                    track = ((VideoSequencerContainer)((TrackListContainer)((Container)trackContainer.parent()).parent()).parent()).createTrack();
                    break;
                } else {
                    track = (TrackContainer) trackContainer.parent().children().get(track.index() + 1);
                }
            } else {
                break;
            }
        }

        track.addChild(new ObjectComponent((TrackListContainer) ((Container)trackContainer.parent()).parent(), object), c -> c.layoutOptions(new AbsoluteOptions(x, 0)));
    }
}
