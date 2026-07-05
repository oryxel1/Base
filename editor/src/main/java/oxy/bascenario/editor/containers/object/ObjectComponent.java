package oxy.bascenario.editor.containers.object;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.keyboard.ModifierKey;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.track.TrackContainer;
import oxy.bascenario.editor.containers.track.TrackListContainer;
import oxy.bascenario.util.NameUtils;

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
        this.parent.selectionManager().prevX(event.x());
        this.parent.selectionManager().prevY(event.y());
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
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        if (!event.buttons().contains(MouseButton.LEFT)) {
            return true;
        }

        float delta = (event.x() - this.parent.selectionManager().prevX());
        this.parent.selectionManager().prevX(event.x());
        this.parent.selectionManager().prevY(event.y());
        System.out.println(event.x());

//        if (Math.abs(delta) < 3) {
//            return true;
//        }

        final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * parent.timelineContainer().screen().scale();
        for (Component baseComponent : this.parentTrack.children()) {
            if (!(baseComponent instanceof ObjectComponent component) || !this.parent.selectionManager().isSelected(component)) {
                continue;
            }

            Rectangle rectangle = this.parentTrack.childBounds(component);
            float newX = rectangle.x() + delta;

            component.object.start = (long) ((newX / this.parentTrack.relativeBounds().width()) * maxTime);
            component.object.start = Math.max(0, component.object.start);
            newX = TimelineContainer.timestampToPosition(
                    component.object.start,
                0,
                    this.parentTrack.relativeBounds().width(),
                    this.parent.timelineContainer().screen().scale(),
                    this.parent.timelineContainer().screen().scroll()
            );

            component.layoutOptions(new AbsoluteLayoutOptions(newX, 0));
        }

        return true;
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        final float maxTime = ScenarioEditorScreen.DEFAULT_MAX_TIME * parent.timelineContainer().screen().scale();
        return new Size((object.duration / maxTime) * constraints.width(), constraints.height());
    }
}
