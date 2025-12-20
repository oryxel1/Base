package oxy.bascenario.editor.screens.element;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.Pair;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@ToString
public class Track {
    private final Timeline timeline;
    private final int index;

    // key: start time, pair: a=element, b=end time.
    private final Map<Long, Pair<Cache, Long>> elements = new HashMap<>();

    public void render(float x, float y, float width) {
        final ImDrawList drawList = ImGui.getWindowDrawList();
        final float maxTime = Timeline.DEFAULT_MAX_TIME * timeline.getScale();
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();

        elements.forEach((k, v) -> {
            float elementWidth = (v.right() / maxTime) * width;
            float elementX = x + (k / maxTime) * width;
            if (timeline.getScroll() > 0) {
                final float ratio = (Timeline.DEFAULT_MAX_TIME * timeline.getScroll() * timeline.getScale()) / (Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                elementX -= ratio * (size.x - size.x / 4);
            }
            float distance = elementX - (pos.x + size.x / 4);
            elementX = Math.max(elementX, pos.x + size.x / 4);
            if (distance < 0) {
                elementWidth = Math.max(0, distance + elementWidth);
            }

            if (elementWidth <= 0) {
                return;
            }

            drawList.addRectFilled(new ImVec2(elementX, y), new ImVec2(elementX + elementWidth, y + 50), ImColor.rgb(192, 192, 192), 8f);
            drawList.addText(new ImVec2(elementX, y), ImColor.rgb(0, 0, 0), v.left().object.getClass().getSimpleName());
        });
    }

    public record Cache(Object object, RenderLayer layer, Integer attachedTo) {
    }

    private float timestampToPosition(Timeline timeline, long timestamp, float offsetX, float size) {
        return offsetX + ((timestamp - (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * timeline.getScroll())) / (Timeline.DEFAULT_MAX_TIME * timeline.getScale())) * size;
    }
}
