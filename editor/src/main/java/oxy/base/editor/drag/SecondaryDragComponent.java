package oxy.base.editor.drag;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.editor.EditorValues;
import oxy.base.editor.containers.sequencer.VideoSequencerContainer;
import oxy.base.editor.containers.track.TrackListContainer;
import oxy.base.editor.containers.track.component.ObjectComponent;
import oxy.base.editor.containers.track.component.TrackContainer;
import oxy.base.utils.NameUtils;

import java.util.*;

import static oxy.base.editor.containers.track.component.ObjectComponent.OBJECT_COLOR;

@RequiredArgsConstructor
public class SecondaryDragComponent extends Component {
    private final float mouseX, mouseY;
    private final List<Data> list;

    @Override
    public void render(Renderer renderer, Size size) {
        for (Data data : list) {
            renderer.translate(mouseX + data.offsetX, mouseY + data.offsetY, () -> {
                float width = EditorValues.instance().oneMilSecondWidth() * data.object.object().duration;

                renderer.fillRoundedRect(0, 0, width, 60, 5, OBJECT_COLOR);
                renderer.outlineRoundedRect(0, 0, width, 60, 5, 1, Color.WHITE);

                renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(data.object.object().object), Color.WHITE), 12, 12, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP);
            });
        }
    }


    @Override
    public Size computeIdealSize(Size size) {
        return size;
    }

    public record Data(ObjectComponent object, float offsetX, float offsetY) {
        public void handle(final TrackContainer container, float x) {
            final float dataX = Math.max(0, x + offsetX);
            long time = Math.max(0, (long) (dataX / EditorValues.instance().oneMilSecondWidth()));

            TrackContainer track = container;
            while (true) {
                boolean accepted = true;
                for (Component baseComponent : track.children()) {
                    if (!(baseComponent instanceof ObjectComponent component) || component == object) {
                        continue;
                    }

                    long otherMin = component.object().start;

                    long maxTime = object.object().start + object.object().duration;
                    long otherMaxTime = component.object().start + component.object().duration;

                    if (maxTime >= otherMin && time <= otherMaxTime) {
                        accepted = false;
                        break;
                    }
                }

                if (!accepted) {
                    int size = container.parent().children().size();
                    if (track.index() >= size - 1) {
                        track = ((VideoSequencerContainer)((TrackListContainer)((Container)container.parent()).parent()).parent()).createTrack();
                    } else {
                        track = (TrackContainer) container.parent().children().get(track.index() + 1);
                    }
                } else {
                    break;
                }
            }

            object.dragging(false);

            object.layoutOptions(new AbsoluteOptions(dataX, 0));
            object.object().start = time;
            track.addChild(object);
        }
    }

    public void handle(final TrackContainer container, float x) {
        if (list.isEmpty()) {
            return;
        }

        list.sort(Comparator.comparingInt(data -> ((TrackContainer)data.object.parent()).index()));

        int firstIndex = ((TrackContainer)list.getFirst().object.parent()).index();

        final Map<Data, Integer> tempMap = new HashMap<>();
        for (Data data : list) { // Remove first.
            tempMap.put(data, ((TrackContainer)data.object.parent()).index());
            ((Container)data.object.parent()).removeChild(data.object);
        }

        for (Data data : list) {
            TrackContainer trackContainer = container;
            int offset = tempMap.get(data) - firstIndex;
            if (offset > 0) {
                int newIndex = trackContainer.index() + offset;

                int size = trackContainer.parent().children().size();
                if (newIndex >= size) {
                    trackContainer = ((VideoSequencerContainer)((TrackListContainer)((Container)container.parent()).parent()).parent()).createTrack();
                } else {
                    trackContainer = (TrackContainer) container.parent().children().get(trackContainer.index() + 1);
                }
            }

            data.handle(trackContainer, x);
        }
    }
}
