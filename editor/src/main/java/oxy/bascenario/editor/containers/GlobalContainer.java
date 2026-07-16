package oxy.bascenario.editor.containers;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.input.keyboard.Key;
import net.lenni0451.rivet.input.keyboard.KeyEvent;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.layout.Layout;
import net.lenni0451.rivet.layout.dock.DockLayout;
import net.lenni0451.rivet.layout.dock.DockPosition;
import net.lenni0451.rivet.math.Rectangle;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.ThinGL;
import net.raphimc.thingl.implementation.window.GLFWWindowInterface;
import org.lwjgl.glfw.GLFW;
import oxy.bascenario.editor.EditorValues;
import oxy.bascenario.editor.containers.assets.AssetsContainer;
import oxy.bascenario.editor.containers.timeline.TimelineContainer;

import java.util.HashMap;
import java.util.Map;

public class GlobalContainer extends Container {
    public GlobalContainer() {
        super(new DockLayout(3));

        addChild(new TimelineContainer(), c -> c.layoutOptions(DockPosition.BOTTOM));
        addChild(new AssetsContainer(), c -> c.layoutOptions(DockPosition.LEFT));

        for (Component component : children()) {
            resizableComponents.put((ResizeableContainer) component, new ResizeComponent(
                    component.layoutOptions() == DockPosition.BOTTOM || component.layoutOptions() == DockPosition.TOP ?
                            ResizeComponent.ResizeType.Vertical : ResizeComponent.ResizeType.Horizontal,
                    (DockPosition) component.layoutOptions()
            ));
        }
    }

    final Map<ResizeableContainer, ResizeComponent> resizableComponents = new HashMap<>();

    @Override
    public void render(Renderer renderer, Size size) {
        super.render(renderer, size);

        for (Map.Entry<ResizeableContainer, ResizeComponent> entry : resizableComponents.entrySet()) {
            if (!entry.getValue().nearAndMouseDown) {
                continue;
            }
            final ResizeComponent component = entry.getValue();

            Rectangle bounds = this.childBounds(entry.getKey());

            if (component.type == ResizeComponent.ResizeType.Horizontal) {
                if (component.position == DockPosition.LEFT) {
                    renderer.fillRect(bounds.maxX() + 5, bounds.y(), 2, bounds.height(), Color.fromRGB(115, 115, 115));
                } else {
                    renderer.fillRect(bounds.x() + 5, bounds.y(), 2, bounds.height(), Color.fromRGB(115, 115, 115));
                }
            } else {
                renderer.fillRect(bounds.x(), bounds.y() - 5, bounds.width(), 2, Color.fromRGB(115, 115, 115));
            }
        }
    }

    @Override
    protected boolean onComponentMouseMove(MouseMoveEvent event, Size size) {
        super.onComponentMouseMove(event, size);

        for (Map.Entry<ResizeableContainer, ResizeComponent> entry : resizableComponents.entrySet()) {
            final ResizeComponent component = entry.getValue();
            Rectangle bounds = this.childBounds(entry.getKey());

            boolean near;
            if (component.type == ResizeComponent.ResizeType.Vertical) {
                near = Math.abs(event.y() - bounds.y()) < 4;
            } else {
                if (component.position == DockPosition.LEFT) {
                    near = Math.abs(event.x() - bounds.maxX()) < 4;
                } else {
                    near = Math.abs(event.x() - bounds.x()) < 4;
                }
            }

            if (near && event.isHeld(MouseButton.LEFT)) {
                int mouse = component.type == ResizeComponent.ResizeType.Vertical ? GLFW.GLFW_VRESIZE_CURSOR : GLFW.GLFW_HRESIZE_CURSOR;
                GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(mouse));
            }

            component.near = near;
            if (!component.nearAndMouseDown) {
                component.nearAndMouseDown = event.isHeld(MouseButton.LEFT) && near;
            }

            if (component.nearAndMouseDown) {
                if (component.type == ResizeComponent.ResizeType.Vertical) {
                    entry.getKey().relativeScale = ((size.height() - event.y()) - 7) / size.height();
                    entry.getKey().requestLayoutRecalculation();
                } else {
                    entry.getKey().relativeScale = (event.x() - 7) / size.width();
                    entry.getKey().requestLayoutRecalculation();
                }
            }
        }

        return true;
    }

    @Override
    protected boolean onComponentMouseUp(MouseButtonEvent event, Size size) {
        this.resizableComponents.values().forEach(c -> c.nearAndMouseDown = false);
        GLFW.glfwSetCursor(((GLFWWindowInterface) ThinGL.windowInterface()).getWindowHandle(), GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
        return super.onComponentMouseUp(event, size);
    }

    @Override
    protected boolean onComponentMouseDown(MouseButtonEvent event, Size size) {
        this.resizableComponents.values().forEach(c -> c.nearAndMouseDown = event.button() == MouseButton.LEFT && c.near);
        super.onComponentMouseDown(event, size);
        return true;
    }

    @Override
    protected boolean onComponentKeyUp(KeyEvent event) {
        if (event.key() == Key.SPACE) {
            EditorValues.instance().playing(!EditorValues.instance().playing());
        }

        return super.onComponentKeyUp(event);
    }

    @RequiredArgsConstructor
    private static class ResizeComponent {
        private final ResizeType type;
        private final DockPosition position;
        private boolean near, nearAndMouseDown;

        public enum ResizeType {
            Horizontal, Vertical
        }
    }

    @Accessors(fluent = true)
    public static class ResizeableContainer extends Container {
        @Setter
        protected float relativeScale = 0.2f;
        public ResizeableContainer(Layout layout) {
            super(layout);
        }
    }
}
