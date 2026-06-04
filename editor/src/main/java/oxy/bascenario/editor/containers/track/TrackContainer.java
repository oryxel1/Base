package oxy.bascenario.editor.containers.track;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayoutOptions;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.TimelineContainer;

@Accessors(fluent = true)
public class TrackContainer extends Container {
    private final TimelineContainer parent;

    @Setter @Getter
    private int index;
    private float height = 60f;

    public TrackContainer(TimelineContainer parent) {
        super(AbsoluteLayout.INSTANCE);
        this.parent = parent;

        this.addChild(new Button("Test", c -> {}).layoutOptions(new AbsoluteLayoutOptions(2000, 0)));
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
//        renderer.fillRect(0, 0, parent.trackListWidth(), bounds.height(), Color.RED); // here for now...

        renderer.scale(0.5f, () -> renderer.text(this.rivet().backend().shapeText("Track " + index, Color.WHITE), 10, 10, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));

        renderer.fillRoundedRect(parent.trackListWidth(), 0, bounds.width() + 100, bounds.height(), 5, Color.fromRGB(50, 50, 50));

        super.render(renderer, bounds);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size idealSize = super.computeIdealSize(new Size(constraints.width(), height));
        return new Size(Math.max(idealSize.width(), constraints.width()), height);
    }
}
