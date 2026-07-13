package oxy.bascenario.editor.containers.ao.tab;

import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.text.AnimatedText;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.utils.components.TextWithNameNoImage;
import oxy.bascenario.utils.components.TypingLabel;
import oxy.bascenario.utils.drag.FakeObjectComponent;
import oxy.bascenario.utils.TimeCompiler;
import oxy.bascenario.utils.components.TextWithName;

import java.util.List;

public class AoObjectTab extends ScrollContainer {
    public AoObjectTab(ScenarioEditorScreen screen) {
        final Container container = new Container(new VerticalListLayout(10, false));
        super(container);

        add(screen, container, "Preview", "assets/base/uis/editor/objects/preview.png", new Preview(FontType.NotoSans, "Title", "Subtitle", null));
        add(screen, container, "Location Info", "assets/base/uis/editor/objects/location-info.png", new LocationInfo(FontType.NotoSans, "Location A", 1000, 200));
        add(screen, container, "Emoticon", "assets/base/uis/editor/objects/emoticon.png", new Emoticon(100, EmoticonType.ANGRY, true));
        add(screen, container, "Text", new Label("Default Text").scale(0.6f), new Text(List.of(TextSegment.builder().text("Text").build()), 42));
        add(screen, container, "Typing Text", new TypingLabel("Typing Text").scale(0.6f), new AnimatedText(1, List.of(TextSegment.builder().text("Typing Text").build()), 42));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), Math.max(0, constraints.height() - 40));
    }

    private void add(ScenarioEditorScreen screen, Container container, String name, Component c, Object object) {
        final TextWithNameNoImage component = new TextWithNameNoImage(name, c);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        long fDuration = duration;
        component.mouseDownListener().add((ignored, bounds) -> {
            FakeObjectComponent ghost = new FakeObjectComponent(screen.timelineContainer(), object, fDuration);
            rivet().dragAndDropManager().startDrag(ghost, ghost, 0, -60 / 2f);
            return true;
        });

        container.addChild(component);
    }

    private void add(ScenarioEditorScreen screen, Container container, String name, String path, Object object) {
        final TextWithName component = new TextWithName(name, path);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        long fDuration = duration;
        component.mouseDownListener().add((ignored, bounds) -> {
            FakeObjectComponent ghost = new FakeObjectComponent(screen.timelineContainer(), object, fDuration);
            rivet().dragAndDropManager().startDrag(ghost, ghost, 0, -60 / 2f);
            return true;
        });

        container.addChild(component);
    }
}
