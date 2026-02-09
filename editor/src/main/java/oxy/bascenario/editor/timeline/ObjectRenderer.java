package oxy.bascenario.editor.timeline;

import com.badlogic.gdx.math.MathUtils;
import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import net.raphimc.thingl.text.TextRun;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.configurate.yaml.internal.snakeyaml.external.com.google.gdata.util.common.base.Escaper;
import oxy.bascenario.editor.utils.NameUtils;
import oxy.bascenario.editor.utils.TimeCompiler;
import oxy.bascenario.utils.font.FontUtils;
import oxy.bascenario.utils.font.TextUtils;

@RequiredArgsConstructor
public class ObjectRenderer {
    private final Timeline timeline;
    private final ObjectOrEvent object;

    public float x, y, width;

    public void render() {
        if (object.track < timeline.getVerticalScroll()) {
            return;
        }

        // This part determine render position and allat.
        final float maxTime = Timeline.DEFAULT_MAX_TIME * timeline.getScale();

        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        float trackX = pos.x + size.x / 4, trackWidth = size.x - (size.x / 4);
        float trackY = pos.y + 80 + ((object.track - timeline.getVerticalScroll()) * 50);

        this.width = (object.duration / maxTime) * trackWidth;
        if (!timeline.isDragging(this.object)) { // Fixed the position if not dragging....
            this.x = trackX + (object.start / maxTime) * trackWidth;
            this.y = trackY;

            if (timeline.getScroll() > 0) {
                final float ratio = (Timeline.DEFAULT_MAX_TIME * timeline.getScroll() * timeline.getScale()) / (Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                this.x -= ratio * (size.x - size.x / 4);
            }

            float distance = x - (pos.x + size.x / 4);
            x = Math.max(x, pos.x + size.x / 4);
            if (distance < 0) {
                this.width = Math.max(0, distance + width);
            }
        }

        if (this.width <= 0) {
            return;
        }

        handleDraggingResult();

        if (ImGui.isWindowFocused()) {
            handleMouse();
        }

        // This part actually draw the object, nothing crazy here...
        final ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(new ImVec2(x, y), new ImVec2(x + width, y + 50), ImColor.rgb(75, 114, 180), 5f);
        if (timeline.getSelectedObject() == this.object) {
            drawList.addRect(new ImVec2(x, y), new ImVec2(x + width, y + 50), ImColor.rgb(255, 255, 255), 5f);
        }

        String s = NameUtils.name(object.object);
        float width = TextUtils.getVisualWidth(17, TextRun.fromString(FontUtils.DEFAULT, s).shape());
        if (width == 0) {
            return;
        }
        s = s.substring(0, MathUtils.floor(Math.min((this.width / width) * s.length(), s.length())));

        drawList.addText(new ImVec2(x + 5, y + 5), ImColor.rgb(255, 255, 255), s);
    }

    private void handleDraggingResult() {
        // This handle the current dragging object see if it overlaps.
        if (timeline.isDragging(this.object) || !timeline.isDragging() || !timeline.getDraggingObject().isWaiting()) {
            return;
        }

        final ObjectOrEvent dragging = timeline.getDraggingObject().object;

        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        float x = Math.max(ImGui.getMousePosX() - dragging.renderer.draggingX, pos.x + size.x / 4);
        float y = Math.max(ImGui.getMousePosY() - dragging.renderer.draggingY, pos.y + 80);
        float width = (object.duration / (Timeline.DEFAULT_MAX_TIME * timeline.getScale())) * (size.x - (size.x / 4));
        int track = trackFromY(timeline, y);
        if (track != this.object.track) {
            return; // Not the same track, skip!
        }

        if (!timeline.getDraggingObject().second) {
            final float ratio = (object.renderer.x - pos.x - size.x / 4) / (size.x - size.x / 4);
            long time = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * timeline.getScroll() + ratio * Timeline.DEFAULT_MAX_TIME * timeline.getScale());
            timeline.getDraggingObject().time(time, object.start + object.duration);
        } else {
            long min = timeline.getDraggingObject().nearestTime + 1, max = min + object.duration;
            if (max >= object.start && min < object.start + object.duration) {
                timeline.getDraggingObject().reject();
            }
            return;
        }

        // Overlap! This result should not be valid.
        if (x + width >= this.x && x <= this.x + width) {
            timeline.getDraggingObject().reject();
        }
    }

