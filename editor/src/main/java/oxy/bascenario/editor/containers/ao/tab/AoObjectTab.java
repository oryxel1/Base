package oxy.bascenario.editor.containers.ao.tab;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.component.container.Button;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.render.elements.LocationInfo;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.api.render.elements.emoticon.EmoticonType;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.util.TimeCompiler;
import oxy.bascenario.util.components.TextWithName;

public class AoObjectTab extends ScrollContainer {
    public AoObjectTab(ScenarioEditorScreen screen) {
        final Container container = new Container(new VerticalListLayout(10, false));
        super(container);

        add(screen, container, "Preview", "assets/base/uis/editor/preview.png", new Preview(FontType.NotoSans, "Title", "Subtitle", null));
        add(screen, container, "Location Info", "assets/base/uis/editor/location-info.png", new LocationInfo(FontType.NotoSans, "Location A", 1000, 200));
        add(screen, container, "Emoticon", "assets/base/uis/editor/emoticon.png", new Emoticon(100, EmoticonType.ANGRY, true));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return new Size(constraints.width(), constraints.height() - 40);
    }

    private void add(ScenarioEditorScreen screen, Container container, String name, String path, Object object) {
        final TextWithName component = new TextWithName(name, path);

        long duration = TimeCompiler.compileTime(object);
        if (duration == Long.MAX_VALUE) {
            duration = 1000L;
        }
        float screenWidth = ThinGL.windowInterface().getFramebufferWidth();
        final float width = (0.99f * screenWidth - 0.15625f * screenWidth) * (duration / (ScenarioEditorScreen.DEFAULT_MAX_TIME * screen.scale()));

        component.mouseDownListener().add((ignored, bounds) -> {
            Button ghost = new Button(new Label(""), _ -> {});
            ghost.interactive(false);
            ghost.inactiveColor().set(Color.fromRGB(35, 35, 35).withAlphaF(0.5f));

            rivet().dragAndDropManager().startDrag(object,
                    ghost,
                    new Size(width, 30),
                    0,
                    -30 / 2f
            );
            return true;
        });

        container.addChild(component);
    }
}
