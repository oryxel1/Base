package oxy.bascenario.api.render.elements;

import lombok.Builder;
import oxy.bascenario.api.utils.FileInfo;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Preview(String title, String subtitle, FileInfo background) {
}
