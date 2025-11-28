package oxy.bascenario.api.render.elements;

import net.lenni0451.commons.color.Color;
import oxy.bascenario.api.render.elements.image.Image;

public record RendererImage(Image image, Color color, int width, int height) {
}
