package oxy.bascenario.editor.drag;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.track.component.ObjectComponent;
import oxy.bascenario.editor.containers.track.component.TrackContainer;
import oxy.bascenario.utils.NameUtils;

import java.util.*;

@RequiredArgsConstructor
public class SecondaryDragComponent extends Component {
    private final float mouseX, mouseY;
    private final List<Data> list;

    @Override
    public void render(Renderer renderer, Size size) {
        for (Data data : list) {
            renderer.translate(mouseX + data.offsetX, mouseY + data.offsetY, () -> {
                float width = EditorValues.instance().oneMilSecondWidth() * data.object.object().duration;

                renderer.fillRect(0, 0, width, 60, Color.fromRGB(30, 30, 30));
                renderer.outlineRect(0, 0, width, 60, 2, Color.fromRGB(145, 218, 255).darker());

                renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(data.object.object().object), Color.WHITE), 20, 20, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));
            });
        }
    }


    @Override
    public Size computeIdealSize(Size size) {
        return size;
    }

    public record Data(ObjectComponent object, float offsetX, float offsetY) {
//        public void handle(final TrackContainer container, float x) {
//            final float dataX = Math.max(0, x + offsetX);
//            long time = Math.max(0, (long) (dataX / container.container().screen().oneMilSecondWidth()));
//
//            TrackContainer track = container;
//            while (true) {
//                boolean accepted = true;
//                for (Component baseComponent : track.children()) {
//                    if (!(baseComponent instanceof ObjectComponent component) || component == object) {
//                        continue;
//                    }
//
//                    long otherMin = component.object().start;
//
//                    long maxTime = object.object().start + object.object().duration;
//                    long otherMaxTime = component.object().start + component.object().duration;
//
//                    if (maxTime >= otherMin && time <= otherMaxTime) {
//                        accepted = false;
//                        break;
//                    }
//                }
//
//                if (!accepted) {
//                    int size = container.container().trackListContainer().container().children().size();
//                    if (track.index() >= size - 1) {
//                        track = new TrackContainer();
//                        container.container().trackListContainer().container().addChild(track);
//                        container.container().trackTabListContainer().addChild(new TrackTabContainer(track));
//                        break;
//                    } else {
//                        track = (TrackContainer) container.container().trackListContainer().container().children().get(track.index() + 1);
//                    }
//                } else {
//                    break;
//                }
//            }
//
//            object.dragging(false);
//
//            object.layoutOptions(new AbsoluteLayoutOptions(dataX, 0));
//            object.object().start = time;
//            track.addChild(object);
//        }
    }

    public void handle(final TrackContainer container, float x) {
//        if (list.isEmpty()) {
//            return;
//        }
//
//        list.sort(Comparator.comparingInt(data -> ((TrackContainer)data.object.parent()).index()));
//
//        int firstIndex = ((TrackContainer)list.getFirst().object.parent()).index();
//
//        final Map<Data, Integer> tempMap = new HashMap<>();
//        for (Data data : list) { // Remove first.
//            tempMap.put(data, ((TrackContainer)data.object.parent()).index());
//            ((Container)data.object.parent()).removeChild(data.object);
//        }
//
//        for (Data data : list) {
//            TrackContainer trackContainer = container;
//            int offset = tempMap.get(data) - firstIndex;
//            if (offset > 0) {
//                int newIndex = trackContainer.index() + offset;
//
//                int size = container.container().trackListContainer().container().children().size();
//                if (newIndex >= size) {
//                    trackContainer = new TrackContainer();
//                    container.container().trackListContainer().container().addChild(trackContainer);
//                    container.container().trackTabListContainer().addChild(new TrackTabContainer(trackContainer));
//                } else {
//                    trackContainer = (TrackContainer) container.container().trackListContainer().container().children().get(newIndex);
//                }
//            }
//
//            data.handle(trackContainer, x);
//        }
    }
}
