package oxy.bascenario.editor.timeline;

import lombok.RequiredArgsConstructor;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.ShapedText;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.layout.absolute.AbsoluteLayout;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import oxy.bascenario.editor.ScenarioEditorScreen;

import static oxy.bascenario.editor.ScenarioEditorScreen.DEFAULT_MAX_TIME;

public class TimelineContainer extends Container {
    private static final Color TIMELINE_CURSOR_COLOR = Color.fromRGB(202, 74, 92);

    private float trackListSizeRatio = 1/5f;

    private final ScenarioEditorScreen screen;
    public TimelineContainer(ScenarioEditorScreen screen) {
        super(AbsoluteLayout.INSTANCE);
        this.screen = screen;
    }

    @Override
    public void render(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(0, 0, bounds.width(), bounds.height(), Color.fromRGB(35, 35, 35).darker());

        drawTimeSection(renderer, bounds);
        drawTimelineCursor(renderer, bounds);

        super.render(renderer, bounds);
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Rectangle bounds) {
        final float timelineX = bounds.width() * trackListSizeRatio;
        if (event.x() > timelineX && event.x() < timelineX + (bounds.width() - timelineX) && event.y() < 35) {
            final float ratio = (event.x() - timelineX) / (bounds.width() - timelineX);
            long newTimestamp = (long) (DEFAULT_MAX_TIME * screen.scale() * screen.scroll() + ratio * DEFAULT_MAX_TIME * screen.scale());
            screen.timestamp(newTimestamp);
        }

        return super.onComponentMouseDown(event, bounds);
    }

    private void drawTimeSection(Renderer renderer, Rectangle bounds) {
        renderer.fillRect(bounds.width() * trackListSizeRatio, 0, bounds.width() - (bounds.width() * trackListSizeRatio), 35, Color.fromRGB(35, 35, 35));

        for (int i = 0; i <= 5; i++) {
            long time = (long) ((DEFAULT_MAX_TIME * screen.scale() * screen.scroll()) + (DEFAULT_MAX_TIME * screen.scale() * (i / 5f)));
            float segmentX = timestampToPosition(time, bounds.width() * trackListSizeRatio, bounds.width() - (bounds.width() * trackListSizeRatio));

            float seconds = time / 1000f;
            ShapedText text = this.rivet().backend().shapeText(seconds + "s", Color.WHITE);

            renderer.fillRect(segmentX, 0, 1, 10, Color.WHITE);
            renderer.scale(0.4f, () -> renderer.text(text, segmentX / 0.4f, 25, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.LOGICAL_TOP));
        }
    }

    private void drawTimelineCursor(Renderer renderer, Rectangle bounds) {
        final float cursorX = timestampToPosition(screen.timestamp(), bounds.width() * trackListSizeRatio, bounds.width() - (bounds.width() * trackListSizeRatio));

        renderer.fillTriangle(cursorX + 1.25f - 10, 0, cursorX + 1.25f, 10, cursorX + 1.25f + 10, 0, TIMELINE_CURSOR_COLOR);
        renderer.fillRect(cursorX, 0, 2, bounds.height(), TIMELINE_CURSOR_COLOR.withAlphaF(0.8f));
    }

    @Override
    public Size computeIdealSize(Size constraints) {
        return Size.EMPTY;
    }

    private float timestampToPosition(long timestamp, float offsetX, float size) {
        return offsetX + ((timestamp - (DEFAULT_MAX_TIME * screen.scale() * screen.scroll())) / (DEFAULT_MAX_TIME * screen.scale())) * size;
    }
}
