package oxy.bascenario.editor;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.DecoratedContainer;
import net.lenni0451.rivet.component.container.PaddedContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Padding;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.containers.DockBarContainer;
import oxy.bascenario.editor.containers.GlobalContainer;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.thingl.ThinGLUtils;

public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario.Builder scenario;

    public ScenarioEditorScreen(Scenario.Builder builder) {
        EditorValues.instance(new EditorValues());

        this.scenario = builder;
    }

    @Override
    public void renderBehindRivet() {
        EditorValues.instance().tick();

        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.fromRGB(24, 24, 24));
    }

    @Override
    public void init(Rivet rivet) {
        Container mainContainer = new Container(new DockLayout());
        rivet.root().addChild(mainContainer);

        // Dock bar (File, Edit, ...)
        mainContainer.addChild(new DockBarContainer(), c -> c.layoutOptions(DockPosition.TOP));
        mainContainer.addChild(new PaddedContainer(new Padding(2, 8, 2, 10), new GlobalContainer()), c -> c.layoutOptions(DockPosition.CENTER));
        System.out.println("Lol!");
    }
}
