package oxy.bascenario.editor.containers;

import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Padding;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.ao.AOTabContainer;
import oxy.bascenario.editor.containers.ao.tab.AoMediaTab;
import oxy.bascenario.editor.containers.ao.tab.AoObjectTab;

// Object & Actions container
@Accessors(fluent = true)
public class AOContainer extends Container {
    private final ScenarioEditorScreen screen;
    private Component currentTab;

    public void tab(Tab tab) {
        this.removeChild(currentTab);
        currentTab = switch (tab) {
            case Object -> new AoObjectTab(screen);
            default -> null;
        };
        if (currentTab != null) {
            final DecoratedContainer container = new DecoratedContainer(new SolidColor(), currentTab);
            container.innerPadding(new Padding(0, 0, 10, 0));
            currentTab = container;

            this.addChild(currentTab, c -> c.layoutOptions(BorderPosition.CENTER));
        }
    }

    public AOContainer(ScenarioEditorScreen screen) {
        super(BorderLayout.INSTANCE);
        this.screen = screen;

        // Just to take space.
        this.addChild(new Component() {
            @Override
            public Size computeIdealSize(Size size) {
                return new Size(20, 20);
            }
        }, c -> c.layoutOptions(BorderPosition.LEFT));

        this.addChild(new DecoratedContainer(new SolidColor(), new AOTabContainer(this)), c -> {
            c.layoutOptions(BorderPosition.TOP);
            c.innerPadding(new Padding(20, 10, 20, 0));
        });

        this.addChild(new AoMediaTab(), c -> c.layoutOptions(BorderPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
    }

    public enum Tab {
        Media, Object, Transition, Actions
    }
}
