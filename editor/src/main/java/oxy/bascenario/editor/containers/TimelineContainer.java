package oxy.bascenario.editor.containers;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.timeline.TimelineTimeSection;
import oxy.bascenario.editor.containers.track.TrackListContainer;

@Accessors(fluent = true)
public class TimelineContainer extends Container {
    private static final Color TIMELINE_CURSOR_COLOR = Color.fromRGB(145, 218, 255);
    @Getter
    private final TrackListContainer trackListContainer;

    @Getter
    private final ScenarioEditorScreen screen;
    public TimelineContainer(ScenarioEditorScreen screen) {
        super(BorderLayout.INSTANCE);
        this.screen = screen;

        this.addChild(new TimelineTimeSection(this), c -> c.layoutOptions(BorderPosition.TOP));
        this.addChild(trackListContainer = new TrackListContainer(this), c -> c.layoutOptions(BorderPosition.CENTER));
    }

    @Override
    public void render(Renderer renderer, Size bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35).darker());
        renderer.fillRect(0, 0, bounds.width(), 35, Color.fromRGB(35, 35, 35));
        super.render(renderer, bounds);
        drawTimelineCursor(renderer, bounds);
    }

    private void drawTimelineCursor(Renderer renderer, Size bounds) {
        final float cursorX = screen.timestamp() * (screen.oneSecondWidth() / 1000f) - trackListContainer.scrollX();

        renderer.fillTriangle(cursorX + 1.25f - 10, 0, cursorX + 1.25f, 10, cursorX + 1.25f + 10, 0, TIMELINE_CURSOR_COLOR);
        renderer.fillRect(cursorX, 0, 2, bounds.height(), TIMELINE_CURSOR_COLOR.withAlphaF(0.8f));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    public static float timestampToPosition(long timestamp, float offsetX, float size, float scale) {
        return offsetX + (timestamp * size * scale);
    }
}
