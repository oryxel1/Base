package oxy.bascenario;

import lombok.Getter;
import oxy.bascenario.renderer.config.RendererConfig;

public class Launcher {
    @Getter
    private static RendererConfig config;

    public static void launch() {
        // TODO: Config save/load.
        config = new RendererConfig();
    }
}