    private float draggingX, draggingY;
    private void handleMouse() {
        if (handleDurationResize()) {
            return;
        }

        final boolean over = ImGui.isMouseHoveringRect(new ImVec2(x, y), new ImVec2(x + width, y + 50));
        final ImVec2 mouse = ImGui.getMousePos(), pos = ImGui.getWindowPos(), size = ImGui.getWindowSize();

        boolean dragging = timeline.isDragging(this.object);
        if (dragging && !timeline.getDraggingObject().isWaiting()) {
            this.x = Math.max(mouse.x - this.draggingX, pos.x + size.x / 4);
            this.y = Math.max(mouse.y - this.draggingY, pos.y + 80);
        }

        float length = ImGui.getMouseDragDelta().x * ImGui.getMouseDragDelta().x + ImGui.getMouseDragDelta().y * ImGui.getMouseDragDelta().y;
        if (ImGui.isMouseClicked(0) && over) {
            timeline.setSelectedObject(this.object); // Selected an element.
        } else if (ImGui.isMouseDown(0) && !timeline.isDragging() && over && length > 5 * 5) {
            // Start dragging an object if we can!
            timeline.setDraggingObject(new ObjectDragDrop(this.object) {
                @Override
                public void accept() {
                    final ImVec2 pos = ImGui.getWindowPos(), size = ImGui.getWindowSize();

                    final long oldStart = object.start;
                    final int oldTrack = object.track;
                    if (second) {
                        object.start = nearestTime + 1;
                    } else {
                        final float ratio = (object.renderer.x - pos.x - size.x / 4) / (size.x - size.x / 4);
                        object.start = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * timeline.getScroll() + ratio * Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                    }
                    object.track = trackFromY(timeline, object.renderer.y);

                    timeline.queueUndo(() -> {
                        object.start = oldStart;
                        object.track = oldTrack;
                    });
                }
            });

            this.draggingX = mouse.x - this.x;
            this.draggingY = mouse.y - this.y;
            dragging = true;
        }

        // Stop dragging, now we wait for result.....
        if (dragging && !ImGui.isMouseDown(0)) {
            timeline.getDraggingObject().waitForResult();
        }
    }

    private boolean resizing;
    private boolean handleDurationResize() {
        if (timeline.isDragging()) {
            this.resizing = false;
            return false;
        }

        final ImVec2 mouse = ImGui.getMousePos(), size = ImGui.getWindowSize();
        final boolean yMatch = mouse.y >= y && mouse.y <= y + 50;
        if (resizing && yMatch) { // If we're already resizing. we can't check for x axis xD.
            float ratio = (mouse.x - (x + width)) / (size.x - size.x / 4);
            long duration = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * ratio);

            // I wonder how fast this is....
            int next = timeline.getObjects().indexOf(this.object);
            if (next != -1 && next + 1 != timeline.getObjects().size()) {
                ObjectOrEvent nextObject = timeline.getObjects().get(next + 1);
                if (nextObject.track == this.object.track) {
                    duration = Math.min(duration, nextObject.start - (object.start + object.duration));
                }
            }

            if (TimeCompiler.canResize(object.object)) {
                final long oldDuration = object.duration;
                final Object oldObject = object;
                final float oldWidth = this.width;
                object.duration = Math.max(0, object.duration + duration);
                object.object = TimeCompiler.addTime(object.object, (int) duration);

                this.width = ((float) object.duration / (Timeline.DEFAULT_MAX_TIME * timeline.getScale())) * (size.x - size.x / 4);
                timeline.queueUndo(() -> {
                    this.width = oldWidth;
                    this.object.duration = oldDuration;
                    this.object.object = oldObject;
                });
            }

            resizing = ImGui.isMouseDown(0);
            return true;
        }

        resizing = false;
        float xDistanceToMax = Math.abs(mouse.x - (x + width));
        if (xDistanceToMax > 5 || !yMatch) {
            return false;
        }

        GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
        if (!ImGui.isMouseDown(0)) {
            return false;
        }

        timeline.setDraggingObject(null);
        resizing = true;
        return true;
    }

    private static int trackFromY(Timeline timeline, float y) {
        float approximateTrack = (y - (ImGui.getWindowPosY() + 80)) / 50f;
        int ceil = MathUtils.ceil(approximateTrack), floor = MathUtils.floor(approximateTrack);
        // See if floor or ceil is closer...
        int trackId;
        if (Math.abs(y - (ImGui.getWindowPosY() + 80 + (50 * ceil))) > Math.abs(y - (ImGui.getWindowPosY() + 80 + (50 * floor)))) {
            trackId = floor;
        } else {
            trackId = ceil;
        }

        return trackId + timeline.getVerticalScroll();
    }
}
