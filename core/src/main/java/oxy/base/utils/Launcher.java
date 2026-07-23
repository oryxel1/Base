package oxy.base.utils;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import oxy.base.Base;
import oxy.base.EngineRenderer;

public class Launcher {
    public static EngineRenderer WINDOW;

    public static void launch(Screen screen) {
        launch(screen, false);
    }

    public static void launch(Screen screen, boolean fullScreen) {
        launch(screen, fullScreen, false);
    }

    public static void launch(Screen screen, boolean fullScreen, boolean bypass) {
        if (!bypass) {
            Base.instance(); // Load a base instance first!
        }

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Blue Archive Scenario Engine.");
        configuration.setWindowedMode(1280, 720);
        configuration.setWindowSizeLimits(1280, 720, -1, -1);

        WINDOW = new EngineRenderer(screen, fullScreen);
        new Lwjgl3Application(WINDOW, configuration);
    }
}
