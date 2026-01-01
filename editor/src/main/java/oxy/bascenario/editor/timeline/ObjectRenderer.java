package oxy.bascenario.editor.timeline;

import com.badlogic.gdx.math.MathUtils;
import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectRenderer {
    private final Timeline timeline;
    private final ObjectOrEvent object;

    private float x, y, width;
//    private float dragX, dragY;
//    private boolean dragging;
//
//    private boolean resizing;

    public void render() {
        // This part determine render position and allat.
        final float maxTime = Timeline.DEFAULT_MAX_TIME * timeline.getScale();

        final ImVec2 size = ImGui.getWindowSize(), pos = ImGui.getWindowPos();
        float trackX = pos.x + size.x / 4, trackWidth = size.x - (size.x / 4);
        float trackY = pos.y + 80 + (object.track * 50);

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
        if (timeline.getSelectedElement() == this) {
            drawList.addRect(new ImVec2(x, y), new ImVec2(x + width, y + 50), ImColor.rgb(255, 255, 255), 5f);
        }

        drawList.addText(new ImVec2(x, y), ImColor.rgb(0, 0, 0), object.object.getClass().getSimpleName());
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
        int track = trackFromY(y);
        if (track != this.object.track) {
            return; // Not the same track, skip!
        }

        // Overlap! This result should not be valid.
        if (x + width >= this.x && x <= this.x + width) {
            timeline.getDraggingObject().reject();
        }
    }

    private float draggingX, draggingY;
    private void handleMouse() {
        final boolean over = ImGui.isMouseHoveringRect(new ImVec2(x, y), new ImVec2(x + width, y + 50));
        final ImVec2 mouse = ImGui.getMousePos(), pos = ImGui.getWindowPos(), size = ImGui.getWindowSize();

        boolean dragging = timeline.isDragging(this.object);
        if (dragging && !timeline.getDraggingObject().isWaiting()) {
            this.x = Math.max(mouse.x - this.draggingX, pos.x + size.x / 4);
            this.y = Math.max(mouse.y - this.draggingY, pos.y + 80);
        }

        if (ImGui.isMouseDoubleClicked(0) && over) {
            timeline.setSelectedElement(this); // Selected an element.
        } else if (ImGui.isMouseDown(0) && !timeline.isDragging() && over) {
            // Start dragging an object if we can!
            timeline.setDraggingObject(new ObjectDragDrop(this.object) {
                @Override
                public void accept() {
                    final ImVec2 pos = ImGui.getWindowPos(), size = ImGui.getWindowSize();

                    final float ratio = (object.renderer.x - pos.x - size.x / 4) / (size.x - size.x / 4);
                    object.start = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * timeline.getScroll() + ratio * Timeline.DEFAULT_MAX_TIME * timeline.getScale());
                    object.track = trackFromY(object.renderer.y);
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

    private static int trackFromY(float y) {
        float approximateTrack = (y - (ImGui.getWindowPosY() + 80)) / 50f;
        int ceil = MathUtils.ceil(approximateTrack), floor = MathUtils.floor(approximateTrack);
        // See if floor or ceil is closer...
        int trackId;
        if (Math.abs(y - (ImGui.getWindowPosY() + 80 + (50 * ceil))) > Math.abs(y - (ImGui.getWindowPosY() + 80 + (50 * floor)))) {
            trackId = floor;
        } else {
            trackId = ceil;
        }

        return trackId;
    }

//    private boolean handleDurationResize() {
//        final ImVec2 mouse = ImGui.getMousePos(), size = ImGui.getWindowSize();
//        boolean yMatch = mouse.y >= y && mouse.y <= y + 50;
//        if (resizing && yMatch) {
//            float delta = mouse.x - (x + width);
//            long duration = (long) (Timeline.DEFAULT_MAX_TIME * timeline.getScale() * (delta / (size.x - size.x / 4)));
//
//            long next = nextObject != null ? nextObject.start : -1;
//            if (next != -1) {
//                long max = next - (object.start + object.duration);
//                duration = Math.min(duration, max);
//            }
//
//            if (TimeCompiler.canResize(object.object)) {
//                object.duration = Math.max(0, object.duration + duration);
//                object.object = TimeCompiler.addTime(object.object, (int) duration);
//
//                this.width = ((float) object.duration / Timeline.DEFAULT_MAX_TIME * timeline.getScale()) * (size.x - size.x / 4);
//                timeline.updateScenario(true);
//            }
//        }
//        resizing = false;
//
//        if (!yMatch) {
//            return false;
//        }
//        float xDistanceToMax = Math.abs(mouse.x - (x + width));
//        if (xDistanceToMax > 1) {
//            return true;
//        }
//
//        GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
//        if (!ImGui.isMouseDown(0)) {
//            return false;
//        }
//
//        this.dragging = false;
//        track.timeline.getScreen().setDragging(null);
//        resizing = true;
//
//        return true;
//    }
}
