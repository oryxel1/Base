package com.bascenario.launcher;

import com.bascenario.render.MainRendererWindow;
import com.bascenario.render.api.Screen;

public class Launcher {
    public static MainRendererWindow WINDOW;

    public static void launch(Screen screen) {
        launch(screen, false);
    }

    public static void launch(Screen screen, boolean fullScreen) {
        WINDOW = new MainRendererWindow(screen, fullScreen);
        WINDOW.launch();
    }
}
