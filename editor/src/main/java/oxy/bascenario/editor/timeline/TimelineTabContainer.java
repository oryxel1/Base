package oxy.bascenario.editor.timeline;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.component.impl.slider.Slider;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.math.Rectangle;
import oxy.bascenario.editor.ScenarioEditorScreen;

public class TimelineTabContainer extends Container {
    private final ScenarioEditorScreen screen;

    private final Label timeLabel;
    public TimelineTabContainer(final ScenarioEditorScreen screen) {
        super(AnchorLayout.INSTANCE);

        final Slider slider = new Slider(0.01, 5, 0.01, 1);
        slider.barHeight().set(5f);
        slider.thumbWidth().set(20f);
        slider.thumbHeight().set(20f);
        slider.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.75f).withAnchorMaxX(0.95f).withAnchorMinY(0.5f).withPivotY(0.5f));

        slider.valueChangeListener().add(d -> screen.scale(d.floatValue()));

        this.addChild(slider);

        timeLabel = new Label("");
        timeLabel.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.5F).withAnchorMinY(0.5f).pivot(0.5f, 0.5f));
        timeLabel.scale(.5f);

        this.addChild(timeLabel);

        this.screen = screen;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        timeLabel.text(format(screen.timestamp()));
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
    }

    private static String format(long ms) {
        long millis = ms % 1000;
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + ":" + (millis < 10 ? "0" + millis : millis);
    }
}
