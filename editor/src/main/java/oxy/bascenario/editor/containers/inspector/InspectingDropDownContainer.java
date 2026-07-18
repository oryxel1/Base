package oxy.bascenario.editor.containers.inspector;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.backend.thingl.ThinGLTexture;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.container.PaddedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.layout.fullsize.FullSizeLayout;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.Base;

@Accessors(fluent = true)
public class InspectingDropDownContainer extends Container {
    private final String name;

    private boolean expanded;
    private final DynamicAnimation size = new DynamicAnimation(EasingFunction.CIRC, EasingMode.EASE_IN, 200, 0);

    private final ThinGLTexture downTexture = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_downarrow_hlt.svg"));
    private final ThinGLTexture rightTexture = new ThinGLTexture(Base.instance().assetsManager().texture("assets/base/uis/editor/icons/blender_icon_rightarrow_thin.svg"));

    @Getter
    private final Container container;
    public InspectingDropDownContainer(String name) {
        container = new Container(new VerticalListLayout(5, false));
        super(FullSizeLayout.INSTANCE);
        this.name = name;

        addChild(new PaddedContainer(new Padding(8, 30, 0, 0), container), c -> c.layoutOptions(BorderPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, Color.fromRGB(61, 61, 61));
        super.render(renderer, size);
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), 5, 1, Color.fromRGB(74, 74, 74));

        Color color = Color.fromRGB(184, 184, 184);
        if (expanded) {
            renderer.image(downTexture, 9, 9, downTexture.width() * .7f, downTexture.height() * .7f, color);
        } else {
            renderer.image(rightTexture, 11, 7, rightTexture.width() * .7f, rightTexture.height() * .7f, color);
        }

        ShapedText text = this.rivet().backend().font().shapeText(name, color);
        renderer.scale(0.35f, () -> renderer.text(text, 25 / .35f, 9.5f / .4f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_TOP));

        if (this.size.isRunning()) {
            requestLayoutRecalculation();
        }
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size size) {
        if (event.y() <= 25) {
            expanded = !expanded;
            this.size.setTarget(expanded ? 1 : 0);
        }

        return super.onComponentMouseDown(event, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        Size size = super.computeIdealSize(constraints);
        return constraints.withWidth(constraints.width() - 8f).withHeight(size.height() * this.size.getValue() + 25);
    }
}
