package oxy.bascenario.editor.inspector.impl.objects;

import oxy.bascenario.api.render.elements.shape.Circle;
import oxy.bascenario.api.render.elements.shape.Rectangle;
import oxy.bascenario.utils.ImGuiUtils;

public class ShapeInspector {
    public static Circle render(Circle circle) {
        Circle.Builder builder = circle.toBuilder();
        builder.radius(ImGuiUtils.sliderFloat("Radius", circle.radius(), 0, 1000));
        builder.outlineOnly(ImGuiUtils.checkbox("Outline Only", circle.outlineOnly()));
        builder.color(ImGuiUtils.color("Color", circle.color()));
        return builder.build();
    }

    public static Rectangle render(Rectangle rectangle) {
        Rectangle.Builder builder = rectangle.toBuilder();
        builder.width(ImGuiUtils.sliderInt("Width", rectangle.width(), 0, 1920));
        builder.height(ImGuiUtils.sliderInt("Height", rectangle.height(), 0, 1080));
        builder.outlineOnly(ImGuiUtils.checkbox("Outline Only", rectangle.outlineOnly()));
        builder.color(ImGuiUtils.color("Color", rectangle.color()));
        return builder.build();
    }
}
