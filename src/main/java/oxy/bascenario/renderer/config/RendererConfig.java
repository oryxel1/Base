package oxy.bascenario.renderer.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RendererConfig {
    private boolean fullScreen;
    private AspectRatio ratio = AspectRatio.SixteenToNine;
}
