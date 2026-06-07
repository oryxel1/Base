package oxy.bascenario.editor.containers.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.util.NameUtils;

import java.util.List;

@Accessors(fluent = true)
public class ObjectComponent extends Component {
    private static final Color OBJECT_COLOR = Color.fromRGB(202, 74, 92).darker();

    private final TrackListContainer parent;
    @Getter
    private final ObjectOrEvent object;

    public ObjectComponent(TrackListContainer parent, ObjectOrEvent object) {
        this.parent = parent;
        this.object = object;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(30, 30, 30));
        renderer.outlineRoundedRect(0, 0, bounds.width(), bounds.height(), 5, 2, parent.selectionManager().isSelected(this) ? Color.WHITE : OBJECT_COLOR);

        renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().shapeText(NameUtils.name(object.object), Color.WHITE), 20, 20, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        this.parent.selectionManager().addIfIntersects(this, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * parent.timelineContainer().screen().scale();
        return new Size((object.duration / maxTime) * constraints.width(), constraints.height());
    }
}
