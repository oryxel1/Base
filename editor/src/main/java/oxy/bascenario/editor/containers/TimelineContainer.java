package oxy.bascenario.editor.containers;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.component.impl.SolidColor;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.layout.border.BorderLayout;
import net.lenni0451.rivet.layout.border.BorderPosition;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import oxy.bascenario.editor.ScenarioEditorScreen;
import oxy.bascenario.editor.containers.timeline.TimelineTimeSection;
import oxy.bascenario.editor.containers.track.TrackListContainer;

import static oxy.bascenario.editor.ScenarioEditorScreen.DEFAULT_MAX_TIME;

@Accessors(fluent = true)
public class TimelineContainer extends Container {
    private static final Color TIMELINE_CURSOR_COLOR = Color.fromRGB(202, 74, 92);
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

    public float scroll(float width) {
        return trackListContainer.scrollX() / width;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        System.out.println(rivet().focusedComponent() == null ? null : rivet().focusedComponent().getClass());

        renderer.fillRect(0, 0, bounds.width(), 35, Color.fromRGB(35, 35, 35));

        super.render(renderer, bounds);

        drawTimelineCursor(renderer, bounds);
    }

    private void drawTimelineCursor(Renderer renderer, Rectangle bounds) {
        final float cursorX = timestampToPosition(screen.timestamp(), 0, bounds.width(), screen.scale(), screen.scroll());

        renderer.fillTriangle(cursorX + 1.25f - 10, 0, cursorX + 1.25f, 10, cursorX + 1.25f + 10, 0, TIMELINE_CURSOR_COLOR);
        renderer.fillRect(cursorX, 0, 2, bounds.height(), TIMELINE_CURSOR_COLOR.withAlphaF(0.8f));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return constraints;
    }

    public static float timestampToPosition(long timestamp, float offsetX, float size, float scale, float scroll) {
        return offsetX + ((timestamp - (DEFAULT_MAX_TIME * scale * scroll)) / (DEFAULT_MAX_TIME * scale)) * size;
    }

    @Override
    protected boolean onComponentKeyUp(KeyEvent event) {
        if (event.key() == Key.DELETE) {
            trackListContainer.selectionManager().objects().forEach(component -> component.parentTrack().removeChild(component));
            trackListContainer.selectionManager().objects().clear();
        }

        return false;
    }
}
