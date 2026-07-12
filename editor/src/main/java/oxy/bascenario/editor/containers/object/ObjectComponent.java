package oxy.bascenario.editor.containers.object;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.keyboard.ModifierKey;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.track.TrackContainer;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.utils.NameUtils;

@Accessors(fluent = true)
public class ObjectComponent extends Component {
    private static final Color OBJECT_COLOR = Color.fromRGB(202, 74, 92).darker();

    private final TrackListContainer parent;
    @Getter
    private final TrackContainer parentTrack;
    @Getter
    private final ObjectOrEvent object;

    public ObjectComponent(TrackListContainer parent, TrackContainer parentTrack, ObjectOrEvent object) {
        this.parent = parent;
        this.parentTrack = parentTrack;
        this.object = object;
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRoundedRect(0, 0, bounds.width(), bounds.height(), 5, Color.fromRGB(30, 30, 30));
        renderer.outlineRoundedRect(0, 0, bounds.width(), bounds.height(), 5, 2, parent.selectionManager().isSelected(this) ? Color.WHITE : OBJECT_COLOR);

        renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(object.object), Color.WHITE), 20, 20, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        this.parent.selectionManager().addIfIntersects(this, parentTrack.childBounds(this));
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size bounds) {
        if (this.parent.selectionManager().isSelected(this)) {
            return true;
        }

        if (!event.modifiers().contains(ModifierKey.CONTROL)) {
            this.parent.selectionManager().objects().clear();
        }

        this.parent.selectionManager().objects().add(this);
        return true;
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        float widthToAMil = parent.timelineContainer().screen().oneSecondWidth() / 1000f;
        return new Size(widthToAMil * object.duration, constraints.height());
    }
}
