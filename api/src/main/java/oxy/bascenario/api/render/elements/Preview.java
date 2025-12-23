package oxy.bascenario.api.render.elements;

import lombok.Builder;
import oxy.bascenario.api.render.elements.image.Image;

@Builder(toBuilder = true)
public record Preview(String title, String subtitle, Image background) {
}
