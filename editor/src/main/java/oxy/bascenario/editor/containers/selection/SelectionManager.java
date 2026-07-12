package oxy.bascenario.editor.containers.selection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.math.Rectangle;
import oxy.bascenario.editor.containers.object.ObjectComponent;

import java.util.HashSet;
import java.util.Set;

@Accessors(fluent = true)
@Setter
public class SelectionManager {
    @Getter
    private Set<ObjectComponent> objects = new HashSet<>();
    public boolean isSelected(ObjectComponent component) {
        return objects.contains(component);
    }

    private float x, y;
    private float x1, y1;

    public void render(Renderer renderer) {
        if (x == 0 && y == 0) {
            return;
        }
        float minX = Math.min(x, x1), minY = Math.min(y, y1);
        float maxX = Math.max(x, x1), maxY = Math.max(y, y1);

        renderer.fillRect(minX, minY, maxX - minX, maxY - minY, Color.WHITE.withAlphaF(0.4f));
        renderer.outlineRect(minX, minY, maxX - minX, maxY - minY, 2, Color.WHITE);
    }

    public void addIfIntersects(ObjectComponent component, Rectangle rectangle) {
        if (x == 0 && y == 0) {
            return;
        }

        float minX = Math.min(x, x1), minY = Math.min(y, y1);
        float maxX = Math.max(x, x1), maxY = Math.max(y, y1);

        if (maxX >= rectangle.x() && minX <= rectangle.maxX() && maxY >= rectangle.y() && minY <= rectangle.maxY() +
                ((Container)component.parent()).parent().childBounds((Component) component.parent()).y()) {
            objects.add(component);
        }
    }
}
