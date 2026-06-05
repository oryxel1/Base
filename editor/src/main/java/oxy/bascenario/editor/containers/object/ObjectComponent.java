package oxy.bascenario.editor.containers.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.track.TrackListContainer;

@Accessors(fluent = true)
@RequiredArgsConstructor
public class ObjectComponent extends Component {
    private static final Color OBJECT_COLOR = Color.fromRGB(202, 74, 92).darker();

    private final TrackListContainer parent;
    @Getter
    private final ObjectOrEvent object;

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(30, 30, 30));
        renderer.outlineRoundedRect(0, 0, bounds.width(), bounds.height(), 5, 2, OBJECT_COLOR);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * parent.timelineContainer().screen().scale();
        return new Size((object.duration / maxTime) * constraints.width(), constraints.height());
    }
}
