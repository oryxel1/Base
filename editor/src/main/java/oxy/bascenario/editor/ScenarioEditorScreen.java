package oxy.bascenario.editor;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.Renderer;
import net.lenni0451.rivet.component.Container;
import net.lenni0451.rivet.component.base.Button;
import net.lenni0451.rivet.component.base.ComboBox;
import net.lenni0451.rivet.component.base.ScrollContainer;
import net.lenni0451.rivet.component.impl.FormattedLabel;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.graphics.SolidColor;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.text.TextRun;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.util.RivetUtil;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.font.TextUtils;
import oxy.bascenario.utils.math.Pair;
import oxy.bascenario.utils.thingl.ThinGLUtils;

import java.util.List;

@RequiredArgsConstructor
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario scenario;

    @Override
    public void renderBehindRivet() {
        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0,
                ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.fromRGB(50, 50, 50));
    }

    @Override
    public void init(Rivet rivet) {
        Container container = new Container(rivet, AnchorLayout.INSTANCE);

//        final FormattedLabel projectName = new FormattedLabel(rivet, scenario.getName()) {
//            @Override
//            public void render(final Renderer renderer, final Rectangle bounds) {
//                renderer.scale(0.5f, () -> super.render(renderer, bounds));
//            }
//        };
//        projectName.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(1 -
//                (TextUtils.getVisualWidth(20, TextRun.fromString())) 0.015f).withAnchorMinY(0.015f));
//        container.addChild(projectName);

        RivetUtil.menuList(rivet, container, "Timeline", AnchorLayoutOptions.EMPTY.withAnchorMinX(0.015f).withAnchorMinY(0.01f),
                List.of(
                        new Pair<>("Play", () -> {}),
                        new Pair<>("Pause", () -> {})
                )
        );

        container.addChild(new SolidColor(rivet), c -> {
            c.color(Color.fromRGB(35, 35, 35));
//            c.cornerRadius(5);
            c.layoutOptions(new AnchorLayoutOptions(
                    0.01f, 0.2f, 0.06f, 0.6f,
                    0, 0, 0, 0,
                    0, 0
            ));
        });

        container.addChild(new SolidColor(rivet), c -> {
            c.color(Color.fromRGB(35, 35, 35));
//            c.cornerRadius(5);
            c.layoutOptions(new AnchorLayoutOptions(
                    0.205f, 0.8f, 0.06f, 0.6f,
                    0, 0, 0, 0,
                    0, 0
            ));
        });

        container.addChild(new SolidColor(rivet), c -> {
            c.color(Color.fromRGB(35, 35, 35));
//            c.cornerRadius(5);
            c.layoutOptions(new AnchorLayoutOptions(
                    0.805f, 0.99f, 0.06f, 0.6f,
                    0, 0, 0, 0,
                    0, 0
            ));
        });

        container.addChild(new SolidColor(rivet), c -> {
            c.color(Color.fromRGB(35, 35, 35));
//            c.cornerRadius(5);
            c.layoutOptions(new AnchorLayoutOptions(
                    0.01f, 0.99f, 0.605f, 0.99f,
                    0, 0, 0, 0,
                    0, 0
            ));
        });

//        container.addChild(new SolidColor(rivet), c -> {
//            c.color(Color.RED);
//            c.layoutOptions(new AnchorLayoutOptions(
//                    0, 1, 0.7F, 1F,
//                    0, 0, 0, 0,
//                    0, 0
//            ));
//        });
//
//        container.addChild(new SolidColor(rivet), c -> {
//            c.color(Color.GREEN);
//            c.layoutOptions(new AnchorLayoutOptions(
//                    0, 0.2F, 0, 0.7F,
//                    0, 0, 0, 0,
//                    0, 0
//            ));
//        });
//        container.addChild(new ScrollContainer(rivet, new Container(rivet, new VerticalListLayout(5, true)), c -> {
//            for (int i = 0; i < 10; i++) {
//                c.addChild(new Button(rivet, new Label(rivet, "Button " + i), e -> {}));
//            }
//        }), c -> {
//            c.layoutOptions(new AnchorLayoutOptions(
//                    0.7F, 1, 0, 0.7F,
//                    0, 0, 0, 0,
//                    0, 0
//            ));
//        });
//        container.addChild(new SolidColor(rivet), c -> {
//            c.color(Color.BLUE);
//            c.layoutOptions(new AnchorLayoutOptions(
//                    0.2F, 0.7F, 0, 0.7F,
//                    0, 0, 0, 0,
//                    0, 0
//            ));
//        });
        rivet.root().addChild(container);
    }
}
