import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.Container;
import net.lenni0451.rivet.component.base.Button;
import net.lenni0451.rivet.component.base.ComboBox;
import net.lenni0451.rivet.component.base.DecoratedContainer;
import net.lenni0451.rivet.component.base.ScrollContainer;
import net.lenni0451.rivet.component.impl.*;
import net.lenni0451.rivet.component.impl.graphics.SolidColor;
import net.lenni0451.rivet.component.impl.slider.Slider;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.layout.grid.GridAnchor;
import net.lenni0451.rivet.layout.grid.GridFill;
import net.lenni0451.rivet.layout.grid.GridLayout;
import net.lenni0451.rivet.layout.grid.GridLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.text.model.TextOrigin;
import net.lenni0451.rivet.theme.Theme;
import net.lenni0451.rivet.theme.impl.DefaultDark;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.Launcher;

public class RivetTest {
    public static void main(String[] args) {
        Launcher.launch(new ExtendableScreen() {
            @Override
            public void init(Rivet rivet) {
                rivet.theme(new DefaultDark() {
                    @Override
                    protected void addValues(final Rivet rivet, final Values values) {
                        super.addValues(rivet, values);
                        values.put(Theme.BUTTON_INACTIVE_COLOR, Color.GRAY.withAlpha(50));
                        values.put(Theme.BUTTON_INACTIVE_OUTLINE_COLOR, Color.BLACK);
                        values.put(Theme.BUTTON_ACTIVE_COLOR, Color.GRAY.withAlpha(150));
                        values.put(Theme.BUTTON_ACTIVE_OUTLINE_COLOR, Color.fromRGB(116, 165, 229));
                        values.put(Theme.BUTTON_CLICK_COLOR, Color.GRAY.withAlpha(150).darker());
                        values.put(Theme.BUTTON_CLICK_OUTLINE_COLOR, Color.fromRGB(116, 165, 229).darker());
                        values.put(Theme.BUTTON_CORNER_RADIUS, 0F);
                        values.put(Theme.BUTTON_OUTLINE_WIDTH, 3F);
                        values.put(Theme.SCROLL_BAR_TYPE, ScrollContainer.ScrollBarType.NORMAL);
                    }
                });


                Container container = new Container(rivet, new GridLayout(10, 10).withHomogeneousColumns(true).withShrinkColumns(true));
                rivet.root().addChild(new DecoratedContainer(rivet, new SolidColor(rivet, solidColor -> {
                    solidColor.color(Color.fromARGB(Integer.MIN_VALUE));
                    solidColor.cornerRadius(20F);
                    solidColor.outlineColor(Color.GREEN);
                }), new ScrollContainer(rivet, container, false, true)), decoratedContainer -> {
                    decoratedContainer.innerPadding(new Padding(20, 20, 20, 20));
                });
                container.addChild(new FormattedLabel(rivet, "Hello this is a really cool test string how are you doing lol"), label -> {
                    label.horizontalOrigin(TextOrigin.Horizontal.VISUAL_LEFT);
                    label.layoutOptions(new GridLayoutOptions(0, 0).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Singleplayer"), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(0, 1).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Minecraft Realms"), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(0, 2).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Multiplayer"), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(1, 2).withAnchor(GridAnchor.EAST).withWeightX(1).withFill(GridFill.HORIZONTAL));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Whatever"), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(0, 3).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Options..."), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(0, 4).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withPadding(Padding.EMPTY.withTop(20)));
                });
                container.addChild(new Button(rivet, new Label(rivet, "Quit Game"), _ -> {}), button -> {
                    button.layoutOptions(new GridLayoutOptions(1, 4).withAnchor(GridAnchor.EAST).withWeightX(1).withFill(GridFill.HORIZONTAL).withPadding(Padding.EMPTY.withTop(20)));
                });
                container.addChild(new ComboBox(rivet, "Testing since 2k26", new DecoratedContainer(rivet, new SolidColor(rivet, solidColor -> {
                    solidColor.color(Color.GREEN);
                }), new ScrollContainer(rivet, new Container(rivet, new VerticalListLayout(5, true)), comboBoxContainer -> {
                    for (int i = 0; i < 10; i++) {
                        comboBoxContainer.addChild(new Button(rivet, new Label(rivet, "Test " + i), _ -> {}));
                    }
                }))), comboBox -> comboBox.layoutOptions(new GridLayoutOptions(0, 5).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2)));
                container.addChild(new TextField(rivet), textField -> {
                    textField.layoutOptions(new GridLayoutOptions(0, 6).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                    textField.keyDownListener().add(event -> {
                        if (event.key().isEquivalent(Key.ENTER)) {
                            System.out.println(textField.text());
                            return true;
                        }
                        return false;
                    });
                });
                container.addChild(new Checkbox(rivet, "Test Test Test", true), checkbox -> {
                    checkbox.layoutOptions(new GridLayoutOptions(0, 7).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                    checkbox.cornerRadius().set(8F);
                });
                container.addChild(new Slider(rivet, 0, 100, 1), slider -> {
                    slider.layoutOptions(new GridLayoutOptions(0, 8).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                });
                container.addChild(new ColorPicker(rivet, Color.RED), colorPicker -> {
                    colorPicker.layoutOptions(new GridLayoutOptions(0, 9).withAnchor(GridAnchor.WEST).withWeightX(1).withFill(GridFill.HORIZONTAL).withColumnSpan(2));
                });
            }
        }, false);
    }
}
