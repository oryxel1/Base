package oxy.bascenario.editor.containers.timeline;

import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.Label;
import net.lenni0451.rivet.layout.anchor.AnchorLayout;
import net.lenni0451.rivet.layout.anchor.AnchorLayoutOptions;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.EditorValues;

public class TimelineTimeControl extends Container {
    private final Label timeLabel;

    public TimelineTimeControl() {
        super(AnchorLayout.INSTANCE);

        timeLabel = new Label("");
        timeLabel.layoutOptions(AnchorLayoutOptions.EMPTY.withAnchorMinX(0.95f).withAnchorMinY(0.5f).pivot(0.5f, 0.5f));
        timeLabel.scale(.45f);

        this.addChild(timeLabel);
    }

    @Override
    public void render(Renderer renderer, Size size) {
        timeLabel.text(format(EditorValues.instance().timestamp()));
        super.render(renderer, size);
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints.withHeight(25);
    }

    private String format(long ms) {
        long millis = ms % 1000;
        long second = (ms / 1000) % 60;
        long minute = (ms / (1000 * 60)) % 60;
        long hour = (ms / (1000 * 60 * 60)) % 24;
        return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + ":" + (millis < 10 ? "0" + millis : millis);
    }
}
