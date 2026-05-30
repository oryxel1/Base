package oxy.bascenario.editor;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.util.RivetUtil;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.math.Pair;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import java.util.List;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario scenario;

    @Override
    public void renderBehindRivet() {
        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.fromRGB(50, 50, 50));
    }

    @Override
    public void init(Rivet rivet) {
        Container container = new Container(AnchorLayout.INSTANCE);

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.cornerRadius(5);
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.01f).withAnchorMinY(0.06f).withAnchorMaxX(0.2f).withAnchorMaxY(0.6f));
        });

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.cornerRadius(5);
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.205f).withAnchorMinY(0.06f).withAnchorMaxX(0.8f).withAnchorMaxY(0.6f));
        });

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.cornerRadius(5);
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.805f).withAnchorMinY(0.06f).withAnchorMaxX(0.99f).withAnchorMaxY(0.6f));
        });

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.cornerRadius(5);
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.01f).withAnchorMinY(0.605f).withAnchorMaxX(0.99f).withAnchorMaxY(0.99f));
        });


        RivetUtil.dropDownList(container, "Timeline", AnchorLayoutOptions.EMPTY.withAnchorMinX(0.015f).withAnchorMinY(0.01f),
                List.of(
                        new Pair<>("Play", () -> {}),
                        new Pair<>("Pause", () -> {})
                )
        );
        rivet.root().addChild(container);
    }
}
