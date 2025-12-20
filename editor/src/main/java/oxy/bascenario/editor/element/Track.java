package oxy.bascenario.editor.element;

import com.badlogic.gdx.math.MathUtils;
import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.utils.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@ToString
public class Track {
    private final Timeline timeline;
    @Getter
    private final int index;

    // key: start time, pair: a=element, b=duration.
    @Getter
    private final Map<Long, Pair<Cache, Long>> elements = new TreeMap<>();
    private final Map<Long, ElementRenderer> renderers = new ConcurrentHashMap<>();
    private final Map<Long, Pair<Long, Long>> occupies = new HashMap<>();

    public void put(Long l, Pair<Cache, Long> p) {
        this.elements.put(l, p);
        this.renderers.put(l, new ElementRenderer(this, timeline, l, p));
        this.occupies.put(l, new Pair<>(l, l + p.right()));
    }

    public void render(float x, float y, float width) {
        renderers.values().forEach(renderer -> renderer.render(x, y, width));
    }

    @RequiredArgsConstructor
    private static class ElementRenderer {
        private final Track track;

        private final Timeline timeline;
        private final long startTime;
        private final Pair<Cache, Long> pair;

        private float x, y, width;
        private float dragX, dragY;
        private boolean dragging;

        public void handleDragging() {
            final ImVec2 mouse = ImGui.getMousePos(), pos = ImGui.getWindowPos(), size = ImGui.getWindowSize();
            boolean mouseDown = ImGui.getIO().getMouseDown(0), mouseClicked = ImGui.getIO().getMouseClicked(0);

            if (this.dragging) {
                this.x = Math.max(mouse.x - dragX, pos.x + size.x / 4);
                this.y = Math.max(mouse.y - dragY, pos.y + 80);
            }

            final boolean hoveringOver = mouse.x >= x && mouse.x <= x + width && mouse.y >= y && mouse.y <= y + 50;
            if (mouseClicked && hoveringOver) {
                timeline.setSelectedElement(this);
            }

            boolean here = (track.timeline.getScreen().getDragging() == null || track.timeline.getScreen().getDragging() == this);

            boolean oldDrag = this.dragging;
            this.dragging = mouseDown && hoveringOver && here;
            if (this.dragging) {
                this.dragX = mouse.x - x;
                this.dragY = mouse.y - y;
                track.timeline.getScreen().setDragging(this);
                return;
            }

            if (oldDrag) {
                int trackId = MathUtils.ceil((this.y - (ImGui.getWindowPosY() + 80)) / 50f);
                long time = (long) ((this.x - pos.x - size.x / 4) / (size.x - size.x / 4) * Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                final Track newTrack = track.timeline.getTracks().get(trackId);
                if (newTrack == null || !newTrack.isOccupied(time, pair.right(), track.occupies.get(startTime)) && trackId != track.index) {
                    track.renderers.remove(startTime);
                    track.elements.remove(startTime);
                    track.occupies.remove(startTime);

                    track.timeline.getTracks().putIfAbsent(trackId, new Track(timeline, trackId));
                    track.timeline.getTracks().get(trackId).put(time, pair);
                }
            }

            if (track.timeline.getScreen().getDragging() == this) {
                track.timeline.getScreen().setDragging(null);
            }
        }

        public void render(float x, float y, float width) {
            if (timeline.getSelectedElement() == this && ImGui.isKeyPressed(ImGuiKey.Delete)) {
                track.renderers.remove(startTime);
                track.elements.remove(startTime);
                track.occupies.remove(startTime);
                return;
            }

            final float maxTime = Timeline.DEFAULT_MAX_TIME * timeline.getScale();
            final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
            final ImDrawList drawList = ImGui.getWindowDrawList();

            this.width = (pair.right() / maxTime) * width;
            if (!this.dragging) {
                this.x = x + (startTime / maxTime) * width;
                this.y = y;

                if (timeline.getScroll() > 0) {
                    final float ratio = (Timeline.DEFAULT_MAX_TIME * timeline.getScroll() * timeline.getScale()) / (Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                    this.x -= ratio * (size.x - size.x / 4);
                }
                float distance = this.x - (pos.x + size.x / 4);
                this.x = Math.max(this.x, pos.x + size.x / 4);
                if (distance < 0) {
                    this.width = Math.max(0, distance + this.width);
                }
            }

            handleDragging();
            if (this.width <= 0) {
                return;
            }

            drawList.addRectFilled(new ImVec2(this.x, this.y), new ImVec2(this.x + this.width, this.y + 50), ImColor.rgb(75, 114, 180), 5f);
            if (timeline.getSelectedElement() == this) {
                drawList.addRect(new ImVec2(this.x, this.y), new ImVec2(this.x + this.width, this.y + 50), ImColor.rgb(255, 255, 255), 5f);
            }

            drawList.addText(new ImVec2(this.x, this.y), ImColor.rgb(0, 0, 0), pair.left().object.getClass().getSimpleName());
        }
    }

    public record Cache(Object object, RenderLayer layer, Integer attachedTo) {
    }

    public boolean isOccupied(long time, long duration, Pair<Long, Long> current) {
        for (Pair<Long, Long> longLongPair : occupies.values()) {
            final long maxTime = longLongPair.right(), minTime = longLongPair.left();
            if (maxTime >= time && minTime <= time + duration && longLongPair != current) {
                return true;
            }
        }

        return false;
    }
}
