package oxy.bascenario.utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import oxy.bascenario.EngineRenderer;

public class Launcher {
    public static EngineRenderer WINDOW;

    public static void launch(Screen screen) {
        launch(screen, false);
    }

    public static void launch(Screen screen, boolean fullScreen) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Blue Archive Scenario Engine.");
        configuration.setWindowedMode(1280, 720);
        configuration.setWindowSizeLimits(1280, 720, -1, -1);
        if (fullScreen) {
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }

        WINDOW = new EngineRenderer(screen);
        new Lwjgl3Application(WINDOW, configuration);
    }
}
