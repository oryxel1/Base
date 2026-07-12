package oxy.bascenario.editor.containers.ao.tab;

import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.api.render.elements.text.AnimatedText;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.object.FakeObjectComponent;
import oxy.bascenario.utils.components.TypingLabel;

import java.util.List;

public class AoTextTab extends ScrollContainer {
    public AoTextTab(ScenarioEditorScreen screen) {
        final Container container = new Container(new VerticalListLayout(10, false));
        super(container);

        final Button defaultText = new Button(new Label("Default Text").scale(0.7f), ignored -> {});
        defaultText.mouseDownListener().add((ignored, size) -> {
            final Object object = new Text(List.of(TextSegment.builder().text("Default Text").build()), 42);
            FakeObjectComponent ghost = new FakeObjectComponent(screen.timelineContainer(), object, 1000L, 0, 0);

            rivet().dragAndDropManager().startDrag(
                    ghost,
                    ghost,
                    0,
                    -30 / 2f
            );
            return true;
        });

        container.addChild(defaultText);

        final Button typingText = new Button(new TypingLabel("Typing Text").scale(0.7f), ignored -> {});
        typingText.mouseDownListener().add((ignored, bounds) -> {
            final Object object = new AnimatedText(1, List.of(TextSegment.builder().text("Typing Text").build()), 42);
            FakeObjectComponent ghost = new FakeObjectComponent(screen.timelineContainer(), object, 1000L, 0, 0);

            rivet().dragAndDropManager().startDrag(
                    ghost,
                    ghost,
                    0,
                    -60 / 2f
            );
            return true;
        });
        container.addChild(typingText);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), Math.max(0, constraints.height() - 40));
    }
}
