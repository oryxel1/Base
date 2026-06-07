package oxy.bascenario.editor.containers.ao.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.grid.GridLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.api.render.elements.text.AnimatedText;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.util.components.TypingLabel;

import java.util.List;

public class AoTextTab extends ScrollContainer {
    public AoTextTab() {
        final Container container = new Container(new VerticalListLayout(10, false));
        super(container);

        final Button defaultText = new Button(new Label("Default Text").scale(0.7f), ignored -> {});
        defaultText.mouseDownListener().add((ignored, bounds) -> {
            Button ghost = new Button(new Label("Default Text").scale(0.7f), _ -> {});
            ghost.interactive(false);
            ghost.inactiveColor().set(Color.fromRGB(35, 35, 35).withAlphaF(0.5f));

            rivet().dragAndDropManager().startDrag(
                    new Text(List.of(TextSegment.builder().text("Default Text").build()), 42),
                    ghost,
                    new Size(bounds.width(), bounds.height()),
                    0,
                    -bounds.height() / 2f
            );
            return true;
        });

        container.addChild(defaultText);

        final Button typingText = new Button(new TypingLabel("Typing Text").scale(0.7f), ignored -> {});
        typingText.mouseDownListener().add((ignored, bounds) -> {
            Button ghost = new Button(new TypingLabel("Typing Text").scale(0.7f), _ -> {});
            ghost.interactive(false);
            ghost.inactiveColor().set(Color.fromRGB(35, 35, 35).withAlphaF(0.5f));

            rivet().dragAndDropManager().startDrag(
                    new AnimatedText(1, List.of(TextSegment.builder().text("Typing Text").build()), 42),
                    ghost,
                    new Size(bounds.width(), bounds.height()),
                    0,
                    -bounds.height() / 2f
            );
            return true;
        });
        container.addChild(typingText);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), constraints.height() - 40);
    }
}
