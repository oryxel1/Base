package oxy.bascenario.editor.containers;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import org.lwjgl.glfw.GLFW;
import oxy.bascenario.editor.containers.timeline.TimelineContainer;

public class GlobalContainer extends Container {
    private final TimelineContainer timeline;

    public GlobalContainer() {
        super(new DockLayout());

        addChild(timeline = new TimelineContainer(), c -> c.layoutOptions(DockPosition.BOTTOM));
    }

    float prevMouseY;
    boolean nearTimeline, nearTimelineAndMouseDown;
    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

        if (nearTimelineAndMouseDown) {
            Rectangle bounds = this.childBounds(timeline);
            renderer.fillRect(bounds.x(), bounds.y() - 5, bounds.width(), 2, Color.fromRGB(115, 115, 115));
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        super.onComponentMouseMove(event, size);

        Rectangle timelineBounds = this.childBounds(timeline);
        boolean nearTimeline = Math.abs(event.y() - timelineBounds.y()) < 15;
        if (nearTimeline && !this.nearTimeline) {
            GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR));
        } else if (!nearTimeline && this.nearTimeline && !nearTimelineAndMouseDown) {
            GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        }
        this.nearTimeline = nearTimeline;
        if (!nearTimelineAndMouseDown) {
            this.nearTimelineAndMouseDown = event.isHeld(MouseButton.LEFT) && nearTimeline;
        }

        if (nearTimelineAndMouseDown && prevMouseY != Float.MAX_VALUE) {
            float newHeight = timelineBounds.height() - (event.y() - prevMouseY);
            float ratio = newHeight / size.height();
            timeline.relativeScale(ratio);
            timeline.requestLayoutRecalculation();
        }

        prevMouseY = event.y();
        if (!nearTimeline || !nearTimelineAndMouseDown) {
            prevMouseY = Float.MAX_VALUE;
        }

        return true;
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        this.nearTimelineAndMouseDown = false;
        return super.onComponentMouseUp(event, size);
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size size) {
        this.nearTimelineAndMouseDown = event.button() == MouseButton.LEFT && nearTimeline;
        if (!nearTimelineAndMouseDown) {
            prevMouseY = Float.MAX_VALUE;
        }

        super.onComponentMouseDown(event, size);
        return true;
    }
}
