package oxy.bascenario.editor.containers.assets.object;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.flow.HorizontalFlowLayout;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.api.render.elements.text.AnimatedText;
import oxy.bascenario.api.render.elements.text.Text;
import oxy.bascenario.api.render.elements.text.TextSegment;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.editor.drag.FakeObjectComponent;
import oxy.bascenario.utils.TimeCompiler;
import oxy.bascenario.utils.components.TextWithName;
import oxy.bascenario.utils.components.TextWithNameNoImage;
import oxy.bascenario.utils.components.TypingLabel;

import java.util.List;

public class ObjectContainer extends ScrollContainer {
    public ObjectContainer() {
        final Container container = new Container(new HorizontalFlowLayout(20, 15));
        super(container);

        add(container, "Preview", "assets/base/uis/editor/objects/preview.png", new Preview(FontType.NotoSans, "Title", "Subtitle", null));
        add(container, "Location Info", "assets/base/uis/editor/objects/location-info.png", new LocationInfo(FontType.NotoSans, "Location A", 1000, 200));
        add(container, "Emoticon", "assets/base/uis/editor/objects/emoticon.png", new Emoticon(100, EmoticonType.ANGRY, true));

        add(container, "Text", new Label("Text"), new Text(List.of(TextSegment.builder().text("Text").build()), 42));
        add(container, "Typing Text", new TypingLabel("Typing Text"), new AnimatedText(1, List.of(TextSegment.builder().text("Typing Text").build()), 42));

        add(container, "Circle", "assets/base/uis/editor/objects/circle.png", new Circle(5, Color.WHITE, false));
        add(container, "Rectangle", "assets/base/uis/editor/objects/rectangle.png", new Rectangle(500, 500, Color.WHITE, false));
    }

    private void add(Container container, String name, Component c, Object object) {
        final TextWithNameNoImage component = new TextWithNameNoImage(name, c);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        long fDuration = duration;
        component.mouseDownListener().add((ignored, bounds) -> {
            FakeObjectComponent ghost = new FakeObjectComponent(object, fDuration);
            rivet().dragAndDropManager().startDrag(ghost, ghost, 0, -60 / 2f);
            return true;
        });

        container.addChild(component);
    }

    private void add(Container container, String name, String path, Object object) {
        final TextWithName component = new TextWithName(name, path);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        long fDuration = duration;
        component.mouseDownListener().add((ignored, bounds) -> {
            FakeObjectComponent ghost = new FakeObjectComponent(object, fDuration);
            rivet().dragAndDropManager().startDrag(ghost, ghost, 0, -60 / 2f);
            return true;
        });

        container.addChild(component);
    }
}
