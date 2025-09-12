package com.bascenario;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bascenario.render.MainRendererWindow;
import com.bascenario.render.api.Screen;

public class Launcher {
    public static MainRendererWindow WINDOW;

    public static void launch(Screen screen) {
        launch(screen, false);
    }

    public static void launch(Screen screen, boolean fullScreen) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Blue Archive Scenario Engine.");
        configuration.setWindowedMode(1280, 700);
        configuration.setWindowSizeLimits(1280, 700, -1, -1);
        if (fullScreen) {
            configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        }

        WINDOW = new MainRendererWindow(screen);
        new Lwjgl3Application(WINDOW, configuration);
    }
}
