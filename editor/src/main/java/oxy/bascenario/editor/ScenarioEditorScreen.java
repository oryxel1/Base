package oxy.bascenario.editor;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import net.raphimc.thingl.ThinGL;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.editor.containers.AOContainer;
import oxy.bascenario.editor.containers.TimelineContainer;
import oxy.bascenario.editor.containers.TimelineTabContainer;
import oxy.bascenario.editor.containers.inspector.InspectorContainer;
import oxy.bascenario.editor.containers.track.tab.TrackTabListContainer;
import oxy.bascenario.utils.ExtendableScreen;
import oxy.bascenario.utils.TimeUtils;
import oxy.bascenario.utils.thingl.ThinGLUtils;

@Accessors(fluent = true)
@Getter
@Setter
public class ScenarioEditorScreen extends ExtendableScreen {
    private final Scenario.Builder scenario;

    private static final float ONE_SECOND_WIDTH = 128;
    public float oneSecondWidth() {
        return ONE_SECOND_WIDTH * scale;
    }
    public float oneMilSecondWidth() {
        return (ONE_SECOND_WIDTH / 1000f) * scale;
    }
//    public static final long DEFAULT_MAX_TIME = 15000;

    private boolean playing;
    private long timestamp = 1000;
    public void timestamp(long timestamp) {
        this.timestamp = timestamp;
        playing(false);
    }

    public void playing(boolean playing) {
        this.playing = playing;
        if (timelineTabContainer != null) {
            timelineTabContainer.playButton().state(playing);
        }
    }

    private float scale = 1;

    public void scale(float scale) {
        this.scale = scale;
        timelineContainer.trackListContainer().recalculateObjectPosition();
        timelineContainer.trackListContainer().requestLayoutRecalculation();
    }

    private TimelineTabContainer timelineTabContainer;
    private TimelineContainer timelineContainer;
//    private Container trackTabContainer;

    public ScenarioEditorScreen(Scenario scenario) {
        this.scenario = scenario.toBuilder();
        TimeUtils.fakeTimeMillis = System.currentTimeMillis();
    }

    private long since = 0;
    @Override
    public void renderBehindRivet() {
        if (since == 0) {
            since = System.currentTimeMillis();
        }
        if (playing) {
            timestamp += System.currentTimeMillis() - since;
        }
        since = System.currentTimeMillis();

        ThinGL.renderer2D().filledRectangle(ThinGLUtils.GLOBAL_RENDER_STACK, 0, 0, ThinGL.windowInterface().getFramebufferWidth(), ThinGL.windowInterface().getFramebufferHeight(), Color.fromRGB(22, 22, 22));
    }

    @Override
    public void init(Rivet rivet) {
        Container container = new Container(AnchorLayout.INSTANCE);

        container.addChild(new AOContainer(this), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.005f).withAnchorMinY(0.02f).withAnchorMaxX(0.2f).withAnchorMaxY(0.6f));
        });

        // preview
        container.addChild(new SolidColor(), c -> {
            c.color(Color.BLACK);
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.205f).withAnchorMinY(0.02f).withAnchorMaxX(0.8f).withAnchorMaxY(0.55f));
        });

        container.addChild(new InspectorContainer(), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.805f).withAnchorMinY(0.02f).withAnchorMaxX(0.99f).withAnchorMaxY(0.6f));
        });

        container.addChild(timelineContainer = new TimelineContainer(this), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0).withAnchorMinY(0.61f).withAnchorMaxX(1).withAnchorMaxY(1));
        });

//        container.addChild(new TrackTabListContainer(new Container(new VerticalListLayout(3, false)), timelineContainer), c -> {
//            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0).withAnchorMinY(0.61f).withAnchorMaxX(0.15525f).withAnchorMaxY(1));
//        });

        container.addChild(timelineTabContainer = new TimelineTabContainer(this), c -> {
            c.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.205f).withAnchorMinY(0.55f).withAnchorMaxX(0.8f).withAnchorMaxY(0.6f));
        });

//        RivetUtil.dropDownList(container, "Timeline", AnchorLayoutOptions.EMPTY.withAnchorMinX(0.015f).withAnchorMinY(0.01f),
//                List.of(
//                        new Pair<>("Play", () -> {}),
//                        new Pair<>("Pause", () -> {})
//                )
//        );

        rivet.root().addChild(container);
    }
}
