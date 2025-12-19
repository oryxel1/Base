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
        final long maxTime = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale()), totalTime = (long) (Timeline.DEFAULT_MAX_TIME * (timeline.getScroll() + 1) * timeline.getScale());
        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();

        elements.forEach((k, v) -> {
            float elementWidth = ((float) v.right() / maxTime) * width;
            float elementX = x + ((float) k / totalTime) * width;

            drawList.addRectFilled(new ImVec2(elementX, y), new ImVec2(elementX + elementWidth, y + 50), ImColor.rgb(192, 192, 192), 8f);
        });
    }

    public record Cache(Object object, RenderLayer layer, Integer attachedTo) {
    }
}
