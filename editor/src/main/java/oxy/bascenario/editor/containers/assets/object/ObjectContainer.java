package oxy.bascenario.editor.containers.assets.object;

import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.utils.TimeCompiler;
import oxy.bascenario.utils.components.TextWithName;

public class ObjectContainer extends ScrollContainer {
    public ObjectContainer() {
        final Container container = new Container(new VerticalListLayout(10, false));
        super(container);

        add(container, "Preview", "assets/base/uis/editor/objects/preview.png", new Preview(FontType.NotoSans, "Title", "Subtitle", null));
        add(container, "Location Info", "assets/base/uis/editor/objects/location-info.png", new LocationInfo(FontType.NotoSans, "Location A", 1000, 200));
        add(container, "Emoticon", "assets/base/uis/editor/objects/emoticon.png", new Emoticon(100, EmoticonType.ANGRY, true));
    }

    private void add(Container container, String name, String path, Object object) {
        final TextWithName component = new TextWithName(name, path);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }

        long fDuration = duration;
//        component.mouseDownListener().add((ignored, bounds) -> {
//            FakeObjectComponent ghost = new FakeObjectComponent(screen.timelineContainer(), object, fDuration);
//            rivet().dragAndDropManager().startDrag(ghost, ghost, 0, -60 / 2f);
//            return true;
//        });

        container.addChild(component);
    }
}
