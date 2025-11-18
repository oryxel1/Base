package oxy.bascenario.api.elements;

import oxy.bascenario.api.effects.Fade;
import oxy.bascenario.api.utils.FileInfo;

public record Sprite(FileInfo path, Fade fadeIn, Fade fadeOut) {
}