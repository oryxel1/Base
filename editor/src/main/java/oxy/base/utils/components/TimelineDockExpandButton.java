package oxy.base.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.ComboBox;
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
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.base.Base;
import oxy.base.editor.EditorValues;
import oxy.base.utils.animation.math.ColorAnimations;

public class TimelineDockExpandButton extends Component {
    private Layer layer;

    private final ColorAnimations backgroundColor = new ColorAnimations(Color.fromRGB(40, 40, 40));
    private final ThinGLTexture downTexture = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_downarrow_hlt.svg"));

    private final ThinGLTexture sequenceTexture = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_sequence.svg"));
    private final ThinGLTexture actionTexture = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_action.svg"));

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, backgroundColor.color());
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(60, 60, 60));

        renderer.image(downTexture, 112, 6, downTexture.width() * .7f, downTexture.height()  * .7f, Color.WHITE);

        if (EditorValues.instance().type() == EditorValues.TimelineType.Sequencer) {
            renderer.image(sequenceTexture, 5, 0, sequenceTexture.width() * .9f, sequenceTexture.height() * .9f, Color.WHITE);
        } else {
            renderer.image(actionTexture, 5, 3, actionTexture.width() * .9f, actionTexture.height() * .9f, Color.WHITE);
        }

        String current = EditorValues.instance().type().name().replace("_", " ");
        renderer.text(rivet().backend().font().derive(12).shapeText(current, Color.WHITE), 30, 11, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);
    }

    protected void updateComponentPosition(Rectangle bounds) {
        if (layer == null) {
            return;
        }

        rivet().removeLayer(layer);
        layer = null;
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size size) {
        if (event.button() != MouseButton.LEFT) {
            return false;
        }

        if (layer != null) {
            rivet().removeLayer(layer);
            layer = null;
            return true;
        }

        final Container container = new Container(AbsoluteLayout.INSTANCE);

        Rectangle bounds = this.absoluteBounds();

        Container childContainer = new Container(new VerticalListLayout(2, true));
        final DecoratedContainer decoratedContainer = new DecoratedContainer(new SolidColor(Color.fromRGB(24, 24, 24)).cornerRadius(5).outlineColor(Color.fromRGB(36, 36, 36)).outlineWidth(1f), childContainer);
        decoratedContainer.minSize(size.width(), 0f);

        decoratedContainer.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y() + 20));
        container.addChild(decoratedContainer);

        childContainer.addChild(new Button("Sequencer", c -> {
            this.rivet().removeLayer(this.layer);
            layer = null;

            EditorValues.instance().type(EditorValues.TimelineType.Sequencer);
        }), b -> {
            ((Label)b.child()).scale(0.75f);
            ((Label)b.child()).horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
            b.inactiveOutlineColor().set(Color.TRANSPARENT);
            b.activeOutlineColor().set(Color.TRANSPARENT);
            b.inactiveColor().set(Color.TRANSPARENT);
        });

        childContainer.addChild(new Button("Dope Sheet", c -> {
            this.rivet().removeLayer(this.layer);
            layer = null;

            EditorValues.instance().type(EditorValues.TimelineType.Dope_Sheet);
        }), b -> {
            ((Label)b.child()).scale(0.75f);
            ((Label)b.child()).horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
            b.inactiveOutlineColor().set(Color.TRANSPARENT);
            b.activeOutlineColor().set(Color.TRANSPARENT);
            b.inactiveColor().set(Color.TRANSPARENT);
        });

        this.layer = new Layer(container, LayerBucket.OVERLAY);
        this.rivet().addLayer(this.layer);

        return true;
    }

    @Override
    protected void onComponentMouseEnter() {
        backgroundColor.set(Color.fromRGB(48, 48, 48), 500);
    }

    @Override
    protected void onComponentMouseLeave() {
        backgroundColor.set(Color.fromRGB(40, 40, 40), 500);
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(130, 20);
    }
}
