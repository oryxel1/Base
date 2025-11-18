package oxy.bascenario.api.effects;

import oxy.bascenario.api.utils.FileInfo;

public record Sound(FileInfo file, float maxVolume, Fade fadeIn, Fade fadeOut) {
}

