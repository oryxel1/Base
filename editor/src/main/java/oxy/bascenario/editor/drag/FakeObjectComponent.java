package oxy.bascenario.editor.drag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.track.component.ObjectComponent;
import oxy.bascenario.editor.containers.track.component.TrackContainer;
import oxy.bascenario.editor.object.ObjectOrEvent;
import oxy.bascenario.utils.NameUtils;
import oxy.bascenario.utils.TimeCompiler;

@AllArgsConstructor
@Accessors(fluent = true)
public class FakeObjectComponent extends Component {
    @Getter
    private final Object object;
    private final long duration;

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(30, 30, 30));
        renderer.outlineRect(0, 0, bounds.width(), bounds.height(), 2, Color.fromRGB(145, 218, 255).darker());

        renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(object), Color.WHITE), 20, 20, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        float widthToAMil = EditorValues.instance().oneSecondWidth() / 1000f;
        return new Size(widthToAMil * duration, 60);
    }

    public void handle(TrackContainer trackContainer, float x) {
//        long time = (long) (x / EditorValues.instance().oneMilSecondWidth());
//        long duration = TimeCompiler.compileTime(object());
//        if (duration == Long.MAX_VALUE) {
//            duration = 1000L;
//        }
//
//        final ObjectOrEvent object = new ObjectOrEvent(time, duration, object(), RenderLayer.ABOVE_DIALOGUE, true);
//
//        TrackContainer track = trackContainer;
//        while (true) {
//            boolean accepted = true;
//            for (Component baseComponent : track.children()) {
//                if (!(baseComponent instanceof ObjectComponent component)) {
//                    continue;
//                }
//
//                long otherMin = component.object().start;
//
//                long maxTime = component.object().start + component.object().duration;
//                long otherMaxTime = component.object().start + component.object().duration;
//
//                if (maxTime >= otherMin && time <= otherMaxTime) {
//                    accepted = false;
//                    break;
//                }
//            }
//
//            if (!accepted) {
//                int size = trackContainer.container().trackListContainer().container().children().size();
//                if (track.index() >= size - 1) {
//                    track = new TrackContainer();
//                    trackContainer.container().trackListContainer().container().addChild(track);
//                    trackContainer.container().trackTabListContainer().addChild(new TrackTabContainer(track));
//                    break;
//                } else {
//                    track = (TrackContainer) trackContainer.container().trackListContainer().container().children().get(track.index() + 1);
//                }
//            } else {
//                break;
//            }
//        }
//
//        track.addChild(new ObjectComponent(trackContainer.container().trackListContainer(), object), c -> c.layoutOptions(new AbsoluteOptions(x, 0)));
    }
}
