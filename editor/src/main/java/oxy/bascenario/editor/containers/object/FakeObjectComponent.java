package oxy.bascenario.editor.containers.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.utils.NameUtils;

@AllArgsConstructor
@Accessors(fluent = true)
public class FakeObjectComponent extends Component {
    private final TimelineContainer timelineContainer;
    private final Object object;
    private final long duration;

    @Getter
    private float offsetX, offsetY;

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.translate(offsetX, offsetY, () -> {
            renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(30, 30, 30));
            renderer.outlineRect(0, 0, bounds.width(), bounds.height(), 2, Color.fromRGB(145, 218, 255).darker());

            renderer.scale(0.4f, () -> renderer.text(this.rivet().backend().font().shapeText(NameUtils.name(object), Color.WHITE), 20, 20, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));
        });
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        float widthToAMil = timelineContainer.screen().oneSecondWidth() / 1000f;
        return new Size(widthToAMil * duration, 60);
    }
}
