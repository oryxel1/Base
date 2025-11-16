package oxy.bascenario.engine.base;

public record Sound(FileInfo file, float maxVolume, Fade fadeIn, Fade fadeOut) {
}
