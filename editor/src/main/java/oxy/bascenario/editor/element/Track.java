package oxy.bascenario.editor.element;

import com.badlogic.gdx.math.MathUtils;
import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import org.lwjgl.glfw.GLFW;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.editor.utils.TimeCompiler;
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
    @Getter
    private final Map<Long, Pair<Long, Long>> occupies = new HashMap<>();

    public void remove(Long l) {
        this.elements.remove(l);
        ElementRenderer renderer = this.renderers.remove(l);
        this.occupies.remove(l);

        if (renderer == timeline.getSelectedElement()) {
            timeline.setSelectedElement(null);
        }
    }

    public void put(Long l, Pair<Cache, Long> p) {
        this.elements.put(l, p);
        this.renderers.put(l, new ElementRenderer(this, timeline, l, p));
        this.occupies.put(l, new Pair<>(l, l + p.right()));

        timeline.updateScenario(true);
    }

    public void render(float x, float y, float width) {
        renderers.values().forEach(renderer -> renderer.render(x, y, width));
    }

    @RequiredArgsConstructor
    public static class ElementRenderer {
        @Getter
        private final Track track;

        private final Timeline timeline;
        @Getter
        private final long startTime;
        @Getter
        private final Pair<Cache, Long> pair;

        private float x, y, width;
        private float dragX, dragY;
        private boolean dragging;

        private boolean resizing;
        private boolean handleDurationResize() {
            final ImVec2 mouse = ImGui.getMousePos(), size = ImGui.getWindowSize();
            boolean yMatch = mouse.y >= y && mouse.y <= y + 50;
            if (resizing && yMatch) {
                float delta = mouse.x - (x + width);
                long duration = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * (delta / (size.x - size.x / 4)));
                if (TimeCompiler.canResize(pair.left().object)) {
                    pair.right(Math.max(0, pair.right() + duration));
                    pair.left().object(TimeCompiler.addTime(pair.left().object, (int) duration));
                    this.width = ((float) pair.right() / Timeline.DEFAULT_MAX_TIME * timeline.getScale()) * (size.x - size.x / 4);
                    final Pair<Long, Long> pair1 = track.occupies.get(startTime);
                    if (pair1 != null) {
                        pair1.right(pair1.left() + pair.right());
                    }

                    timeline.updateScenario(true);
                }
            }
            resizing = false;

            if (!yMatch) {
                return false;
            }
            float xDistanceToMax = Math.abs(mouse.x - (x + width));
            if (xDistanceToMax > 1) {
                return true;
            }

            GLFW.glfwSetCursor(((GLFWWindowInterface)ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
            if (!ImGui.isMouseDown(0)) {
                return false;
            }

            this.dragging = false;
            track.timeline.getScreen().setDragging(null);
            resizing = true;

            return true;
        }

        public void handleDragging() {
            boolean resize = handleDurationResize();

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
            this.dragging = mouseDown && hoveringOver && here && resize;
            if (this.dragging) {
                this.dragX = mouse.x - x;
                this.dragY = mouse.y - y;
                track.timeline.getScreen().setDragging(this);
                return;
            }

            if (oldDrag) {
                int ceil = MathUtils.ceil((this.y - (ImGui.getWindowPosY() + 80)) / 50f);
                int floor = MathUtils.floor((this.y - (ImGui.getWindowPosY() + 80)) / 50f);
                int trackId;
                if (Math.abs(this.y - (ImGui.getWindowPosY() + 80 + (50 * ceil))) > Math.abs(this.y - (ImGui.getWindowPosY() + 80 + (50 * floor)))) {
                    trackId = floor;
                } else {
                    trackId = ceil;
                }

                final float ratio = (this.x - pos.x - size.x / 4) / (size.x - size.x / 4);
                long time = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * timeline.getScroll() + ratio * Timeline.DEFAULT_MAX_TIME * timeline.getScale());

                Track newTrack = track.timeline.getTrack(trackId);
                if (newTrack == null || !newTrack.isOccupied(time, pair.right(), track.occupies.get(startTime)) && time != startTime) {
                    track.occupies.remove(startTime);
                    track.remove(startTime);

                    if (newTrack == null) {
                        newTrack = new Track(timeline, trackId);
                        track.timeline.putTrack(trackId, newTrack);
                    }
                    newTrack.put(time, pair);
                }
            }

            if (track.timeline.getScreen().getDragging() == this) {
                track.timeline.getScreen().setDragging(null);
            }
        }

        public void render(float x, float y, float width) {
            if (timeline.getSelectedElement() == this && ImGui.isKeyPressed(ImGuiKey.Delete)) {
                track.remove(startTime);
                timeline.updateScenario(true);
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

    @AllArgsConstructor
    public static final class Cache {
        private Object object;
        private RenderLayer layer;
        private final Integer attachedTo;
        private boolean requireWait;

        public Object object() {
            return object;
        }

        public void object(Object object) {
            this.object = object;
        }

        public void layer(RenderLayer layer) {
            this.layer = layer;
        }

        public RenderLayer layer() {
            return layer;
        }

        public Integer attachedTo() {
            return attachedTo;
        }

        public boolean requireWait() {
            return requireWait;
        }

        public void requireWait(boolean requireWait) {
            this.requireWait = requireWait;
        }
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
