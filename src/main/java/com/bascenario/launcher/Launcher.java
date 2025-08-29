package com.bascenario.launcher;

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
//        configuration.setWindowSizeLimits(1280, 899, -1, -1);

        WINDOW = new MainRendererWindow(screen, fullScreen);
        new Lwjgl3Application(WINDOW, configuration);
    }
}
