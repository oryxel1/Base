package oxy.base.editor.containers.dopesheet;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.layer.Layer;
import net.lenni0451.rivet.layer.LayerBucket;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.base.api.effects.EaseType;
import oxy.base.api.effects.Easing;
import oxy.base.editor.EditorValues;
import oxy.base.editor.containers.dopesheet.other.KeyframeOptions;
import oxy.base.editor.object.values.KeyframeValue;
import oxy.base.utils.math.Pair;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DopeKeyframeComponent extends Component {
    private Layer layer;

    private final Runnable selfRemove;
    private final long timestamp;
    private final KeyframeValue.Keyframe keyframe;

    @Override
    public void renderInternal(Renderer renderer, Size size) {
        renderer.fillCircle(5, 5, 5, Color.fromRGB(255, 190, 51));
        renderer.outlineCircle(5, 5, 5, 1, Color.BLACK);
    }

    protected void updateComponentPosition(Rectangle bounds) {
        if (layer == null) {
            return;
        }

        rivet().removeLayer(layer);
        layer = null;
    }

    @Override
    protected void onRemovedInternal() {
        if (layer == null) {
            return;
        }

        rivet().removeLayer(layer);
        layer = null;
    }

    @Override
    protected boolean onMouseDownInternal(MouseButtonEvent event, Size size) {
        if (event.button() != MouseButton.RIGHT) {
            return true;
        }

        while (rivet().layers().size() > 1) {
            rivet().removeLayer(rivet().layers().getLast());
        }

        final Container container = new Container(AbsoluteLayout.INSTANCE);

        Rectangle bounds = this.absoluteBounds();

        Container childContainer = new Container(new VerticalListLayout(2, true));
        final DecoratedContainer decoratedContainer = new DecoratedContainer(new SolidColor(Color.fromRGB(24, 24, 24)).cornerRadius(5).outlineColor(Color.fromRGB(36, 36, 36)).outlineWidth(1f), childContainer);
        decoratedContainer.maxSize(185f, Float.MAX_VALUE);

        decoratedContainer.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y() + 20));
        container.add(decoratedContainer);

        childContainer.add(new KeyframeOptions("Easing Mode", List.of(
                new Pair<>("Ease In", () -> keyframe.easeType(EaseType.EASE_IN)),
                new Pair<>("Ease Out", () -> keyframe.easeType(EaseType.EASE_OUT)),
                new Pair<>("Ease In and Out", () -> keyframe.easeType(EaseType.EASE_IN_OUT))
        )));

        final List<Pair<String, Runnable>> otherList = new ArrayList<>();
        otherList.add(new Pair<>("Instant", () -> keyframe.easing(null)));
        for (Easing easing : Easing.values()) {
            otherList.add(new Pair<>(easing.getName(), () -> keyframe.easing(easing)));
        }
        childContainer.add(new KeyframeOptions("Interpolation Mode", otherList));
        childContainer.add(new Button("Snap Cursor To Keyframe", e -> {
            rivet().removeLayer(layer);
            layer = null;
            EditorValues.instance().timestamp(timestamp);
        }), button -> ((Label)button.child()).scale(0.8f));
        childContainer.add(new Button("Delete Keyframe", e -> {
            rivet().removeLayer(layer);
            layer = null;
            selfRemove.run();
        }));

        this.layer = new Layer(container, LayerBucket.OVERLAY);
        this.rivet().addLayer(this.layer);

        return true;
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(12, 12);
    }
}
