package oxy.bascenario.utils.components;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.component.impl.TextField;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.layer.Layer;
import net.lenni0451.rivet.layer.LayerBucket;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.layout.absolute.AbsoluteOptions;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridLayout;
import net.lenni0451.rivet.layout.grid.GridOptions;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.theme.ThemeOption;
import oxy.bascenario.utils.ColorUtils;


public class AdvancedColorPicker extends Component {
    private Layer layer;

    private DecoratedContainer container;
    private final ColorWheelPicker picker;
    private final TextField hexField;
    private final ColorValuesComponent colorValuesComponent;

    private final ThemeOption<Float> height = new ThemeOption<>(this, null);

    // Please don't comment on this part, it's easier to do this.
    public AdvancedColorPicker(Color color) {
        this.height.set(20f);

        this.picker = new ColorWheelPicker(color);
        this.colorValuesComponent = new ColorValuesComponent(color);

        this.hexField = new TextField(ColorUtils.toHex(color)) {
            @Override
            protected void onComponentFocusLost() {
                super.onComponentFocusLost();

                correct();
            }

            @Override
            protected boolean onComponentKeyUp(KeyEvent event) {
                if (event.key() == Key.ENTER) {
                    correct();
                }

                return super.onComponentKeyUp(event);
            }

            private void correct() {
                String s = text();
                s = s.startsWith("#") ? s : "#" + s;
                try {
                    Color c = ColorUtils.fromHex(s);
                    picker.color(c);
                } catch (Exception ignored) {
                }
                text(ColorUtils.toHex(picker.color()));
            }
        };
        this.hexField.cornerRadius().set(5f);
        this.hexField.outlineColor().set(Color.fromRGB(61, 61, 61));

        this.picker.colorChangeListener().add(c -> {
            this.hexField.text(ColorUtils.toHex(c));
            this.colorValuesComponent.color(c);
        });
        this.colorValuesComponent.colorChangeListener().add(this.picker::color);
    }

    @Override
    public void render(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), 5, picker.color());
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(),  5, 1, Color.GRAY);

        if (this.layer != null &&
                (rivet().focusedComponent() == null
                        || rivet().focusedComponent() != this &&
                        rivet().focusedComponent() != this.layer.container() &&
                        rivet().focusedComponent().parent() != this.container.child() &&
                        rivet().focusedComponent() != this.container.background()
                )) {
            this.rivet().removeLayer(this.layer);
            this.layer = null;
            this.container = null;
        }
    }

    @Override
    protected void updateComponentPosition(Rectangle bounds) {
        if (this.container != null) {

            this.container.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y() + height.value() + 2));
        }
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT && layer == null) {
            final Container layerContainer = new Container(AbsoluteLayout.INSTANCE);

            Rectangle bounds = this.absoluteBounds();

            Container container = new Container(GridLayout.DEFAULT);
            SolidColor color = new SolidColor(Color.fromRGB(24, 24, 24));
            color.cornerRadius(5f);
            color.outlineColor(Color.fromRGB(36, 36, 36));

            this.container = new DecoratedContainer(color, container);
            this.container.innerPadding(new Padding(10, 10, 10 , 10));

            this.container.layoutOptions(new AbsoluteOptions(bounds.x(), bounds.y() + height.value() + 2));

            {
                if (picker.parent() != null) {
                    picker.setRivet(null, null);
                }
                if (hexField.parent() != null) {
                    hexField.setRivet(null, null);
                }

                final Label hex = new Label("Hex").scale(0.8f);
                hex.textColor().set(Color.GRAY);

                hexField.font(rivet().backend().font().derive(14));

                container.addChild(picker, c -> c.layoutOptions(new GridOptions(0, 0).withColumnSpan(2)));
                container.addChild(hex, c -> c.layoutOptions(new GridOptions(0, 1)
                        .withPadding(new Padding(5, 15, 0, 0)).withAnchor(GridAnchor.LEFT)));
                container.addChild(hexField, c -> c.layoutOptions(new GridOptions(1, 1)
                        .withPadding(new Padding(5, 15, 0, 0)).withAnchor(GridAnchor.LEFT)));
//
                container.addChild(colorValuesComponent, c -> c.layoutOptions(new GridOptions(0, 2)
                        .withPadding(new Padding(5, 15, 0, 0)).withAnchor(GridAnchor.LEFT).withColumnSpan(2)));


//                container.addChild(redField, c -> c.layoutOptions(new GridOptions(0, 2)
//                        .withPadding(new Padding(0, 15, 0, 0))));
//                container.addChild(greenField, c -> c.layoutOptions(new GridOptions(0, 3)));
//                container.addChild(blueField, c -> c.layoutOptions(new GridOptions(0, 4)));
//                container.addChild(alphaField, c -> c.layoutOptions(new GridOptions(0, 5)));
            }

            layerContainer.addChild(this.container);

            this.layer = new Layer(layerContainer, LayerBucket.OVERLAY);
            this.rivet().addLayer(this.layer);
        }

        return true;
    }

    @Override
    public Size computeIdealSize(Size size) {
        return size.withHeight(height.value()).withWidth(size.width() - 8f); // Don't comment on this.
    }

    private Color color() {
        return picker.color();
    }
}
