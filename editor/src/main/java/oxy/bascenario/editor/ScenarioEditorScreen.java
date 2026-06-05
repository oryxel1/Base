package oxy.bascenario.editor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.container.ScrollContainer;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.containers.AOContainer;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.TimelineTabContainer;
import oxy.bascenario.editor.containers.track.tab.TrackTabListContainer;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.thingl.ThinGLUtils;

@Accessors(fluent = true)
@Getter
@Setter
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario scenario;

    public static final long DEFAULT_MAX_TIME = 15000;
    private long timestamp = 1000;
    private float scale = 1;

    public void scale(float scale) {
        this.scale = scale;

        float width = ThinGL.windowInterface().getFramebufferWidth();
        timelineContainer.trackListContainer().resize(0.99f * width - 0.15625f * width);
        timelineContainer.trackListContainer().requestLayoutRecalculation();
    }

    private TimelineContainer timelineContainer;
    private Container trackTabContainer;

    public float scroll() {
        float width = ThinGL.windowInterface().getFramebufferWidth();
        return timelineContainer().scroll(0.99f * width - 0.15625f * width);
    }

    public ScenarioEditorScreen(Scenario scenario) {
        this.scenario = scenario;
        TimeUtils.fakeTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void renderBehindRivet() {
        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.fromRGB(50, 50, 50));
    }

    @Override
    public void init(Rivet rivet) {
        Container container = new Container(AnchorLayout.INSTANCE);

        container.addChild(new AOContainer(), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.01f).withAnchorMinY(0.06f).withAnchorMaxX(0.2f).withAnchorMaxY(0.6f));
        });

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.205f).withAnchorMinY(0.06f).withAnchorMaxX(0.8f).withAnchorMaxY(0.6f));
        });

        container.addChild(new SolidColor(), c -> {
            c.color(Color.fromRGB(35, 35, 35));
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.805f).withAnchorMinY(0.06f).withAnchorMaxX(0.99f).withAnchorMaxY(0.6f));
        });

        container.addChild(new TrackTabListContainer(trackTabContainer = new Container(new VerticalListLayout(3, false))) {
            @Override
            public void render(Renderer renderer, Rectangle bounds) {
                renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
                super.render(renderer, bounds);
            }
        }, c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.01f).withAnchorMinY(0.655f).withAnchorMaxX(0.15625f).withAnchorMaxY(0.99f));
        });

        container.addChild(timelineContainer = new TimelineContainer(this), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.15625f).withAnchorMinY(0.655f).withAnchorMaxX(0.99f).withAnchorMaxY(0.99f));
        });

        container.addChild(new TimelineTabContainer(this), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.01f).withAnchorMinY(0.605f).withAnchorMaxX(0.99f).withAnchorMaxY(0.65f));
        });

//        RivetUtil.dropDownList(container, "Timeline", AnchorLayoutOptions.EMPTY.withAnchorMinX(0.015f).withAnchorMinY(0.01f),
//                List.of(
//                        new Pair<>("Play", () -> {}),
//                        new Pair<>("Pause", () -> {})
//                )
//        );
        rivet.root().addChild(container);
    }

    @Override
    public void render(float delta) {
    }

//    @Override
//    public void resize(int width, int height) {
//        timelineContainer.trackListContainer().
//    }
}
