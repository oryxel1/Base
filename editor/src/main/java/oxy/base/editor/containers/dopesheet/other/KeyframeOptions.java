package oxy.base.editor.containers.dopesheet.other;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layer.Layer;
import net.lenni0451.rivet.layer.LayerBucket;
import net.lenni0451.rivet.layout.Layout;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.layout.flow.VerticalFlowLayout;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.utils.math.Pair;

import java.util.List;

@RequiredArgsConstructor
public class KeyframeOptions extends Component {
    private final String name;
    private final List<Pair<String, Runnable>> options;

    private Layer layer;
    private boolean over;

    @Override
    public void render(Renderer renderer, Size size) {
        final ShapedText text = rivet().backend().font().derive(12).shapeText(name, Color.WHITE);
        if (over) {
            renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(63, 63, 63));
        }

        renderer.text(text, 5, 5, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP);
    }

    @Override
    protected void onComponentRemoved() {
        removeLayer();
    }

    @Override
    protected void onComponentMouseEnter() {
        over = true;

        if (!rivet().layers().contains(layer)) {
            layer = null;
        }

        if (layer != null) {
            removeLayer();
            return;
        }

        final Container container = new Container(AbsoluteLayout.INSTANCE);

        Rectangle bounds = this.absoluteBounds();

        final Layout layout;
        if (options.size() > 3) {
            layout = new VerticalFlowLayout(2, 2);
        } else {
            layout = new VerticalListLayout(2, true);
        }

        Container childContainer = new Container(layout);
        final DecoratedContainer decoratedContainer = new DecoratedContainer(new SolidColor(Color.fromRGB(24, 24, 24)).cornerRadius(5).outlineColor(Color.fromRGB(36, 36, 36)).outlineWidth(1f), childContainer);
        decoratedContainer.minSize(185f, 0f);

        decoratedContainer.layoutOptions(new AbsoluteOptions(bounds.x() + 190, bounds.y(), null, layout instanceof VerticalListLayout ? null : 100f));
        container.addChild(decoratedContainer);

        for (Pair<String, Runnable> option : options) {
            childContainer.addChild(new Button(option.left(), c -> {
                removeLayer();
                if (rivet().layers().size() > 1) {
                    rivet().removeLayer(rivet().layers().getLast());
                }

                option.right().run();
            }), b -> {
                ((Label)b.child()).scale(0.75f);
                ((Label)b.child()).horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
                b.outlineColor().set(Color.TRANSPARENT);
                b.hoverOutlineColor().set(Color.TRANSPARENT);
            });
        }

        if (this.rivet().layers().size() >= 3) {
            this.rivet().removeLayer(this.rivet().layers().getLast());
        }
        this.layer = new Layer(container, LayerBucket.OVERLAY);
        this.rivet().addLayer(this.layer);
    }

    @Override
    protected void onComponentMouseLeave() {
        over = false;
//        removeLayer();
    }

    private void removeLayer() {
        if (layer != null) {
            rivet().removeLayer(layer);
            layer = null;
        }
    }

    @Override
    public Size computeIdealSize(Size size) {
        return size.withHeight(20f);
    }
}
