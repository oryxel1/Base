package oxy.bascenario.api.elements;

import oxy.bascenario.api.elements.image.Image;

import java.awt.*;

public record RendererImage(Image image, Color color, int width, int height) {
}
