package oxy.bascenario.renderer.config;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public final class RendererConfig {
    private boolean fullScreen;
    private AspectRatio ratio = AspectRatio.SixteenToNine;
    private File path = new File("base");
}
