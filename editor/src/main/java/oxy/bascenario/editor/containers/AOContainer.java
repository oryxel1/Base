package oxy.bascenario.editor.containers;

import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.ao.AOTabContainer;
import oxy.bascenario.editor.containers.ao.tab.AoMediaTab;
import oxy.bascenario.editor.containers.ao.tab.AoObjectTab;
import oxy.bascenario.editor.containers.ao.tab.AoTextTab;

// Object & Actions container
@Accessors(fluent = true)
public class AOContainer extends Container {
    private final ScenarioEditorScreen screen;
    private Component currentTab;

    public void tab(Tab tab) {
        this.removeChild(currentTab);
        currentTab = switch (tab) {
            case Text -> new AoTextTab(screen);
            case Object -> new AoObjectTab(screen);
            default -> null;
        };

        if (currentTab != null) {
            this.addChild(currentTab, c -> c.layoutOptions(BorderPosition.BOTTOM));
        }
    }

    public AOContainer(ScenarioEditorScreen screen) {
        super(BorderLayout.INSTANCE);
        this.screen = screen;

        this.addChild(new AOTabContainer(this), c -> c.layoutOptions(BorderPosition.TOP));
        this.addChild(new AoMediaTab(), c -> c.layoutOptions(BorderPosition.BOTTOM));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
    }

    public enum Tab {
        Media, Text, Object, Transition, Actions
    }
}
